package features.advancements

import DataPack
import Generator
import arguments.chatcomponents.ChatComponents
import arguments.chatcomponents.textComponent
import arguments.selector.Advancements
import arguments.types.resources.*
import features.advancements.triggers.AdvancementTriggerCondition
import features.advancements.types.AdvancementsJSONSerializer
import features.advancements.types.Entity
import features.predicates.Predicate
import features.predicates.conditions.PredicateCondition
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import net.benwoodworth.knbt.NbtTag

@Serializable
data class Advancement internal constructor(
	@Transient
	override var fileName: String = "advancement",
	var display: AdvancementDisplay? = null,
	var parent: AdvancementArgument? = null,
	var criteria: Map<String, AdvancementCriterion> = emptyMap(),
	var requirements: List<List<String>>? = null,
	var rewards: AdvancementReward? = null,
	var sendsTelemetryEvent: Boolean? = null,
) : Generator {
	@Transient
	private lateinit var jsonEncoder: Json

	override fun generateJson(dataPack: DataPack) = getJsonEncoder(dataPack).encodeToString(this)

	@OptIn(ExperimentalSerializationApi::class)
	fun getJsonEncoder(dataPack: DataPack) = when {
		::jsonEncoder.isInitialized -> jsonEncoder
		else -> {
			jsonEncoder = Json {
				prettyPrint = dataPack.jsonEncoder.configuration.prettyPrint
				if (prettyPrint) prettyPrintIndent = dataPack.jsonEncoder.configuration.prettyPrintIndent
				serializersModule = SerializersModule {
					contextual(Advancements::class, AdvancementsJSONSerializer)
				}
				namingStrategy = dataPack.jsonEncoder.configuration.namingStrategy
			}
			jsonEncoder
		}
	}
}

fun DataPack.advancement(fileName: String, block: Advancement.() -> Unit = {}): AdvancementArgument {
	advancements += Advancement(fileName).apply(block)
	return AdvancementArgument(fileName, name)
}

fun Advancement.display(icon: AdvancementIcon, title: String = "", description: String = "", block: AdvancementDisplay.() -> Unit = {}) {
	display = AdvancementDisplay(icon, textComponent(title), textComponent(description)).apply(block)
}

fun Advancement.display(
	icon: AdvancementIcon,
	title: ChatComponents,
	description: ChatComponents,
	block: AdvancementDisplay.() -> Unit = {}
) {
	display = AdvancementDisplay(icon, title, description).apply(block)
}

fun Advancement.display(icon: ItemArgument, title: String = "", description: String = "", block: AdvancementDisplay.() -> Unit = {}) {
	display = AdvancementDisplay(AdvancementIcon(icon), textComponent(title), textComponent(description)).apply(block)
}

fun Advancement.display(
	icon: ItemArgument,
	title: ChatComponents,
	description: ChatComponents,
	block: AdvancementDisplay.() -> Unit = {}
) {
	display = AdvancementDisplay(AdvancementIcon(icon), title, description).apply(block)
}

fun AdvancementDisplay.icon(icon: ItemArgument, nbtData: NbtTag? = null) {
	this.icon = AdvancementIcon(icon, nbtData)
}

fun Advancement.criteria(name: String, triggerCondition: AdvancementTriggerCondition, condition: Entity? = null) {
	criteria += name to AdvancementCriterion(triggerCondition, EntityOrPredicates(condition))
}

fun Advancement.criteria(name: String, triggerCondition: AdvancementTriggerCondition, vararg conditions: PredicateCondition) {
	criteria += name to AdvancementCriterion(triggerCondition, EntityOrPredicates(predicateConditions = listOf(*conditions)))
}

fun Advancement.criteria(name: String, triggerCondition: AdvancementTriggerCondition, block: Predicate.() -> Unit) {
	criteria += name to AdvancementCriterion(
		triggerCondition,
		EntityOrPredicates(predicateConditions = Predicate().apply(block).predicateConditions)
	)
}

fun Advancement.requirements(vararg requirements: List<String>) {
	this.requirements = listOf(*requirements)
}

fun Advancement.requirements(vararg requirements: String) {
	this.requirements = listOf(listOf(*requirements))
}

fun Advancement.rewards(block: AdvancementReward.() -> Unit) {
	rewards = AdvancementReward().apply(block)
}

fun Advancement.rewards(
	function: String? = null,
	experience: Int? = null,
	loot: List<LootTableArgument>? = null,
	recipes: List<RecipeArgument>? = null
) {
	rewards = AdvancementReward(experience, function, loot, recipes)
}

fun Advancement.rewards(
	function: FunctionArgument? = null,
	experience: Int? = null,
	loot: List<LootTableArgument>? = null,
	recipes: List<RecipeArgument>? = null
) {
	rewards = AdvancementReward(experience, function?.asString(), loot, recipes)
}
