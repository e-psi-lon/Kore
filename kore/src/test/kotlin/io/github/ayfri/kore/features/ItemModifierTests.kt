package io.github.ayfri.kore.features

import io.github.ayfri.kore.DataPack
import io.github.ayfri.kore.arguments.WEAPON
import io.github.ayfri.kore.arguments.colors.Color
import io.github.ayfri.kore.arguments.components.customName
import io.github.ayfri.kore.arguments.components.damage
import io.github.ayfri.kore.arguments.enums.MapDecoration
import io.github.ayfri.kore.arguments.types.literals.self
import io.github.ayfri.kore.assertions.assertsIs
import io.github.ayfri.kore.commands.items
import io.github.ayfri.kore.features.itemmodifiers.functions.conditions
import io.github.ayfri.kore.features.itemmodifiers.functions.explorationMap
import io.github.ayfri.kore.features.itemmodifiers.functions.setComponents
import io.github.ayfri.kore.features.itemmodifiers.functions.setInstrument
import io.github.ayfri.kore.features.itemmodifiers.itemModifier
import io.github.ayfri.kore.features.predicates.conditions.randomChance
import io.github.ayfri.kore.features.predicates.conditions.weatherCheck
import io.github.ayfri.kore.functions.load
import io.github.ayfri.kore.generated.Tags

fun DataPack.itemModifierTests() {
	val modifier = itemModifier("test_modifier") {
		explorationMap {
			decoration = MapDecoration.BANNER_BLACK
			destination = Tags.Worldgen.Structure.MINESHAFT
			skipExistingChunks = true

			conditions {
				randomChance(0.5f)
				weatherCheck(true)
			}
		}

		setInstrument(Tags.Instrument.GOAT_HORNS)
	}

	itemModifiers.last() assertsIs """
		[
			{
				"function": "minecraft:exploration_map",
				"conditions": [
					{
						"condition": "minecraft:random_chance",
						"chance": 0.5
					},
					{
						"condition": "minecraft:weather_check",
						"raining": true
					}
				],
				"destination": "worldgen/structure/mineshaft",
				"decoration": "banner_black",
				"skip_existing_chunks": true
			},
			{
				"function": "minecraft:set_instrument",
				"options": "#minecraft:goat_horns"
			}
		]
	""".trimIndent()

	load {
		items {
			modify(self(), WEAPON.MAINHAND, modifier)
		}
	}

	itemModifier("set_components_modifier") {
		setComponents {
			customName("Test", color = Color.BLACK)
			!damage(5)
			!"test"
			setToRemove("test2")
		}
	}

	itemModifiers.last() assertsIs """
		{
			"function": "minecraft:set_components",
			"components": {
				"custom_name": {
					"text": "Test",
					"color": "black"
				},
				"!damage": {},
				"!test": {},
				"!test2": {}
			}
		}
	""".trimIndent()
}
