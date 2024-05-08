package io.github.ayfri.kore.arguments.components

import io.github.ayfri.kore.arguments.types.resources.EnchantmentArgument
import io.github.ayfri.kore.generated.ComponentTypes
import io.github.ayfri.kore.serializers.SinglePropertySimplifierSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = Enchantments.Companion.EnchantmentsSerializer::class)
data class Enchantments(
	var levels: MutableMap<EnchantmentArgument, Int> = mutableMapOf(),
	@SerialName("show_in_tooltip")
	var showInTooltip: Boolean? = null,
) : Component() {
	companion object {
		data object EnchantmentsSerializer : SinglePropertySimplifierSerializer<Enchantments, Map<EnchantmentArgument, Int>>(
			Enchantments::class,
			Enchantments::levels,
		)
	}
}

fun Components.enchantments(levels: Map<EnchantmentArgument, Int>, showInTooltip: Boolean? = null) =
	apply { this[ComponentTypes.ENCHANTMENTS] = Enchantments(levels.toMutableMap(), showInTooltip) }

fun Components.enchantments(block: Enchantments.() -> Unit) = apply { this[ComponentTypes.ENCHANTMENTS] = Enchantments().apply(block) }

fun Enchantments.enchantment(enchantment: EnchantmentArgument, level: Int) = apply { levels[enchantment] = level }
