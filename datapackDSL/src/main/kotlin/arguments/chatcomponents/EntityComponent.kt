package arguments.chatcomponents

import kotlinx.serialization.Serializable
import net.benwoodworth.knbt.buildNbtCompound
import utils.set

@Serializable
data class EntityComponent(
	var selector: String,
	var separator: String? = null,
) : ChatComponent() {
	override fun toNbtTag() = buildNbtCompound {
		super.toNbtTag().entries.forEach { (key, value) -> if (key != "text") this[key] = value }
		this["selector"] = selector
		separator?.let { this["separator"] = it }
	}
}

fun entityComponent(selector: String, separator: String? = null, block: EntityComponent.() -> Unit = {}) =
	ChatComponents(EntityComponent(selector, separator).apply(block))
