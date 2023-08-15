package commands

import arguments.types.literals.int
import arguments.types.literals.literal
import functions.Function

class Trigger(private val fn: Function, val objective: String) {
	fun add(value: Int) = fn.addLine(command("trigger", literal(objective), literal("add"), int(value)))
	fun set(value: Int) = fn.addLine(command("trigger", literal(objective), literal("set"), int(value)))
	fun remove(value: Int) = add(-value)

	operator fun plusAssign(value: Int) {
		add(value)
	}

	operator fun minusAssign(value: Int) {
		add(-value)
	}
}

fun Function.trigger(objective: String, block: Trigger.() -> Command) = Trigger(this, objective).block()
