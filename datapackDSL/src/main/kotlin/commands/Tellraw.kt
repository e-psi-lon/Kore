package commands

import arguments.chatcomponents.ChatComponents
import arguments.chatcomponents.PlainTextComponent
import arguments.chatcomponents.textComponent
import arguments.types.EntityArgument
import functions.Function

fun Function.tellraw(targets: EntityArgument, text: String = "", block: PlainTextComponent.() -> Unit) =
	addLine(command("tellraw", targets, textComponent(text, block).asJsonArg()))

fun Function.tellraw(targets: EntityArgument, message: ChatComponents) = addLine(command("tellraw", targets, message.asJsonArg()))
fun Function.tellraw(targets: EntityArgument, message: String) =
	addLine(command("tellraw", targets, textComponent(message).asJsonArg()))
