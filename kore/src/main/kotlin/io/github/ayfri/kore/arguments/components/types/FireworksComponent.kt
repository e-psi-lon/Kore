package io.github.ayfri.kore.arguments.components.types

import io.github.ayfri.kore.arguments.components.ComponentsScope
import io.github.ayfri.kore.generated.ComponentTypes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FireworksComponent(
	var explosions: List<FireworkExplosionComponent> = emptyList(),
	@SerialName("flight_duration")
	var flightDuration: Int,
) : Component()

fun ComponentsScope.fireworks(explosions: List<FireworkExplosionComponent>, flightDuration: Int) = apply {
	this[ComponentTypes.FIREWORKS] = FireworksComponent(explosions, flightDuration)
}

fun ComponentsScope.fireworks(flightDuration: Int, block: FireworksComponent.() -> Unit) = apply {
	this[ComponentTypes.FIREWORKS] = FireworksComponent(mutableListOf(), flightDuration).apply(block)
}

fun FireworksComponent.explosion(shape: FireworkExplosionShape, block: FireworkExplosionComponent.() -> Unit) = apply {
	explosions += FireworkExplosionComponent(shape).apply(block)
}
