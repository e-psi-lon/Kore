package features.advancements

import features.advancements.types.Location
import features.predicates.conditions.PredicateCondition
import serializers.ToStringSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encoding.Encoder

@Serializable(with = LocationOrPredicates.Companion.LocationOrPredicatesSerializer::class)
data class LocationOrPredicates(
	var legacyLocation: Location? = null,
	var predicateConditions: List<PredicateCondition> = emptyList(),
) {
	companion object {
		object LocationOrPredicatesSerializer : ToStringSerializer<LocationOrPredicates>() {
			override fun serialize(encoder: Encoder, value: LocationOrPredicates) {
				when {
					value.legacyLocation != null -> encoder.encodeSerializableValue(Location.serializer(), value.legacyLocation!!)
					else -> encoder.encodeSerializableValue(
						ListSerializer(kotlinx.serialization.serializer<PredicateCondition>()),
						value.predicateConditions
					)
				}
			}
		}
	}
}
