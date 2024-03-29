package io.github.ayfri.kore.arguments.types.resources

import io.github.ayfri.kore.arguments.Argument
import io.github.ayfri.kore.arguments.types.ResourceLocationArgument
import kotlinx.serialization.Serializable

@Serializable(with = Argument.ArgumentSerializer::class)
interface TrimPatternArgument : ResourceLocationArgument {
	companion object {
		operator fun invoke(name: String, namespace: String = "minecraft") = object : TrimPatternArgument {
			override val name = name
			override val namespace = namespace
		}
	}
}
