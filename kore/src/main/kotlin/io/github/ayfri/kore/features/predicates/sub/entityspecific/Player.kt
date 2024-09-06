package io.github.ayfri.kore.features.predicates.sub.entityspecific

import io.github.ayfri.kore.arguments.enums.Gamemode
import io.github.ayfri.kore.arguments.types.resources.RecipeArgument
import io.github.ayfri.kore.arguments.types.resources.StatisticArgument
import io.github.ayfri.kore.features.advancements.serializers.IntRangeOrIntJson
import io.github.ayfri.kore.features.predicates.sub.Advancements
import io.github.ayfri.kore.features.predicates.sub.Entity
import io.github.ayfri.kore.features.predicates.sub.Statistic
import kotlinx.serialization.Serializable

@Serializable
data class Player(
	var lookingAt: Entity? = null,
	var advancements: Advancements? = null,
	var gamemode: List<Gamemode>? = null,
	var level: IntRangeOrIntJson? = null,
	var recipes: Map<RecipeArgument, Boolean>? = null,
	var stats: Map<StatisticArgument, Statistic>? = null,
) : EntityTypeSpecific()

fun Entity.playerTypeSpecific(block: Player.() -> Unit = {}) = apply {
	typeSpecific = Player().apply(block)
}

fun Player.advancements(block: Advancements.() -> Unit) {
	advancements = Advancements().apply(block)
}

fun Player.gamemodes(vararg gamemodes: Gamemode) {
	gamemode = gamemodes.toList()
}

fun Player.lookingAt(block: Entity.() -> Unit = {}) {
	lookingAt = Entity().apply(block)
}

fun Player.stats(block: MutableMap<StatisticArgument, Statistic>.() -> Unit = {}) {
	stats = buildMap(block)
}

fun MutableMap<StatisticArgument, Statistic>.statistic(statistic: StatisticArgument, block: Statistic.() -> Unit = {}) {
	put(statistic, Statistic().apply(block))
}

fun Player.recipes(vararg recipes: RecipeArgument, block: MutableMap<RecipeArgument, Boolean>.() -> Unit = {}) {
	this.recipes = (this.recipes ?: mutableMapOf()) + buildMap(block) + recipes.associateWith { true }
}
