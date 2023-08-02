package arguments.types.resources

import arguments.Argument
import arguments.types.ResourceLocationArgument
import kotlinx.serialization.Serializable

@Serializable(with = Argument.ArgumentSerializer::class)
interface SoundArgument : ResourceLocationArgument {
	companion object {
		operator fun invoke(name: String, namespace: String = "minecraft") = object : SoundArgument {
			override val name = name
			override val namespace = namespace
		}
	}
}