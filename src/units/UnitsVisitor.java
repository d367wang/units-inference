package units;

import javax.lang.model.element.AnnotationMirror;
import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import org.checkerframework.framework.source.Result;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.javacutil.AnnotationUtils;
import org.checkerframework.javacutil.TreeUtils;
import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.Tree.Kind;
import com.sun.source.tree.TypeCastTree;
import checkers.inference.InferenceChecker;
import checkers.inference.InferenceMain;
import checkers.inference.InferenceVisitor;
import checkers.inference.SlotManager;
import checkers.inference.VariableAnnotator;
import checkers.inference.model.ArithmeticConstraint.ArithmeticOperationKind;
import checkers.inference.model.ArithmeticVariableSlot;
import checkers.inference.model.ConstraintManager;
import checkers.inference.model.VariableSlot;
import units.representation.UnitsRepresentationUtils;
import units.util.UnitsTypecheckUtils;

public class UnitsVisitor extends InferenceVisitor<UnitsChecker, BaseAnnotatedTypeFactory> {

    public UnitsVisitor(UnitsChecker checker, InferenceChecker ichecker,
            BaseAnnotatedTypeFactory factory, boolean infer) {
        super(checker, ichecker, factory, infer);
    }

    @Override
    public Void visitBinary(BinaryTree binaryTree, Void p) {
        if (infer) {
            SlotManager slotManager = InferenceMain.getInstance().getSlotManager();
            ConstraintManager constraintManager =
                    InferenceMain.getInstance().getConstraintManager();

            AnnotatedTypeMirror lhsATM = atypeFactory.getAnnotatedType(binaryTree.getLeftOperand());
            AnnotatedTypeMirror rhsATM =
                    atypeFactory.getAnnotatedType(binaryTree.getRightOperand());
            // Note: lhs and rhs either contains constant slots or var slots, resolved
            VariableSlot lhs = slotManager.getVariableSlot(lhsATM);
            VariableSlot rhs = slotManager.getVariableSlot(rhsATM);

            Kind kind = binaryTree.getKind();
            switch (binaryTree.getKind()) {
                case PLUS:
                    // if either are string arguments, result is LUB
                    if (TreeUtils.isStringConcatenation(binaryTree)) {
                        // TODO: replace with LUBSlot pending mier's PR
                        VariableSlot lubSlot = slotManager
                                .getVariableSlot(atypeFactory.getAnnotatedType(binaryTree));
                        // Create LUB constraint by default
                        constraintManager.addSubtypeConstraint(lhs, lubSlot);
                        constraintManager.addSubtypeConstraint(rhs, lubSlot);
                        break;
                    } // else create arithmetic constraint
                case MINUS:
                case MULTIPLY:
                case DIVIDE:
                case REMAINDER:
                    ArithmeticOperationKind opKind = ArithmeticOperationKind.fromTreeKind(kind);
                    ArithmeticVariableSlot avsRes = slotManager.getArithmeticVariableSlot(
                            VariableAnnotator.treeToLocation(atypeFactory, binaryTree));
                    constraintManager.addArithmeticConstraint(opKind, lhs, rhs, avsRes);
                    break;
                default:
                    // TODO: replace with LUBSlot pending mier's PR
                    VariableSlot lubSlot =
                            slotManager.getVariableSlot(atypeFactory.getAnnotatedType(binaryTree));
                    // Create LUB constraint by default
                    constraintManager.addSubtypeConstraint(lhs, lubSlot);
                    constraintManager.addSubtypeConstraint(rhs, lubSlot);
                    break;
            }

        } else { // if (atypeFactory instanceof UnitsAnnotatedTypeFactory)
            UnitsAnnotatedTypeFactory atf = (UnitsAnnotatedTypeFactory) atypeFactory;
            UnitsRepresentationUtils unitsRepUtils = UnitsRepresentationUtils.getInstance();

            AnnotatedTypeMirror lhsATM = atf.getAnnotatedType(binaryTree.getLeftOperand());
            AnnotatedTypeMirror rhsATM = atf.getAnnotatedType(binaryTree.getRightOperand());
            AnnotationMirror lhsAM = lhsATM.getEffectiveAnnotationInHierarchy(unitsRepUtils.TOP);
            AnnotationMirror rhsAM = rhsATM.getEffectiveAnnotationInHierarchy(unitsRepUtils.TOP);

            switch (binaryTree.getKind()) {
                case PLUS:
                    // if it is not a string concatenation and the units don't match, issue warning
                    if (!TreeUtils.isStringConcatenation(binaryTree)
                            && !AnnotationUtils.areSame(lhsAM, rhsAM)) {
                        checker.report(Result.failure("addition.unit.mismatch", lhsAM.toString(),
                                rhsAM.toString()), binaryTree);
                    }
                    break;
                case MINUS:
                    if (!AnnotationUtils.areSame(lhsAM, rhsAM)) {
                        checker.report(Result.failure("subtraction.unit.mismatch", lhsAM.toString(),
                                rhsAM.toString()), binaryTree);
                    }
                    break;
                default:
                    break;
            }
        }

        return super.visitBinary(binaryTree, p);
    }

    // permit casts from dimensionless to a unit
    // cast to top are redundant but permitted
    // cast to bottom is usually nonsense, but can appear in inference results... so permitted
    @Override
    public Void visitTypeCast(TypeCastTree node, Void p) {
        // TODO: infer mode
        if (infer) {
            return super.visitTypeCast(node, p);
        }

        // validate "node" instead of "node.getType()" to prevent duplicate errors.
        boolean valid = validateTypeOf(node) && validateTypeOf(node.getExpression());
        if (valid) {
            UnitsRepresentationUtils unitsRepUtils = UnitsRepresentationUtils.getInstance();

            // AnnotationMirror castType =
            // atypeFactory.getAnnotatedType(node).getAnnotationInHierarchy(unitsRepUtils.TOP);
            AnnotationMirror exprType = atypeFactory.getAnnotatedType(node.getExpression())
                    .getAnnotationInHierarchy(unitsRepUtils.TOP);

            if (UnitsTypecheckUtils.unitsEqual(exprType, unitsRepUtils.DIMENSIONLESS)) {
                if (atypeFactory.getDependentTypesHelper() != null) {
                    AnnotatedTypeMirror type = atypeFactory.getAnnotatedType(node);
                    atypeFactory.getDependentTypesHelper().checkType(type, node.getType());
                }

                // perform scan and reduce as per super.super.visitTypeCast()
                Void r = scan(node.getType(), p);
                r = reduce(scan(node.getExpression(), p), r);
                return r;
            }
        }
        return super.visitTypeCast(node, p);
    }

    // Slots created in ATF

    // Constraints created in Visitor

    // see
    // https://github.com/topnessman/immutability/blob/master/src/main/java/pico/inference/PICOInferenceVisitor.java
}
