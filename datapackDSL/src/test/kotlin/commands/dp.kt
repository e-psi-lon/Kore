package commands

import commands.special.testMacros
import dataPack
import functions.load

val dp = dataPack("unit_tests") {
	load {
		cloneTests()
		damageTests()
		dataTests()
		effectTests()
		executeTests()
		forceLoadTests()
		functionTests()
		gameruleTests()
		itemTests()
		locateTests()
		lootTests()
		particleTests()
		placeTests()
		randomTests()
		recipeTests()
		returnCommand()
		rideTests()
		scheduleTests()
		tellrawTests()
		titleTests()
		weatherTests()
	}

	testMacros()
}

fun runUnitTests() = dp.generate()
