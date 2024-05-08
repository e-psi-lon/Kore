package io.github.ayfri.kore.features.itemmodifiers.functions

import io.github.ayfri.kore.arguments.components.WritablePage
import io.github.ayfri.kore.features.itemmodifiers.ItemModifier
import io.github.ayfri.kore.features.itemmodifiers.types.Mode
import io.github.ayfri.kore.features.itemmodifiers.types.ModeHandler
import io.github.ayfri.kore.features.predicates.PredicateAsList
import kotlinx.serialization.Serializable

@Serializable
data class SetWritableBookPages(
	override var conditions: PredicateAsList? = null,
	var pages: List<WritablePage> = emptyList(),
	var generation: Int? = null,
) : ItemFunction(), ModeHandler {
	@Serializable
	override lateinit var mode: Mode

	@Serializable
	override var offset: Int? = null

	@Serializable
	override var size: Int? = null
}

fun ItemModifier.setWritableBookPages(
	pages: List<WritablePage> = emptyList(),
	generation: Int? = null,
	block: SetWritableBookPages.() -> Unit = {},
) = SetWritableBookPages(pages = pages, generation = generation).also {
	this.modifiers += it.apply(block)
}

fun SetWritableBookPages.page(text: String, filtered: String? = null) = apply {
	pages += WritablePage(text, filtered)
}