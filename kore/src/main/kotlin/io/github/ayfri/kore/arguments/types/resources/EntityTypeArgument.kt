package io.github.ayfri.kore.arguments.types.resources

import io.github.ayfri.kore.arguments.Argument
import io.github.ayfri.kore.arguments.types.EntityTypeOrTagArgument
import io.github.ayfri.kore.arguments.types.ResourceLocationArgument
import kotlinx.serialization.Serializable

@Serializable(with = Argument.ArgumentSerializer::class)
interface EntityTypeArgument : ResourceLocationArgument, EntityTypeOrTagArgument {
	companion object {
		operator fun invoke(name: String, namespace: String = "minecraft") = object : EntityTypeArgument {
			override val name = name
			override val namespace = namespace
		}
	}
}
