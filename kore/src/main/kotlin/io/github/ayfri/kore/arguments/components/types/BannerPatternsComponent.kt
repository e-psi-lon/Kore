package io.github.ayfri.kore.arguments.components.types

import io.github.ayfri.kore.arguments.colors.FormattingColor
import io.github.ayfri.kore.arguments.components.ComponentsScope
import io.github.ayfri.kore.generated.BannerPatterns
import io.github.ayfri.kore.generated.ComponentTypes
import io.github.ayfri.kore.serializers.InlineSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class BannerPatternEntry(
	var pattern: BannerPatterns,
	var color: FormattingColor,
)

@Serializable(with = BannerPatternsComponent.Companion.BannerPatternComponentSerializer::class)
data class BannerPatternsComponent(var list: List<BannerPatternEntry>) : Component() {
	companion object {
		object BannerPatternComponentSerializer : InlineSerializer<BannerPatternsComponent, List<BannerPatternEntry>>(
			ListSerializer(BannerPatternEntry.serializer()),
			BannerPatternsComponent::list
		)
	}
}

fun ComponentsScope.bannerPatterns(patterns: List<BannerPatternEntry>) = apply {
	this[ComponentTypes.BANNER_PATTERNS] = BannerPatternsComponent(patterns)
}

fun ComponentsScope.bannerPatterns(vararg patterns: BannerPatternEntry) = apply {
	this[ComponentTypes.BANNER_PATTERNS] = BannerPatternsComponent(patterns.toList())
}

fun ComponentsScope.bannerPatterns(block: BannerPatternsComponent.() -> Unit) = apply {
	this[ComponentTypes.BANNER_PATTERNS] = BannerPatternsComponent(mutableListOf()).apply(block)
}

fun BannerPatternsComponent.pattern(pattern: BannerPatterns, color: FormattingColor) = apply {
	list += BannerPatternEntry(pattern, color)
}
