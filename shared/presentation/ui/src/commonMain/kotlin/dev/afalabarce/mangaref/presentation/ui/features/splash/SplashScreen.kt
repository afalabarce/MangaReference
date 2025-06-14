package dev.afalabarce.mangaref.presentation.ui.features.splash

import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.toIntSize
import dev.afalabarce.mangaref.core.ui.theme.AppMaterialTheme
import dev.afalabarce.mangaref.presentation.ui.Res
import dev.afalabarce.mangaref.presentation.ui.ic_splash
import org.jetbrains.compose.resources.imageResource


@Composable
fun SplashScreen(onFinishLoad: () -> Unit) {
    val background = imageResource(Res.drawable.ic_splash)
    var size by remember { mutableStateOf(Size.Zero) }
    val alphaLayer by animateFloatAsState(
        targetValue = if (size == Size.Zero) 0f else 1f,
        label = "SplashAnimation",
        animationSpec = FloatTweenSpec(duration = 2500),
        finishedListener = { onFinishLoad() },
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppMaterialTheme.colorScheme.background)
            .graphicsLayer {
                size = this.size
                this.alpha = alphaLayer
            }
            .drawBehind {
                drawImage(
                    image = background,
                    dstSize = size.toIntSize(),
                    alpha = alphaLayer
                )
            }
    )
}