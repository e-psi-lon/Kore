package features.predicates.conditions

import arguments.types.resources.EnchantmentArgument
import features.predicates.Predicate
import kotlinx.serialization.Serializable

@Serializable
data class TableBonus(
	var enchantment: EnchantmentArgument,
	var chances: List<Float>? = null,
) : PredicateCondition

fun Predicate.tableBonus(enchantment: EnchantmentArgument, chances: List<Float>) {
	predicateConditions += TableBonus(enchantment, chances)
}

fun Predicate.tableBonus(enchantment: EnchantmentArgument, vararg chances: Float) {
	predicateConditions += TableBonus(enchantment, chances.toList())
}

fun Predicate.tableBonus(enchantment: EnchantmentArgument, chances: List<Float>.() -> Unit = {}) {
	predicateConditions += TableBonus(enchantment, buildList(chances))
}
