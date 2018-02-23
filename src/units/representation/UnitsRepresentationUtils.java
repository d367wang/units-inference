package units.representation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.util.Elements;
import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import org.checkerframework.javacutil.AnnotationBuilder;
import org.checkerframework.javacutil.AnnotationUtils;
import org.checkerframework.javacutil.ErrorReporter;
import units.qual.BaseUnit;
import units.qual.Dimensionless;
import units.qual.PolyUnit;
import units.qual.UnitsAlias;
import units.qual.UnitsBottom;
import units.qual.UnitsInternal;
import units.qual.UnknownUnits;

/**
 * Utility class containing logic for creating and converting internal representations of units
 * between its 3 primary forms: {@link UnitsInternal} as annotation mirrors and
 * {@link TypecheckUnit}.
 *
 * TODO: {@code @Unit}, and alias forms.
 */
public class UnitsRepresentationUtils {
    private static UnitsRepresentationUtils singletonInstance;
    private static ProcessingEnvironment processingEnv;
    private static Elements elements;

    /** An instance of {@link PolyUnit} as an {@link AnnotationMirror} */
    public AnnotationMirror POLYUNIT;

    /** An instance of {@link UnitsInternal} with no values in its elements */
    public AnnotationMirror RAWUNITSINTERNAL;

    /** Instances of {@link UnitsInternal} with values to represent UnknownUnits and UnitsBottom */
    public AnnotationMirror TOP;
    public AnnotationMirror BOTTOM;

    /**
     * An instance of {@link UnitsInternal} with default values in its elements, which represents
     * dimensionless
     */
    public AnnotationMirror DIMENSIONLESS;

    // Comparator used to sort annotation classes by their simple class name
    private static Comparator<Class<? extends Annotation>> annoClassComparator =
            new Comparator<Class<? extends Annotation>>() {
                @Override
                public int compare(Class<? extends Annotation> a1, Class<? extends Annotation> a2) {
                    return a1.getSimpleName().compareTo(a2.getSimpleName());
                }
            };

    /** The set of base units */
    private final Set<Class<? extends Annotation>> baseUnits = new TreeSet<>(annoClassComparator);
    private Set<String> baseUnitNames;

    /** The set of alias units defined as qualifiers */
    private final Set<Class<? extends Annotation>> aliasUnits = new TreeSet<>(annoClassComparator);

    /**
     * A map from supported units annotation mirrors (including aliases) to their internal units
     * representation, keyed on the string name of the (alias) annotation mirror
     */
    private final Map<AnnotationMirror, AnnotationMirror> unitsAnnotationMirrorMap =
            new HashMap<>();

    private UnitsRepresentationUtils(ProcessingEnvironment processingEnv, Elements elements) {
        UnitsRepresentationUtils.processingEnv = processingEnv;
        UnitsRepresentationUtils.elements = elements;
    }

    public static UnitsRepresentationUtils getInstance(ProcessingEnvironment processingEnv,
            Elements elements) {
        if (singletonInstance == null) {
            singletonInstance = new UnitsRepresentationUtils(processingEnv, elements);
        }
        return singletonInstance;
    }

    public static UnitsRepresentationUtils getInstance() {
        if (singletonInstance == null) {
            ErrorReporter.errorAbort(
                    "getInstance() called without initializing UnitsRepresentationUtils.");
        }
        return singletonInstance;
    }

    public void addBaseUnit(Class<? extends Annotation> baseUnit) {
        baseUnits.add(baseUnit);
    }

    public Set<String> baseUnits() {
        // copy simple names of all base units into the set if it hasn't been done before
        if (baseUnitNames == null) {
            baseUnitNames = new TreeSet<>();
            for (Class<? extends Annotation> baseUnit : baseUnits) {
                baseUnitNames.add(baseUnit.getSimpleName());
            }

            baseUnitNames = Collections.unmodifiableSet(baseUnitNames);
        }

        return baseUnitNames;
    }

    public void addAliasUnit(Class<? extends Annotation> aliasUnit) {
        aliasUnits.add(aliasUnit);
    }

    // public Set<Class<? extends Annotation>> aliasUnits() {
    // return aliasUnits;
    // }

    // postInit() is called after performing annotation loading to obtain the full list of base
    // units
    public void postInit() {
        POLYUNIT = AnnotationBuilder.fromClass(elements, PolyUnit.class);

        Map<String, Integer> zeroBaseDimensions = new TreeMap<>();
        for (String baseUnit : baseUnits()) {
            zeroBaseDimensions.put(baseUnit, 0);
        }

        RAWUNITSINTERNAL = AnnotationBuilder.fromClass(elements, UnitsInternal.class);

        TOP = createInternalUnit("UnknownUnits", true, false, 0, zeroBaseDimensions);
        BOTTOM = createInternalUnit("UnitsBottom", false, true, 0, zeroBaseDimensions);
        DIMENSIONLESS = createInternalUnit("Dimensionless", false, false, 0, zeroBaseDimensions);

        unitsAnnotationMirrorMap.put(AnnotationBuilder.fromClass(elements, UnknownUnits.class),
                TOP);
        unitsAnnotationMirrorMap.put(AnnotationBuilder.fromClass(elements, UnitsBottom.class),
                BOTTOM);
        unitsAnnotationMirrorMap.put(AnnotationBuilder.fromClass(elements, Dimensionless.class),
                DIMENSIONLESS);

        for (Class<? extends Annotation> baseUnit : baseUnits) {
            createInternalBaseUnit(baseUnit);
        }

        for (Class<? extends Annotation> aliasUnit : aliasUnits) {
            createInternalAliasUnit(aliasUnit);
        }

        // for (Entry<AnnotationMirror, AnnotationMirror> entry : unitsAnnotationMirrorMap
        // .entrySet()) {
        // System.out.println(" == built map " + entry.getKey() + " --> " + entry.getValue());
        // }
    }

    /**
     * Creates an internal unit representation for the given base unit and adds it to the alias map.
     */
    private void createInternalBaseUnit(Class<? extends Annotation> baseUnitClass) {
        // check to see if the annotation has already been mapped before
        AnnotationMirror baseUnitAM = AnnotationBuilder.fromClass(elements, baseUnitClass);
        for (AnnotationMirror unit : unitsAnnotationMirrorMap.keySet()) {
            if (AnnotationUtils.areSame(baseUnitAM, unit)) {
                return;
            }
        }

        Map<String, Integer> exponents = new TreeMap<>();
        // default all base units to exponent 0
        for (String bu : baseUnits()) {
            exponents.put(bu, 0);
        }
        // set the exponent of the given base unit to 1
        exponents.put(baseUnitClass.getSimpleName(), 1);
        // create the internal unit and add to alias map
        unitsAnnotationMirrorMap.put(baseUnitAM,
                createInternalUnit(baseUnitClass.getCanonicalName(), false, false, 0, exponents));
    }

    /**
     * Creates an internal unit representation for the given alias unit and adds it to the alias
     * map.
     */
    private void createInternalAliasUnit(Class<? extends Annotation> aliasUnitClass) {
        // check to see if the annotation has already been mapped before
        AnnotationMirror aliasUnitAM = AnnotationBuilder.fromClass(elements, aliasUnitClass);
        for (AnnotationMirror unit : unitsAnnotationMirrorMap.keySet()) {
            if (AnnotationUtils.areSame(aliasUnitAM, unit)) {
                return;
            }
        }

        int prefix = 0;

        Map<String, Integer> exponents = new TreeMap<>();
        // default all base units to exponent 0
        for (String bu : baseUnits()) {
            exponents.put(bu, 0);
        }

        // replace default base unit exponents from anno, and accumulate prefixes
        UnitsAlias aliasInfo = aliasUnitClass.getAnnotation(UnitsAlias.class);
        for (BaseUnit bu : aliasInfo.value()) {
            exponents.put(bu.unit(), bu.exponent());
            prefix += bu.prefix();
        }

        unitsAnnotationMirrorMap.put(aliasUnitAM, createInternalUnit(
                aliasUnitClass.getCanonicalName(), false, false, prefix, exponents));
    }
    //
    // /**
    // * Creates an internal unit representation for the given alias AnnotationMirror and its
    // * component UnitsAlias meta annotation, adds it to the alias map, and returns the internal
    // * representation.
    // *
    // * @param aliasAnno an {@link AnnotationMirror} of an alias annotation
    // * @param aliasMetaAnno the {@link UnitsAlias} meta annotation of the given alias annotation
    // * @return the internal representation unit as an {@link AnnotationMirror}
    // */
    // public AnnotationMirror createInternalAliasUnit(AnnotationMirror aliasAnno,
    // AnnotationMirror aliasMetaAnno) {
    // // check to see if the annotation has already been mapped before
    // // we search based on the string name of the annotation
    // String fullAnnotationName = aliasAnno.toString();
    // for (AnnotationMirror unit : unitsAnnotationMirrorMap.keySet()) {
    // if (fullAnnotationName.contentEquals(unit.toString())) {
    // return unitsAnnotationMirrorMap.get(unit);
    // }
    // }
    //
    // int prefix = 0;
    //
    // Map<String, Integer> exponents = new TreeMap<>();
    // // default all base units to exponent 0
    // for (String bu : baseUnits()) {
    // exponents.put(bu, 0);
    // }
    // // replace default base unit exponents from anno
    // for (AnnotationMirror bu : AnnotationUtils.getElementValueArray(aliasMetaAnno, "value",
    // AnnotationMirror.class, true)) {
    // exponents.put(AnnotationUtils.getElementValue(bu, "unit", String.class, false),
    // AnnotationUtils.getElementValue(bu, "exponent", Integer.class, true));
    // prefix += AnnotationUtils.getElementValue(bu, "prefix", Integer.class, true);
    // }
    //
    // unitsAnnotationMirrorMap.put(aliasAnno,
    // createInternalUnit(aliasAnno.toString(), false, false, prefix, exponents));
    //
    // return unitsAnnotationMirrorMap.get(aliasAnno);
    // }

    /**
     * Returns the internal unit representation for the given annotation if it has been created,
     * null otherwise.
     *
     * @param anno an {@link AnnotationMirror} of an annotation
     * @return the internal representation unit as an {@link AnnotationMirror}
     */
    public AnnotationMirror getInternalAliasUnit(AnnotationMirror anno) {
        // check to see if the annotation has already been mapped before
        for (AnnotationMirror unit : unitsAnnotationMirrorMap.keySet()) {
            if (AnnotationUtils.areSame(anno, unit)) {
                return unitsAnnotationMirrorMap.get(unit);
            }
        }

        return null;
    }

    // /**
    // * creates a normalized UnitsInternal annotation for the given supported annotation (including
    // * aliases), adds the pair to the map, and returns the normalized annotation.
    // */
    // public AnnotationMirror addUnitsAnnotation(AnnotationMirror anno, String originalName,
    // boolean unknownUnits, boolean unitsBottom, int prefixExponent,
    // Map<String, Integer> exponents) {
    // if (unitsAnnotationMirrorMap.containsKey(anno)) {
    // return unitsAnnotationMirrorMap.get(anno);
    // }
    //
    // System.out.println(" === Adding annot to alias " + anno + " hashcode: " + anno.hashCode());
    //
    // AnnotationMirror internalUnit = createInternalUnit(originalName, unknownUnits, unitsBottom,
    // prefixExponent, exponents);
    // unitsAnnotationMirrorMap.put(anno, internalUnit);
    // return internalUnit;
    // }

    public boolean isUnitsAnnotation(BaseAnnotatedTypeFactory realTypeFactory,
            AnnotationMirror anno) {
        return unitsAnnotationMirrorMap.keySet().contains(anno)
                || realTypeFactory.isSupportedQualifier(anno);
    }

    /**
     * Returns an immutable map of surface units annotations mapped to their internal units
     */
    public Map<AnnotationMirror, AnnotationMirror> getUnitsAliasMap() {
        return Collections.unmodifiableMap(unitsAnnotationMirrorMap);
    }

    /**
     * Returns an immutable map of internal units mapped to their surface units annotations
     */
    public Map<AnnotationMirror, AnnotationMirror> getUnitsAliasMapSwapped() {
        return Collections.unmodifiableMap(unitsAnnotationMirrorMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey)));
    }

    private final Map<AnnotationMirror, AnnotationMirror> fillMissingBaseUnitsCache =
            new HashMap<>();

    // Builds a fresh AnnotationMirror for the given annotation with any missing base units filled
    // in
    public AnnotationMirror fillMissingBaseUnits(AnnotationMirror anno) {
        if (AnnotationUtils.areSameByClass(anno, UnitsInternal.class)) {
            if (fillMissingBaseUnitsCache.containsKey(anno)) {
                return fillMissingBaseUnitsCache.get(anno);
            }

            String originalName =
                    AnnotationUtils.getElementValue(anno, "originalName", String.class, true);
            boolean unknownUnits =
                    AnnotationUtils.getElementValue(anno, "unknownUnits", Boolean.class, true);
            boolean unitsBottom =
                    AnnotationUtils.getElementValue(anno, "unitsBottom", Boolean.class, true);
            int prefixExponent =
                    AnnotationUtils.getElementValue(anno, "prefixExponent", Integer.class, true);

            Map<String, Integer> exponents = new HashMap<>();
            // default all base units to exponent 0
            for (String bu : baseUnits()) {
                exponents.put(bu, 0);
            }
            // replace base units with values in annotation
            for (AnnotationMirror bu : AnnotationUtils.getElementValueArray(anno, "baseUnits",
                    AnnotationMirror.class, true)) {
                exponents.put(AnnotationUtils.getElementValue(bu, "unit", String.class, false),
                        AnnotationUtils.getElementValue(bu, "exponent", Integer.class, false));
            }

            AnnotationMirror filledInAM = createInternalUnit(originalName, unknownUnits,
                    unitsBottom, prefixExponent, exponents);

            fillMissingBaseUnitsCache.put(anno, filledInAM);

            return filledInAM;
        } else {
            // not an internal units annotation
            return null;
        }
    }

    // A 1 to 1 mapping between an annotation mirror and its unique typecheck unit.
    private final Map<AnnotationMirror, TypecheckUnit> typecheckUnitCache = new HashMap<>();

    public TypecheckUnit createTypecheckUnit(AnnotationMirror anno) {
        if (typecheckUnitCache.containsKey(anno)) {
            return typecheckUnitCache.get(anno);
        }

        TypecheckUnit unit = new TypecheckUnit();

        if (AnnotationUtils.areSameByClass(anno, UnitsInternal.class)) {
            unit.setOriginalName(
                    AnnotationUtils.getElementValue(anno, "originalName", String.class, true));
            unit.setUnknownUnits(
                    AnnotationUtils.getElementValue(anno, "unknownUnits", Boolean.class, true));
            unit.setUnitsBottom(
                    AnnotationUtils.getElementValue(anno, "unitsBottom", Boolean.class, true));
            unit.setPrefixExponent(
                    AnnotationUtils.getElementValue(anno, "prefixExponent", Integer.class, true));

            Map<String, Integer> exponents = new HashMap<>();
            // default all base units to exponent 0
            for (String bu : baseUnits()) {
                exponents.put(bu, 0);
            }
            // replace base units with values in annotation
            for (AnnotationMirror bu : AnnotationUtils.getElementValueArray(anno, "baseUnits",
                    AnnotationMirror.class, true)) {
                exponents.put(AnnotationUtils.getElementValue(bu, "unit", String.class, false),
                        AnnotationUtils.getElementValue(bu, "exponent", Integer.class, false));
            }

            for (String bu : exponents.keySet()) {
                unit.setExponent(bu, exponents.get(bu));
            }
        } else {
            // not a units annotation
            return null;
        }
        typecheckUnitCache.put(anno, unit);

        return unit;
    }

    public AnnotationMirror createInternalUnit(TypecheckUnit unit) {
        // see if cache already has a mapping, if so return from cache
        for (Entry<AnnotationMirror, TypecheckUnit> entry : typecheckUnitCache.entrySet()) {
            if (unit.equals(entry.getValue())) {
                return entry.getKey();
            }
        }

        // otherwise create an internal unit for the typecheck unit and add to cache
        AnnotationMirror anno = createInternalUnit(unit.getOriginalName(), unit.isUnknownUnits(),
                unit.isUnitsBottom(), unit.getPrefixExponent(), unit.getExponents());

        typecheckUnitCache.put(anno, unit);
        return anno;
    }

    public AnnotationMirror createInternalUnit(String originalName, boolean unknownUnits,
            boolean unitsBottom, int prefixExponent, Map<String, Integer> exponents) {
        // not allowed to set both a UU and UB to true on the same annotation
        assert !(unknownUnits && unitsBottom);

        AnnotationBuilder builder = new AnnotationBuilder(processingEnv, UnitsInternal.class);

        List<AnnotationMirror> expos = new ArrayList<>();
        for (String key : exponents.keySet()) {
            // Construct BaseUnit annotations for each exponent
            AnnotationBuilder buBuilder = new AnnotationBuilder(processingEnv, BaseUnit.class);
            buBuilder.setValue("unit", key);
            buBuilder.setValue("exponent", exponents.get(key));
            expos.add(buBuilder.build());
        }

        // See {@link UnitsInternal}
        // builder.setValue("originalName", originalName); // TODO: set original name
        builder.setValue("unknownUnits", unknownUnits);
        builder.setValue("unitsBottom", unitsBottom);
        builder.setValue("prefixExponent", prefixExponent);
        builder.setValue("baseUnits", expos);
        return builder.build();
    }
}