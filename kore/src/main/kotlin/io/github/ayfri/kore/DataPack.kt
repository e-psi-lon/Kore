package io.github.ayfri.kore

import io.github.ayfri.kore.annotations.FunctionsHolder
import io.github.ayfri.kore.arguments.chatcomponents.textComponent
import io.github.ayfri.kore.arguments.types.resources.FunctionArgument
import io.github.ayfri.kore.features.advancements.Advancement
import io.github.ayfri.kore.features.bannerpatterns.BannerPattern
import io.github.ayfri.kore.features.chattype.ChatType
import io.github.ayfri.kore.features.damagetypes.DamageType
import io.github.ayfri.kore.features.itemmodifiers.ItemModifier
import io.github.ayfri.kore.features.loottables.LootTable
import io.github.ayfri.kore.features.predicates.Predicate
import io.github.ayfri.kore.features.recipes.RecipeFile
import io.github.ayfri.kore.features.tags.Tag
import io.github.ayfri.kore.features.worldgen.biome.Biome
import io.github.ayfri.kore.features.worldgen.configuredcarver.ConfiguredCarver
import io.github.ayfri.kore.features.worldgen.configuredfeature.ConfiguredFeature
import io.github.ayfri.kore.features.worldgen.densityfunction.DensityFunction
import io.github.ayfri.kore.features.worldgen.dimension.Dimension
import io.github.ayfri.kore.features.worldgen.dimensiontype.DimensionType
import io.github.ayfri.kore.features.worldgen.flatlevelgeneratorpreset.FlatLevelGeneratorPreset
import io.github.ayfri.kore.features.worldgen.noise.Noise
import io.github.ayfri.kore.features.worldgen.noisesettings.NoiseSettings
import io.github.ayfri.kore.features.worldgen.placedfeature.PlacedFeature
import io.github.ayfri.kore.features.worldgen.processorlist.ProcessorList
import io.github.ayfri.kore.features.worldgen.structures.Structure
import io.github.ayfri.kore.features.worldgen.structureset.StructureSet
import io.github.ayfri.kore.features.worldgen.templatepool.TemplatePool
import io.github.ayfri.kore.features.worldgen.worldpreset.WorldPreset
import io.github.ayfri.kore.functions.Function
import io.github.ayfri.kore.generated.DEFAULT_DATAPACK_FORMAT
import io.github.ayfri.kore.pack.Features
import io.github.ayfri.kore.pack.Filter
import io.github.ayfri.kore.pack.Pack
import io.github.ayfri.kore.pack.PackMCMeta
import io.github.ayfri.kore.serializers.JsonNamingSnakeCaseStrategy
import kotlin.io.path.Path
import java.io.File
import java.nio.file.Path
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@FunctionsHolder
class DataPack(val name: String) {
	val generators = mutableListOf<MutableList<out Generator>>()

	val functions = mutableListOf<Function>()
	val generatedFunctions = mutableListOf<Function>()

	val advancements = registerGenerator<Advancement>()
	val bannerPatterns = registerGenerator<BannerPattern>()
	val biomes = registerGenerator<Biome>()
	val chatTypes = registerGenerator<ChatType>()
	val configuredCarvers = registerGenerator<ConfiguredCarver>()
	val configuredFeatures = registerGenerator<ConfiguredFeature>()
	val damageTypes = registerGenerator<DamageType>()
	val densityFunctions = registerGenerator<DensityFunction>()
	val dimensions = registerGenerator<Dimension>()
	val dimensionTypes = registerGenerator<DimensionType>()
	val flatLevelGeneratorPresets = registerGenerator<FlatLevelGeneratorPreset>()
	val itemModifiers = registerGenerator<ItemModifier>()
	val lootTables = registerGenerator<LootTable>()
	val noises = registerGenerator<Noise>()
	val noiseSettings = registerGenerator<NoiseSettings>()
	val placedFeatures = registerGenerator<PlacedFeature>()
	val predicates = registerGenerator<Predicate>()
	val processorLists = registerGenerator<ProcessorList>()
	val recipes = registerGenerator<RecipeFile>()
	val structures = registerGenerator<Structure>()
	val structureSets = registerGenerator<StructureSet>()
	val tags = registerGenerator<Tag<*>>()
	val templatePools = registerGenerator<TemplatePool>()
	val worldPresets = registerGenerator<WorldPreset>()

	var configuration = Configuration.DEFAULT
	var features = Features()
	var filter: Filter? = null
	var generated = false
		private set
	var iconPath: Path? = null
	var path = Path("out")
	val pack = Pack(DEFAULT_DATAPACK_FORMAT, textComponent("Generated by Kore"))

	private fun <T : Generator> registerGenerator() = mutableListOf<T>().also { generators += it }

	fun addFunction(function: Function): FunctionArgument {
		functions += function
		return function
	}

	fun addGeneratedFunction(function: Function): FunctionArgument {
		generatedFunctions.find { it.lines == function.lines }?.let {
			return@addGeneratedFunction it
		}

		generatedFunctions += function
		return function
	}

	fun generate() {
		val start = System.currentTimeMillis()
		val root = File("$path/$name")
		root.mkdirs()

		val packMCMeta = generatePackMCMetaFile()
		File(root, "pack.mcmeta").writeText(packMCMeta)
		iconPath?.let { File(root, "pack.png").writeBytes(it.toFile().readBytes()) }

		val data = File(root, "data")
		data.mkdirs()

		generators.forEach { generator ->
			generator.forEach { it.generateFile(this) }
		}

		data.generateFunctions("functions", functions.groupBy(Function::namespace))
		data.generateFunctions(
			dirName = "functions/${configuration.generatedFunctionsFolder}",
			functionsMap = generatedFunctions.map {
				it.directory = it.directory.removePrefix(configuration.generatedFunctionsFolder)
				it
			}.groupBy(Function::namespace),
			deleteOldFiles = true
		)
		val end = System.currentTimeMillis()
		println("Generated data pack '$name' in ${end - start}ms in: ${root.absolutePath}")

		generated = true
	}

	fun generatePackMCMetaFile() = jsonEncoder.encodeToString(PackMCMeta(pack, features, filter))

	fun generateZip() {
		// if (!generated) generate()
		if (generated) return
		val start = System.currentTimeMillis()

		val zip = File("$path/$name.zip")
		zip.delete()
		zip.createNewFile()
		zip.outputStream().use { outputStream ->
			ZipOutputStream(outputStream).use { zipOutputStream ->
				zipOutputStream.putNextEntry(ZipEntry("pack.mcmeta"))
				zipOutputStream.write(generatePackMCMetaFile().toByteArray())
				zipOutputStream.closeEntry()

				iconPath?.let {
					zipOutputStream.putNextEntry(ZipEntry("pack.png"))
					zipOutputStream.write(it.toFile().readBytes())
				}

				functions.forEach { function ->
					zipOutputStream.putNextEntry(ZipEntry("data/${function.namespace}/functions/${function.directory}${function.name}.mcfunction"))
					zipOutputStream.write(function.lines.joinToString("\n").toByteArray())
					zipOutputStream.closeEntry()
				}

				generatedFunctions.forEach { function ->
					zipOutputStream.putNextEntry(ZipEntry("data/${function.namespace}/functions/${function.directory}${function.name}.mcfunction"))
					zipOutputStream.write(function.lines.joinToString("\n").toByteArray())
					zipOutputStream.closeEntry()
				}

				generators.forEach { generator ->
					generator.forEach { it.generateZipEntry(this, zipOutputStream) }
				}
			}
		}

		val end = System.currentTimeMillis()
		println("Generated data pack '$name' in ${end - start}ms in: ${zip.absolutePath}")
	}

	private fun File.generateFunctions(
		dirName: String,
		functionsMap: Map<String, List<Function>>,
		deleteOldFiles: Boolean = false,
	) = functionsMap.forEach { (namespace, functions) ->
		val namespaceDir = File(this, namespace)
		namespaceDir.mkdirs()

		if (functions.isEmpty()) return
		val dir = File(let { if (it.name == "data") File(it, this@DataPack.name) else it }, dirName)
		if (deleteOldFiles) dir.deleteRecursively()
		dir.mkdirs()

		functions.forEach { it.generate(dir) }
	}

	@OptIn(ExperimentalSerializationApi::class)
	val jsonEncoder
		get() = Json {
			prettyPrint = configuration.prettyPrint
			if (prettyPrint) prettyPrintIndent = configuration.prettyPrintIndent
			encodeDefaults = true
			explicitNulls = false
			ignoreUnknownKeys = true
			namingStrategy = JsonNamingSnakeCaseStrategy
			useAlternativeNames = false
		}

	companion object {
		const val DEFAULT_GENERATED_FUNCTIONS_FOLDER = "generated_scopes"
	}
}

fun dataPack(name: String, block: DataPack.() -> Unit) = DataPack(name).apply(block)

fun DataPack.configuration(block: Configuration.() -> Unit) {
	configuration = Configuration().apply(block)
}
