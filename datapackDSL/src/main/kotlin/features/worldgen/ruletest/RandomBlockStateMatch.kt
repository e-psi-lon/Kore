package features.worldgen.ruletest

import data.block.BlockState
import data.block.blockStateStone
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("random_blockstate_match")
data class RandomBlockStateMatch(
	var blockState: BlockState = blockStateStone(),
	var probability: Double = 0.0,
) : RuleTest()

fun randomBlockStateMatch(
	blockState: BlockState = blockStateStone(),
	probability: Double = 0.0,
) = RandomBlockStateMatch(blockState, probability)
