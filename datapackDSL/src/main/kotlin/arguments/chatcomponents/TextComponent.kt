package arguments.chatcomponents

import arguments.ChatComponents
import arguments.Color
import arguments.nbt
import arguments.set
import commands.asArg
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import net.benwoodworth.knbt.buildNbtCompound

@OptIn(ExperimentalSerializationApi::class)
@Serializable
open class TextComponent constructor(
	@EncodeDefault(EncodeDefault.Mode.NEVER)
	var text: String = "",
	var bold: Boolean? = null,
	var clickEvent: ClickEvent? = null,
	var color: Color? = null,
	var extra: ChatComponents? = null,
	var font: String? = null,
	var hoverEvent: HoverEvent? = null,
	var insertion: String? = null,
	var italic: Boolean? = null,
	var keybind: String? = null,
	var obfuscated: Boolean? = null,
	var strikethrough: Boolean? = null,
	var underlined: Boolean? = null,
) : ChatComponent {
	fun containsOnlyText() = TextComponent(text) == this

	override fun toNbtTag() = buildNbtCompound {
		if (extra == null) this["text"] = text
		color?.let { this["color"] = it.asArg() }
		bold?.let { this["bold"] = it }
		clickEvent?.let { this["clickEvent"] = it.toNbtTag() }
		font?.let { this["font"] = it }
		hoverEvent?.let { this["hoverEvent"] = it.toNbtTag() }
		insertion?.let { this["insertion"] = it }
		italic?.let { this["italic"] = it }
		keybind?.let { this["keybind"] = it }
		obfuscated?.let { this["obfuscated"] = it }
		extra?.let { this["extra"] = it }
		strikethrough?.let { this["strikethrough"] = it }
		underlined?.let { this["underlined"] = it }
	}

	override fun toString() =
		"TextComponent(text='$text', bold=$bold, clickEvent=$clickEvent, color=$color, extra=$extra, font=$font, hoverEvent=$hoverEvent, insertion=$insertion, italic=$italic, keybind=$keybind, obfuscated=$obfuscated, strikethrough=$strikethrough, underlined=$underlined)"

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as TextComponent

		if (text != other.text) return false
		if (bold != other.bold) return false
		if (clickEvent != other.clickEvent) return false
		if (color != other.color) return false
		if (extra != other.extra) return false
		if (font != other.font) return false
		if (hoverEvent != other.hoverEvent) return false
		if (insertion != other.insertion) return false
		if (italic != other.italic) return false
		if (keybind != other.keybind) return false
		if (obfuscated != other.obfuscated) return false
		if (strikethrough != other.strikethrough) return false
		if (underlined != other.underlined) return false

		return true
	}

	override fun hashCode(): Int {
		var result = text.hashCode()
		result = 31 * result + (bold?.hashCode() ?: 0)
		result = 31 * result + (clickEvent?.hashCode() ?: 0)
		result = 31 * result + (color?.hashCode() ?: 0)
		result = 31 * result + (extra?.hashCode() ?: 0)
		result = 31 * result + (font?.hashCode() ?: 0)
		result = 31 * result + (hoverEvent?.hashCode() ?: 0)
		result = 31 * result + (insertion?.hashCode() ?: 0)
		result = 31 * result + (italic?.hashCode() ?: 0)
		result = 31 * result + (keybind?.hashCode() ?: 0)
		result = 31 * result + (obfuscated?.hashCode() ?: 0)
		result = 31 * result + (strikethrough?.hashCode() ?: 0)
		result = 31 * result + (underlined?.hashCode() ?: 0)
		return result
	}
}

fun textComponent(text: String = "", block: TextComponent.() -> Unit = {}) = ChatComponents(TextComponent(text).apply(block))
fun text(text: String = "", block: TextComponent.() -> Unit = {}) = TextComponent(text).apply(block)

fun TextComponent.hoverEvent(action: HoverAction = HoverAction.SHOW_TEXT, block: HoverEvent.() -> Unit) =
	apply { hoverEvent = HoverEvent(action, "".nbt).apply(block) }

fun TextComponent.clickEvent(action: ClickAction = ClickAction.RUN_COMMAND, block: ClickEvent.() -> Unit) =
	apply { clickEvent = ClickEvent(action, "").apply(block) }

fun TextComponent.hoverEvent(text: String, block: TextComponent.() -> Unit = {}) =
	apply { hoverEvent = HoverEvent(HoverAction.SHOW_TEXT, textComponent(text, block).toNbtTag()) }