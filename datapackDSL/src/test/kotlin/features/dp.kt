package features

import configuration
import dataPack
import features.worldgen.biomeTests
import features.worldgen.dimensionTypeTests
import features.worldgen.noiseSettingsTests
import setTestPath

fun featuresTests() = dataPack("features_tests") {
	setTestPath()

	configuration {
		prettyPrint = true
		prettyPrintIndent = "\t"
	}

	advancementTests()
	biomeTests()
	chatTypeTests()
	damageTypeTests()
	dimensionTypeTests()
	itemModifierTests()
	lootTableTests()
	noiseSettingsTests()
	predicateTests()
	recipeTest()
}.generate()
