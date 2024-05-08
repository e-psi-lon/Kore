package io.github.ayfri.kore.arguments.components

import io.github.ayfri.kore.arguments.types.resources.EnchantmentArgument
import io.github.ayfri.kore.generated.ComponentTypes
import io.github.ayfri.kore.serializers.SinglePropertySimplifierSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = StoredEnchantments.Companion.StoredEnchantmentsSerializer::class)
data class StoredEnchantments(
	var levels: MutableMap<EnchantmentArgument, Int> = mutableMapOf(),
	@SerialName("show_in_tooltip")
	var showInTooltip: Boolean? = null,
) : Component() {
	companion object {
		data object StoredEnchantmentsSerializer : SinglePropertySimplifierSerializer<StoredEnchantments, Map<EnchantmentArgument, Int>>(
			StoredEnchantments::class,
			StoredEnchantments::levels,
		)
	}
}

fun Components.storedEnchantments(levels: Map<EnchantmentArgument, Int>, showInTooltip: Boolean? = null) =
	apply { this[ComponentTypes.STORED_ENCHANTMENTS] = StoredEnchantments(levels.toMutableMap(), showInTooltip) }

fun Components.storedEnchantments(block: StoredEnchantments.() -> Unit) =
	apply { this[ComponentTypes.STORED_ENCHANTMENTS] = StoredEnchantments().apply(block) }

fun StoredEnchantments.enchantment(enchantment: EnchantmentArgument, level: Int) = apply { levels[enchantment] = level }
