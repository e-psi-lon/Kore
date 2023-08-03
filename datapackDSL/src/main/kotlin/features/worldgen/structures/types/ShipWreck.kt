package features.worldgen.structures.types

import arguments.types.BiomeOrTagArgument
import features.worldgen.biome.types.Spawners
import features.worldgen.structures.GenerationStep
import features.worldgen.structures.Structure
import features.worldgen.structures.StructuresBuilder
import features.worldgen.structures.TerrainAdaptation
import serializers.InlinableList
import kotlinx.serialization.Serializable

@Serializable
data class ShipWreck(
	override var biomes: InlinableList<BiomeOrTagArgument> = emptyList(),
	override var step: GenerationStep,
	override var spawnOverrides: Spawners = Spawners(),
	override var terrainAdaptation: TerrainAdaptation? = null,
	var isBeached: Boolean? = null,
) : StructureType()

fun StructuresBuilder.shipWreck(
	filename: String = "shipwreck",
	step: GenerationStep = GenerationStep.SURFACE_STRUCTURES,
	init: ShipWreck.() -> Unit = {},
): ShipWreck {
	val shipWreck = ShipWreck(step = step).apply(init)
	dp.structures += Structure(filename, shipWreck)
	return shipWreck
}