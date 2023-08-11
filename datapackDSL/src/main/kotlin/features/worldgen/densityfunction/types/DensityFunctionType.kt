package features.worldgen.densityfunction.types

import serializers.NamespacedPolymorphicSerializer
import kotlinx.serialization.Serializable

@Serializable(with = DensityFunctionType.Companion.DensityFunctionTypeSerializer::class)
sealed class DensityFunctionType {
	companion object {
		data object DensityFunctionTypeSerializer : NamespacedPolymorphicSerializer<DensityFunctionType>(DensityFunctionType::class)
	}
}
