package io.github.ayfri.kore.features.itemmodifiers.types

import kotlinx.serialization.Serializable

interface ModeHandler {
	@Serializable
	var mode: Mode

	@Serializable
	var offset: Int?

	@Serializable
	var size: Int?

	fun mode(mode: Mode, offset: Int? = null, size: Int? = null) = when (mode) {
		Mode.REPLACE_ALL -> {
			this.mode = mode
			this.offset = null
			this.size = null
		}

		Mode.REPLACE_SECTION -> {
			this.mode = mode
			this.offset = offset
			this.size = size
		}

		Mode.INSERT -> {
			this.mode = mode
			this.offset = offset
			this.size = null
		}

		Mode.APPEND -> {
			this.mode = mode
			this.offset = null
			this.size = null
		}
	}
}
