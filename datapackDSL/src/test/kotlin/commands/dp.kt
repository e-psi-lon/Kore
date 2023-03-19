package commands

import dataPack
import functions.load
import setTestPath

val dp = dataPack("unit_tests") {
	setTestPath()
	load {
		cloneTests()
		damageTests()
		effectTests()
		executeTests()
		rideTests()
		tellrawTests()
		titleTests()
		weatherTests()
	}
}

fun runUnitTests() = dp.generate()
