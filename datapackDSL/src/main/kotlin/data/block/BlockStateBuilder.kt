package data.block

import arguments.Argument

data class BlockStateBuilder(var name: String = "") {
	var properties = emptyMap<String, String>()

	fun build() = BlockState(name, properties.ifEmpty { null })
}

fun blockState(block: BlockStateBuilder.() -> Unit) = BlockStateBuilder().apply(block).build()
fun blockState(name: String, properties: Map<String, String>) = BlockState(name, properties)
fun blockState(name: String, vararg properties: Pair<String, String>) = BlockState(name, properties.toMap())
fun blockState(name: String, block: BlockStateBuilder.() -> Unit) = BlockStateBuilder().apply(block).build()

fun blockState(name: Argument.Block, block: BlockStateBuilder.() -> Unit) = BlockStateBuilder().apply {
	this.name = name.asId()
	block()
}.build()

fun blockState(name: Argument.Block, properties: Map<String, String>) = BlockState(name.asId(), properties)
fun blockState(name: Argument.Block, vararg properties: Pair<String, String>) = BlockState(name.asId(), properties.toMap())

fun BlockStateBuilder.properties(block: BlockStatePropertiesBuilder.() -> Unit) {
	properties = BlockStatePropertiesBuilder().apply(block).properties
}