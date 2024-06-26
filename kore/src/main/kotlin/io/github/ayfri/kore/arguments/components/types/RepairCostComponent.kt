package io.github.ayfri.kore.arguments.components.types

import io.github.ayfri.kore.arguments.components.ComponentsScope
import io.github.ayfri.kore.generated.ComponentTypes
import io.github.ayfri.kore.serializers.InlineSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer

@Serializable(with = RepairCostComponent.Companion.RepairCostComponentSerializer::class)
data class RepairCostComponent(var repairCost: Int) : Component() {
	companion object {
		object RepairCostComponentSerializer : InlineSerializer<RepairCostComponent, Int>(Int.serializer(), RepairCostComponent::repairCost)
	}
}

fun ComponentsScope.repairCost(repairCost: Int) = apply { this[ComponentTypes.REPAIR_COST] = RepairCostComponent(repairCost) }
