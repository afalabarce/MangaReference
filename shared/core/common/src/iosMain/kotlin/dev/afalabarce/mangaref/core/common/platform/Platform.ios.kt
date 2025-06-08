package dev.afalabarce.mangaref.core.common.platform

import dev.afalabarce.mangaref.core.common.platform.entities.PlatformData
import dev.afalabarce.mangaref.core.common.platform.enums.PlatformType
import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val platformData: PlatformData
        get() = PlatformData(
            platformType = PlatformType.IOS,
            osVersion = UIDevice.currentDevice.systemVersion
        )
}

actual fun getPlatform(): Platform = IOSPlatform()