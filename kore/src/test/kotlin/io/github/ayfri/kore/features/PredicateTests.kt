package io.github.ayfri.kore.features

import io.github.ayfri.kore.DataPack
import io.github.ayfri.kore.arguments.WEAPON
import io.github.ayfri.kore.arguments.components.matchers.enchantments
import io.github.ayfri.kore.arguments.enums.Gamemode
import io.github.ayfri.kore.arguments.numbers.ranges.rangeOrInt
import io.github.ayfri.kore.assertions.assertsIs
import io.github.ayfri.kore.commands.execute.execute
import io.github.ayfri.kore.features.predicates.conditions.*
import io.github.ayfri.kore.features.predicates.predicate
import io.github.ayfri.kore.features.predicates.sub.*
import io.github.ayfri.kore.features.predicates.sub.entityspecific.Player
import io.github.ayfri.kore.features.predicates.sub.item.enchantment
import io.github.ayfri.kore.functions.function
import io.github.ayfri.kore.generated.*
import io.github.ayfri.kore.utils.set

fun DataPack.predicateTests() {
	val myPredicate = predicate("test1") {
		matchTool {
			item(Items.DIAMOND_PICKAXE)
		}

		allOf {
			randomChance(0.5f)
			weatherCheck(raining = true, thundering = false)
		}
	}

	predicates.last() assertsIs """
		[
			{
				"condition": "minecraft:match_tool",
				"predicate": {
					"items": "minecraft:diamond_pickaxe"
				}
			},
			{
				"condition": "minecraft:all_of",
				"terms": [
					{
						"condition": "minecraft:random_chance",
						"chance": 0.5
					},
					{
						"condition": "minecraft:weather_check",
						"raining": true,
						"thundering": false
					}
				]
			}
		]
	""".trimIndent()

	function("test") {
		execute {
			ifCondition {
				predicate(myPredicate)
			}

			run {
				debug("predicate validated !")
			}
		}
	}

	predicate("test2") {
		entityProperties {
			effects {
				this[Effects.INVISIBILITY] = effect {
					amplifier = rangeOrInt(1)
				}
			}

			equipment {
				mainHand = itemStack(Items.DIAMOND_SWORD)
			}

			flags {
				isBaby = true
			}

			location {
				block {
					blocks(Blocks.STONE)
				}
			}

			nbt {
				this["foo"] = "bar"
			}

			passenger {
				team = "foo"
			}

			slots {
				this[WEAPON.MAINHAND] = itemStack(Items.DIAMOND_SWORD)
			}

			steppingOn {
				blocks(Blocks.STONE)
			}

			type(EntityTypes.MARKER)

			typeSpecific = Player(gamemode = Gamemode.SURVIVAL)

			vehicle {
				distance {
					x(1f..4f)
					z(1f)
				}
			}
		}
	}

	predicates.last() assertsIs """
		{
			"condition": "minecraft:entity_properties",
			"predicate": {
				"effects": {
					"minecraft:invisibility": {
						"amplifier": 1
					}
				},
				"equipment": {
					"mainhand": {
						"items": "minecraft:diamond_sword"
					}
				},
				"flags": {
					"is_baby": true
				},
				"location": {
					"block": {
						"blocks": "minecraft:stone"
					}
				},
				"nbt": {
					"foo": "bar"
				},
				"passenger": {
					"team": "foo"
				},
				"slots": {
					"weapon.mainhand": {
						"items": "minecraft:diamond_sword"
					}
				},
				"stepping_on": {
					"blocks": "minecraft:stone"
				},
				"type": "minecraft:marker",
				"type_specific": {
					"type": "minecraft:player",
					"gamemode": "survival"
				},
				"vehicle": {
					"distance": {
						"x": {
							"min": 1.0,
							"max": 4.0
						},
						"z": {
							"min": 1.0,
							"max": 1.0
						}
					}
				}
			}
		}
	""".trimIndent()

	predicate("test3") {
		matchTool {
			item(Items.DIAMOND_PICKAXE)
			predicates {
				enchantments(enchantment(Enchantments.EFFICIENCY))
			}
		}
	}

	predicates.last() assertsIs """
		{
			"condition": "minecraft:match_tool",
			"predicate": {
				"items": "minecraft:diamond_pickaxe",
				"predicates": {
					"minecraft:enchantments": [
						{
							"enchantment": "minecraft:efficiency"
						}
					]
				}
			}
		}
	""".trimIndent()
}
