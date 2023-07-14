package features

import DataPack
import arguments.self
import commands.damage
import features.damagetypes.damageType
import features.damagetypes.types.DeathMessageType
import features.damagetypes.types.Effects
import features.damagetypes.types.Scaling
import functions.load
import utils.assertsIs

fun DataPack.damageTypeTests() {
	val damageType = damageType("test_damage_type", "test_message_id", Scaling.NEVER) {
		exhaustion = 1f
		effects = Effects.BURNING
		deathMessageType = DeathMessageType.DEFAULT
	}
	damageTypes.last() assertsIs """
		{
			"message_id": "test_message_id",
			"exhaustion": 1.0,
			"scaling": "never",
			"effects": "burning",
			"death_message_type": "default"
		}
	""".trimIndent()

	load {
		damage(self(), 1f, damageType)
	}
}
