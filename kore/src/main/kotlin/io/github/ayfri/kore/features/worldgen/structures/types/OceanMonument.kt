package io.github.ayfri.kore.features.worldgen.structures.types

import io.github.ayfri.kore.arguments.types.BiomeOrTagArgument
import io.github.ayfri.kore.features.worldgen.structures.*
import io.github.ayfri.kore.serializers.InlinableList
import kotlinx.serialization.Serializable

@Serializable
data class OceanMonument(
	override var biomes: InlinableList<BiomeOrTagArgument> = emptyList(),
	override var step: GenerationStep,
	override var spawnOverrides: SpawnOverrides = SpawnOverrides(),
	override var terrainAdaptation: TerrainAdaptation? = null,
) : StructureType()

fun StructuresBuilder.oceanMonument(
	filename: String = "ocean_monument",
	step: GenerationStep = io.github.ayfri.kore.features.worldgen.structures.GenerationStep.UNDERGROUND_DECORATION,
	init: OceanMonument.() -> Unit = {},
): OceanMonument {
	val oceanMonument = OceanMonument(step = step).apply(init)
	dp.structures += Structure(filename, oceanMonument)
	return oceanMonument
}
