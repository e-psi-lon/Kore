package io.github.ayfri.kore.arguments.types.resources

import io.github.ayfri.kore.arguments.Argument
import io.github.ayfri.kore.arguments.types.ResourceLocationArgument
import io.github.ayfri.kore.arguments.types.TrimMaterialOrTagArgument
import kotlinx.serialization.Serializable

@Serializable(with = Argument.ArgumentSerializer::class)
interface TrimMaterialArgument : ResourceLocationArgument, TrimMaterialOrTagArgument {
	companion object {
		operator fun invoke(name: String, namespace: String = "minecraft") = object : TrimMaterialArgument {
			override val name = name
			override val namespace = namespace
		}
	}
}
