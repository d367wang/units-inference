#!/bin/bash

java -classpath /home/jeff/jsr308/units-inference/corpus/jblas/target/classes::/home/jeff/jsr308/checker-framework-inference/dist/checker.jar:/home/jeff/jsr308/checker-framework-inference/dist/plume.jar:/home/jeff/jsr308/checker-framework-inference/dist/checker-framework-inference.jar:/home/jeff/jsr308/units-inference/bin \
    checkers.inference.InferenceLauncher \
    --solverArgs collectStatistic=true,solver=Z3Int \
    --checker units.UnitsChecker \
    --solver units.solvers.backend.UnitsSolverEngine \
    --mode INFER \
    --hacks=true \
    --targetclasspath /home/jeff/jsr308/units-inference/corpus/jblas/target/classes: \
    --logLevel=INFO \
    --jaifFile logs/infer_result_0.jaif \
    -afud /home/jeff/jsr308/units-inference/corpus/jblas/annotated \
    /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/ranges/package-info.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/benchmark/Benchmark.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/ranges/AllRange.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/NativeBlas.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/benchmark/JavaFloatMultiplicationBenchmark.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/benchmark/JavaDoubleMultiplicationBenchmark.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/benchmark/NativeFloatMultiplicationBenchmark.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/exceptions/LapackPositivityException.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/Trigonometry.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/benchmark/package-info.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/util/LibraryLoader.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/ranges/IntervalRange.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/package-info.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/FloatFunction.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/exceptions/LapackConvergenceException.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/NativeBlasLibraryLoader.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/ConvertsToFloatMatrix.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/Geometry.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/exceptions/package-info.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/util/Permutations.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/Decompose.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/benchmark/Timer.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/Info.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/util/ArchFlavor.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/util/Functions.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/ComplexFloat.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/exceptions/LapackArgumentException.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/MatrixFunctions.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/ComplexDoubleMatrix.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/ranges/PointRange.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/util/Random.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/FloatMatrix.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/Eigen.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/DoubleMatrix.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/exceptions/UnsupportedArchitectureException.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/ComplexDouble.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/ComplexFloatMatrix.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/Singular.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/JavaBlas.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/ConvertsToDoubleMatrix.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/DoubleFunction.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/benchmark/BenchmarkResult.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/benchmark/NativeDoubleMultiplicationBenchmark.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/benchmark/Main.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/util/SanityChecks.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/ranges/Range.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/Solve.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/exceptions/SizeException.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/ranges/IndicesRange.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/exceptions/NoEigenResultException.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/ranges/RangeUtils.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/exceptions/LapackSingularityException.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/util/Logger.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/util/package-info.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/SimpleBlas.java /home/jeff/jsr308/units-inference/corpus/jblas/src/main/java/org/jblas/exceptions/LapackException.java


# java -classpath /home/jeff/jsr308/units-inference/corpus/JLargeArrays/target/classes::/home/jeff/jsr308/checker-framework-inference/dist/checker.jar:/home/jeff/jsr308/checker-framework-inference/dist/plume.jar:/home/jeff/jsr308/checker-framework-inference/dist/checker-framework-inference.jar:/home/jeff/jsr308/units-inference/bin \
#     checkers.inference.InferenceLauncher \
#     --solverArgs collectStatistic=true,solver=Z3Int \
#     --checker units.UnitsChecker \
#     --solver checkers.inference.solver.DebugSolver \
#     --mode ROUNDTRIP \
#     --hacks=true \
#     --targetclasspath /home/jeff/jsr308/units-inference/corpus/JLargeArrays/target/classes: \
#     --logLevel=INFO \
#     --jaifFile logs/infer_result_0.jaif \
#     -afud /home/jeff/jsr308/units-inference/corpus/JLargeArrays/annotated \
#     /home/jeff/jsr308/units-inference/corpus/JLargeArrays/src/main/java/pl/edu/icm/jlargearrays/ObjectLargeArray.java /home/jeff/jsr308/units-inference/corpus/JLargeArrays/src/main/java/pl/edu/icm/jlargearrays/DoubleLargeArray.java /home/jeff/jsr308/units-inference/corpus/JLargeArrays/src/main/java/pl/edu/icm/jlargearrays/ByteLargeArray.java /home/jeff/jsr308/units-inference/corpus/JLargeArrays/src/main/java/pl/edu/icm/jlargearrays/LongLargeArray.java /home/jeff/jsr308/units-inference/corpus/JLargeArrays/src/main/java/pl/edu/icm/jlargearrays/ComplexFloatLargeArray.java /home/jeff/jsr308/units-inference/corpus/JLargeArrays/src/main/java/pl/edu/icm/jlargearrays/LargeArrayType.java /home/jeff/jsr308/units-inference/corpus/JLargeArrays/src/main/java/pl/edu/icm/jlargearrays/ComplexDoubleLargeArray.java /home/jeff/jsr308/units-inference/corpus/JLargeArrays/src/main/java/pl/edu/icm/jlargearrays/Utilities.java /home/jeff/jsr308/units-inference/corpus/JLargeArrays/src/main/java/pl/edu/icm/jlargearrays/MemoryCounter.java /home/jeff/jsr308/units-inference/corpus/JLargeArrays/src/main/java/pl/edu/icm/jlargearrays/FloatLargeArray.java /home/jeff/jsr308/units-inference/corpus/JLargeArrays/src/main/java/pl/edu/icm/jlargearrays/StringLargeArray.java /home/jeff/jsr308/units-inference/corpus/JLargeArrays/src/main/java/pl/edu/icm/jlargearrays/IntLargeArray.java /home/jeff/jsr308/units-inference/corpus/JLargeArrays/src/main/java/pl/edu/icm/jlargearrays/LogicLargeArray.java /home/jeff/jsr308/units-inference/corpus/JLargeArrays/src/main/java/pl/edu/icm/jlargearrays/ShortLargeArray.java /home/jeff/jsr308/units-inference/corpus/JLargeArrays/src/main/java/pl/edu/icm/jlargearrays/Benchmark.java /home/jeff/jsr308/units-inference/corpus/JLargeArrays/src/main/java/pl/edu/icm/jlargearrays/LargeArray.java
# ^ 1 error crash

# java -classpath /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/target/classes::/home/jeff/jsr308/checker-framework-inference/dist/checker.jar:/home/jeff/jsr308/checker-framework-inference/dist/plume.jar:/home/jeff/jsr308/checker-framework-inference/dist/checker-framework-inference.jar:/home/jeff/jsr308/units-inference/bin \
#     checkers.inference.InferenceLauncher \
#     --solverArgs collectStatistic=true,solver=Z3Int \
#     --checker units.UnitsChecker \
#     --solver checkers.inference.solver.DebugSolver \
#     --mode ROUNDTRIP \
#     --hacks=true \
#     --targetclasspath /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/target/classes: \
#     --logLevel=INFO \
#     --jaifFile logs/infer_result_0.jaif \
#     -afud /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/annotated \
#     /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/shapes/BoxShape.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/constraint/ContactPoint.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/broadphase/Utils.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/broadphase/BroadPhaseAlgorithm.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/constraint/SliderJointInfo.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/engine/Island.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/broadphase/SweepAndPruneAlgorithm.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/body/CollisionBody.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/engine/ProfileSample.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/constraint/JointType.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/configuration/JointsPositionCorrectionTechnique.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/narrowphase/EPA/Utils.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/constraint/JointListElement.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/engine/ProfileNode.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/engine/ContactManifoldSolver.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/constraint/JointInfo.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/engine/OverlappingPair.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/narrowphase/EPA/TriangleEPA.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/engine/Impulse.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/narrowphase/SphereVsSphereAlgorithm.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/mathematics/Matrix3x3.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/broadphase/PairManager.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/body/RigidBody.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/engine/CollisionWorld.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/configuration/ContactsPositionCorrectionTechnique.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/engine/ContactSolver.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/CollisionDetection.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/engine/Profiler.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/broadphase/BoxAABB.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/engine/EventListener.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/engine/ConstraintSolver.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/mathematics/Matrix2x2.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/constraint/Joint.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/constraint/HingeJointInfo.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/body/Body.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/broadphase/EndPoint.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/broadphase/AABBInt.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/BodyIndexPair.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/mathematics/Vector3.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/broadphase/NoBroadPhaseAlgorithm.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/engine/ContactManifoldListElement.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/engine/DynamicsWorld.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/broadphase/BodyPair.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/shapes/CylinderShape.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/shapes/ConvexMeshShape.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/narrowphase/GJK/Simplex.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/narrowphase/EPA/TriangleComparison.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/configuration/Defaults.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/constraint/BallAndSocketJointInfo.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/narrowphase/NarrowPhaseAlgorithm.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/shapes/AABB.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/constraint/HingeJoint.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/narrowphase/EPA/EPAAlgorithm.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/shapes/ConeShape.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/mathematics/Mathematics.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/narrowphase/EPA/EdgeEPA.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/engine/Timer.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/engine/ProfileNodeIterator.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/shapes/CollisionShapeType.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/shapes/CollisionShape.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/constraint/FixedJoint.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/BroadPhasePair.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/shapes/CapsuleShape.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/narrowphase/GJK/GJKAlgorithm.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/shapes/SphereShape.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/mathematics/Transform.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/engine/ContactManifold.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/engine/Material.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/constraint/FixedJointInfo.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/constraint/SliderJoint.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/mathematics/Quaternion.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/constraint/BallAndSocketJoint.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/engine/ContactPointSolver.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/mathematics/Vector2.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/constraint/ContactPointInfo.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/engine/ConstraintSolverData.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/collision/narrowphase/EPA/TrianglesStore.java /home/jeff/jsr308/units-inference/corpus/jReactPhysics3D/src/main/java/net/smert/jreactphysics3d/ReactPhysics3D.java
# ^ 1 error crash

