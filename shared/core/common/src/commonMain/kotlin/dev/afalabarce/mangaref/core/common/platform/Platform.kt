package dev.afalabarce.mangaref.core.common.platform

import dev.afalabarce.mangaref.core.common.platform.entities.PlatformData

interface Platform {
    val platformData: PlatformData
}

expect fun getPlatform(): Platform