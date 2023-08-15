import annotations.FunctionsHolder
import arguments.chatcomponents.textComponent
import arguments.types.resources.FunctionArgument
import features.advancements.Advancement
import features.chattype.ChatType
import features.damagetypes.DamageType
import features.itemmodifiers.ItemModifier
import features.loottables.LootTable
import features.predicates.Predicate
import features.recipes.RecipeFile
import features.tags.Tag
import features.worldgen.biome.Biome
import features.worldgen.configuredcarver.ConfiguredCarver
import features.worldgen.configuredfeature.ConfiguredFeature
import features.worldgen.densityfunction.DensityFunction
import features.worldgen.dimension.Dimension
import features.worldgen.dimensiontype.DimensionType
import features.worldgen.flatlevelgeneratorpreset.FlatLevelGeneratorPreset
import features.worldgen.noise.Noise
import features.worldgen.noisesettings.NoiseSettings
import features.worldgen.placedfeature.PlacedFeature
import features.worldgen.processorlist.ProcessorList
import features.worldgen.structures.Structure
import features.worldgen.structureset.StructureSet
import features.worldgen.templatepool.TemplatePool
import features.worldgen.worldpreset.WorldPreset
import functions.Function
import kotlin.io.path.Path
import pack.Features
import pack.Filter
import pack.Pack
import pack.PackMCMeta
import serializers.JsonNamingSnakeCaseStrategy
import java.io.File
import java.nio.file.Path
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@FunctionsHolder
class DataPack(val name: String) {
	val functions = mutableListOf<Function>()
	val generatedFunctions = mutableListOf<Function>()
	val generators = mutableListOf<MutableList<out Generator>>()

	val advancements = registerGenerator<Advancement>()
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
	val tags = registerGenerator<Tag>()
	val templatePools = registerGenerator<TemplatePool>()
	val worldPresets = registerGenerator<WorldPreset>()

	var configuration = Configuration.DEFAULT
	var features = Features()
	var filter: Filter? = null
	var iconPath: Path? = null
	var path = Path("out")
	val pack = Pack(15, textComponent("Generated by DataPackDSL"))

	var generated = false
		private set

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

	@Deprecated(
		"Generation to zip is for now not working fine with Minecraft, please use generate() instead",
		ReplaceWith("generate()"),
		DeprecationLevel.WARNING
	)
	fun generateZip() = generate()

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
