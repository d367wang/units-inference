#!/bin/bash

ROOT=$(cd $(dirname "$0")/.. && pwd)

echo "$ROOT"/units-inference

CFI="${ROOT}/checker-framework-inference"
UI="${ROOT}/units-inference"

export AFU="${JSR308}/annotation-tools/annotation-file-utilities"
export PATH="${PATH}:${AFU}/scripts"

CHECKER=units.UnitsChecker

SOLVER=units.solvers.backend.UnitsSolverEngine

IS_HACK=true

export CLASSPATH="${UI}/build/classes/java/main:${UI}/build/libs/units-inference.jar:."
export external_checker_classpath="${UI}/build/classes/java/main:${UI}/build/resources/main:${UI}/build/libs/units-inference.jar"

# Inference
$CFI/scripts/inference-dev -m ROUNDTRIP --checker "$CHECKER" --solver "$SOLVER" --solverArgs="collectStatistic=true" --hacks="$IS_HACK" -afud ./annotated "$@"