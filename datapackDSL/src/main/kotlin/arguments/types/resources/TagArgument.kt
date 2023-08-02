package arguments.types.resources

import arguments.Argument
import arguments.types.ResourceLocationArgument
import kotlinx.serialization.Serializable

@Serializable(with = Argument.ArgumentSerializer::class)
interface TagArgument : ResourceLocationArgument {
	companion object {
		operator fun invoke(name: String, namespace: String = "minecraft") = object : TagArgument {
			override val name = name
			override val namespace = namespace
		}
	}
}