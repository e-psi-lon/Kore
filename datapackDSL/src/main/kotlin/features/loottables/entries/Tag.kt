package features.loottables.entries

import arguments.Argument
import features.itemmodifiers.ItemModifier
import features.itemmodifiers.ItemModifierAsList
import features.predicates.Predicate
import features.predicates.PredicateAsList
import kotlinx.serialization.Serializable

@Serializable
data class Tag(
	var name: Argument.Tag,
	var conditions: PredicateAsList? = null,
	var expand: Boolean? = null,
	var functions: ItemModifierAsList? = null,
	var quality: Int? = null,
	var weight: Int? = null,
) : LootEntry

fun LootEntries.tag(name: Argument.Tag, block: Tag.() -> Unit = {}) {
	add(Tag(name).apply(block))
}

fun Tag.conditions(block: Predicate.() -> Unit) {
	conditions = Predicate().apply(block)
}

fun Tag.functions(block: ItemModifier.() -> Unit) {
	functions = ItemModifier().apply(block)
}
