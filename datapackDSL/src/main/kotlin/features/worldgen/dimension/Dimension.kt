package features.worldgen.dimension

import DataPack
import Generator
import arguments.Argument
import features.worldgen.dimension.generator.Debug
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.encodeToString
import features.worldgen.dimension.generator.Generator as DimensionGenerator

@Serializable
data class Dimension(
	@Transient
	override var fileName: String = "dimension",
	var type: Argument.DimensionType,
	var generator: DimensionGenerator = Debug
) : Generator {
	override fun generateJson(dataPack: DataPack) = dataPack.jsonEncoder.encodeToString(this)
}

/**
 * Creates a new Dimension with [Debug] world generator.
 * See [features.worldgen.dimension.generator.Noise] for using noise (overworld) generator.
 */
fun DataPack.dimension(
	fileName: String = "dimension",
	type: Argument.DimensionType,
	block: Dimension.() -> Unit = {},
): Argument.Dimension {
	dimensions += Dimension(fileName, type).apply(block)
	return Argument.Dimension(fileName, name)
}
