package features.worldgen.configuredfeature.ruletest

import arguments.types.resources.BlockArgument
import kotlinx.serialization.Serializable

@Serializable
data class RandomBlockMatch(
	var block: BlockArgument,
	var probability: Double = 0.0,
) : RuleTest()

fun randomBlockMatch(block: BlockArgument, probability: Double = 0.0) = RandomBlockMatch(block, probability)