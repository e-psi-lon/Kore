package io.github.ayfri.kore.features.itemmodifiers.functions

import io.github.ayfri.kore.features.itemmodifiers.ItemModifier
import io.github.ayfri.kore.features.predicates.PredicateAsList
import io.github.ayfri.kore.serializers.InlinableList
import kotlinx.serialization.Serializable

@Serializable
data class ModifyContents(
	override var conditions: PredicateAsList? = null,
	var component: ContentComponentTypes? = null,
	var modifier: InlinableList<ItemFunction> = emptyList(),
) : ItemFunction()

fun ItemModifier.modifyContents(component: ContentComponentTypes, block: ModifyContents.() -> Unit = {}) =
	ModifyContents(component = component).apply(block).also { modifiers += it }

fun ModifyContents.modifiers(block: ItemModifier.() -> Unit) {
	modifier += ItemModifier().apply(block).modifiers
}
