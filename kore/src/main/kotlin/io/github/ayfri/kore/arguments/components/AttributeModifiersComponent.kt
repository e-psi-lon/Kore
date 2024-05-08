package io.github.ayfri.kore.arguments.components

import io.github.ayfri.kore.arguments.components.data.EquipmentSlot
import io.github.ayfri.kore.arguments.types.literals.UUIDArgument
import io.github.ayfri.kore.arguments.types.resources.AttributeArgument
import io.github.ayfri.kore.commands.AttributeModifierOperation
import io.github.ayfri.kore.generated.ComponentTypes
import io.github.ayfri.kore.serializers.SinglePropertySimplifierSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttributeModifier(
	val type: AttributeArgument,
	val slot: EquipmentSlot? = null,
	val uuid: UUIDArgument,
	val name: String,
	val amount: Double,
	val operation: AttributeModifierOperation,
)

@Serializable(with = AttributeModifiersComponent.Companion.AttributeModifiersComponentSerializer::class)
data class AttributeModifiersComponent(
	val modifiers: MutableList<AttributeModifier>,
	@SerialName("show_in_tooltip")
	var showInTooltip: Boolean? = null,
) : Component() {
	companion object {
		data object AttributeModifiersComponentSerializer : SinglePropertySimplifierSerializer<AttributeModifiersComponent, List<AttributeModifier>>(
			AttributeModifiersComponent::class,
			AttributeModifiersComponent::modifiers,
		)
	}
}

fun Components.attributeModifiers(
	modifiers: List<AttributeModifier>,
	showInTooltip: Boolean? = null,
) = apply { this[ComponentTypes.ATTRIBUTE_MODIFIERS] = AttributeModifiersComponent(modifiers.toMutableList(), showInTooltip) }

fun Components.attributeModifiers(
	vararg modifiers: AttributeModifier,
	showInTooltip: Boolean? = null,
) = attributeModifiers(modifiers.toList(), showInTooltip)

fun Components.attributeModifiers(modifiers: AttributeModifiersComponent.() -> Unit) =
	AttributeModifiersComponent(mutableListOf()).apply(modifiers).let { this[ComponentTypes.ATTRIBUTE_MODIFIERS] = it }

fun AttributeModifiersComponent.modifier(
	type: AttributeArgument,
	slot: EquipmentSlot? = null,
	uuid: UUIDArgument,
	name: String,
	amount: Double,
	operation: AttributeModifierOperation,
) = apply { modifiers += AttributeModifier(type, slot, uuid, name, amount, operation) }
