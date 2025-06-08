package dev.afalabarce.mangaref.core.common.platform.entities

import dev.afalabarce.mangaref.core.common.platform.enums.PlatformType

data class PlatformData(
    val platformType: PlatformType,
    val osVersion: String
) {
    override fun toString(): String = "${platformType.name} v. $osVersion"
}