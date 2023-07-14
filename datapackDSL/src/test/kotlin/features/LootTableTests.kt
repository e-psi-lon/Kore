package features

import DataPack
import arguments.self
import commands.loot
import features.itemmodifiers.functions.conditions
import features.itemmodifiers.functions.enchantRandomly
import features.itemmodifiers.functions.setCount
import features.loottables.*
import features.loottables.entries.functions
import features.loottables.entries.lootTable
import features.predicates.conditions.randomChance
import features.predicates.conditions.weatherCheck
import features.predicates.providers.constant
import functions.load
import generated.Enchantments
import generated.LootTables
import utils.assertsIs

fun DataPack.lootTableTests() {
	val lootTable = lootTable("loot_table") {
		functions {
			enchantRandomly {
				enchantments += Enchantments.LOOTING
			}
		}

		pool {
			rolls = constant(2f)
			bonusRolls = constant(1f)
			conditions {
				weatherCheck(true)
			}

			entries {
				lootTable(LootTables.Gameplay.PIGLIN_BARTERING) {
					conditions {
						weatherCheck(true)
					}

					functions {
						setCount(1f) {
							conditions {
								randomChance(0.5f)
							}
						}
					}
				}
			}

			functions {
				setCount(1f)
			}
		}
	}

	lootTables.last() assertsIs """
		{
			"functions": [
				{
					"function": "minecraft:enchant_randomly",
					"enchantments": [
						"minecraft:looting"
					]
				}
			],
			"pools": [
				{
					"rolls": 2.0,
					"bonus_rolls": 1.0,
					"conditions": [
						{
							"condition": "minecraft:weather_check",
							"raining": true
						}
					],
					"entries": [
						{
							"type": "minecraft:loot_table",
							"name": "minecraft:gameplay/piglin_bartering",
							"functions": [
								{
									"function": "minecraft:set_count",
									"conditions": [
										{
											"condition": "minecraft:random_chance",
											"chance": 0.5
										}
									],
									"count": 1.0
								}
							]
						}
					],
					"functions": [
						{
							"function": "minecraft:set_count",
							"count": 1.0
						}
					]
				}
			]
		}
	""".trimIndent()

	load {
		loot(self(), lootTable)
	}
}
