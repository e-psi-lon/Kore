package io.github.ayfri.kore.arguments.components

import io.github.ayfri.kore.generated.ComponentTypes
import io.github.ayfri.kore.serializers.InlineSerializer
import io.github.ayfri.kore.utils.nbt
import net.benwoodworth.knbt.NbtCompound
import net.benwoodworth.knbt.NbtCompoundBuilder
import kotlinx.serialization.Serializable

@Serializable(with = BucketEntityDataComponent.Companion.BucketEntityDataComponentSerializer::class)
data class BucketEntityDataComponent(var data: NbtCompound) : Component() {
	companion object {
		object BucketEntityDataComponentSerializer : InlineSerializer<BucketEntityDataComponent, NbtCompound>(
			NbtCompound.serializer(),
			BucketEntityDataComponent::data
		)
	}
}

fun Components.bucketEntityData(data: NbtCompound) = apply {
	this[ComponentTypes.BUCKET_ENTITY_DATA] = BucketEntityDataComponent(data)
}

fun Components.bucketEntityData(block: NbtCompoundBuilder.() -> Unit) = apply {
	this[ComponentTypes.BUCKET_ENTITY_DATA] = BucketEntityDataComponent(nbt(block))
}