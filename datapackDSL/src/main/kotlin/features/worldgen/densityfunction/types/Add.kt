package features.worldgen.densityfunction.types

import arguments.types.resources.worldgen.DensityFunctionArgument
import features.worldgen.densityfunction.DensityFunctionOrDouble
import features.worldgen.densityfunction.densityFunctionOrDouble
import kotlinx.serialization.Serializable

@Serializable
data class Add(
	var argument1: DensityFunctionOrDouble = densityFunctionOrDouble(0.0),
	var argument2: DensityFunctionOrDouble = densityFunctionOrDouble(0.0),
) : DensityFunctionType()

fun add(constant: Double, constant2: Double) = Add(densityFunctionOrDouble(constant), densityFunctionOrDouble(constant2))
fun add(constant: Double, reference: DensityFunctionArgument) = Add(densityFunctionOrDouble(constant), densityFunctionOrDouble(reference))
fun add(reference: DensityFunctionArgument, constant: Double) = Add(densityFunctionOrDouble(reference), densityFunctionOrDouble(constant))
fun add(reference: DensityFunctionArgument, reference2: DensityFunctionArgument) =
	Add(densityFunctionOrDouble(reference), densityFunctionOrDouble(reference2))