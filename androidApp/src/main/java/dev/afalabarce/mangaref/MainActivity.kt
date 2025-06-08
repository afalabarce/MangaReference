package dev.afalabarce.mangaref

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import dev.afalabarce.mangaref.core.common.platform.AndroidPlatform
import dev.afalabarce.mangaref.presentation.ui.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AndroidPlatform.androidContext = this

        setContent {
            val localView = LocalView.current

            val isDarkMode = isSystemInDarkTheme()

            if (!localView.isInEditMode) {
                val window = (localView.context as Activity).window

                this.changeStatusBarColor(true, Color.Transparent.toArgb())
                WindowCompat.getInsetsController(window, localView).isAppearanceLightStatusBars = !isDarkMode
            }
            App()
        }
    }
}

val isBeforeVanillaIceCream
    get() = Build.VERSION.SDK_INT <= Build.VERSION_CODES.UPSIDE_DOWN_CAKE

@Suppress("Deprecation")
fun Activity.changeStatusBarColor(isLightStatusBar: Boolean, color: Int) {
    if (isBeforeVanillaIceCream) {
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.isAppearanceLightStatusBars = isLightStatusBar
        window.statusBarColor = color
    } else {
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = isLightStatusBar

        window.decorView.setBackgroundColor(color)
        window.insetsController?.hide(android.view.WindowInsets.Type.statusBars())
        window.insetsController?.show(android.view.WindowInsets.Type.statusBars())
    }
}
