package features.worldgen.templatepool.elements

import arguments.types.resources.worldgen.ProcessorListArgument
import arguments.types.resources.worldgen.StructureArgument
import features.worldgen.templatepool.Projection
import features.worldgen.templatepool.TemplatePoolEntry
import kotlinx.serialization.Serializable

@Serializable
data class LegacySinglePoolElement(
	var projection: Projection = Projection.RIGID,
	var location: StructureArgument,
	var processors: ProcessorListArgument,
) : TemplatePoolElement()

fun MutableList<TemplatePoolEntry>.legacySingle(
	weight: Int = 0,
	projection: Projection = Projection.RIGID,
	location: StructureArgument,
	processors: ProcessorListArgument,
) = run {
	this += TemplatePoolEntry(weight, LegacySinglePoolElement(projection, location, processors))
}

fun MutableList<TemplatePoolElement>.legacySingle(
	projection: Projection = Projection.RIGID,
	location: StructureArgument,
	processors: ProcessorListArgument,
) = run {
	this += LegacySinglePoolElement(projection, location, processors)
}
