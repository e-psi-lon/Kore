package features.worldgen.structureset

import serializers.LowercaseSerializer
import kotlinx.serialization.Serializable

@Serializable(FrequencyReductionMethod.Companion.FrequencyReductionMethodSerializer::class)
enum class FrequencyReductionMethod {
	DEFAULT,
	LEGACY_TYPE_1,
	LEGACY_TYPE_2,
	LEGACY_TYPE_3;

	companion object {
		data object FrequencyReductionMethodSerializer : LowercaseSerializer<FrequencyReductionMethod>(entries)
	}
}
