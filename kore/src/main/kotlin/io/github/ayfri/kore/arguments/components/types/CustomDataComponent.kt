package io.github.ayfri.kore.arguments.components.types

import io.github.ayfri.kore.arguments.components.ComponentsScope
import io.github.ayfri.kore.generated.ComponentTypes
import io.github.ayfri.kore.utils.nbt
import net.benwoodworth.knbt.NbtCompoundBuilder
import net.benwoodworth.knbt.NbtTag

typealias CustomDataComponent = CustomComponent

fun ComponentsScope.customData(nbt: NbtTag) = apply { this[ComponentTypes.CUSTOM_DATA] = CustomDataComponent(nbt) }
fun ComponentsScope.customData(block: NbtCompoundBuilder.() -> Unit) = customData(nbt(block))
