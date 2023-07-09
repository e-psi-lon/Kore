package features.advancements

import features.advancements.types.Entity
import features.predicates.conditions.PredicateCondition
import features.predicates.conditions.PredicateConditionSurrogate
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encoding.Encoder
import serializers.ToStringSerializer

@Serializable(with = EntityOrPredicates.Companion.EntityOrPredicatesSerializer::class)
data class EntityOrPredicates(
	var legacyEntity: Entity? = null,
	var predicateConditions: List<PredicateCondition>? = null,
) {
	companion object {
		object EntityOrPredicatesSerializer : ToStringSerializer<EntityOrPredicates>() {
			override fun serialize(encoder: Encoder, value: EntityOrPredicates) {
				when {
					value.legacyEntity != null -> encoder.encodeSerializableValue(Entity.serializer(), value.legacyEntity!!)
					value.predicateConditions != null -> encoder.encodeSerializableValue(
						ListSerializer(PredicateConditionSurrogate.Companion.PredicateConditionSerializer),
						value.predicateConditions!!
					)

					else -> error("EntityOrPredicates must have either an Entity or predicates")
				}
			}
		}
	}
}
