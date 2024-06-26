package io.github.ayfri.kore.arguments.components.types

import io.github.ayfri.kore.arguments.components.ComponentsScope
import io.github.ayfri.kore.generated.ComponentTypes
import io.github.ayfri.kore.serializers.InlineSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer

@Serializable(with = LockComponent.Companion.LockComponentSerializer::class)
data class LockComponent(var name: String) : Component() {
	companion object {
		object LockComponentSerializer : InlineSerializer<LockComponent, String>(
			String.serializer(),
			LockComponent::name
		)
	}
}

fun ComponentsScope.lock(name: String) = apply {
	this[ComponentTypes.LOCK] = LockComponent(name)
}
