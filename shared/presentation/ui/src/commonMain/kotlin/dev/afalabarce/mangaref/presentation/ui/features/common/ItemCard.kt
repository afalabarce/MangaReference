package dev.afalabarce.mangaref.presentation.ui.features.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import dev.afalabarce.mangaref.core.ui.theme.AppMaterialTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.CupertinoMaterials
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun ItemCard(
    pictureUri: String,
    pictureHeight: Dp,
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit) = {}
) {
    ElevatedCard(modifier = modifier) {
        ConstraintLayout(
            modifier = Modifier.padding(AppMaterialTheme.dimens.minEndSurface).fillMaxSize()
        ) {
            val (pictureRef, dataCardRef) = createRefs()

            AsyncImage(
                model = pictureUri,
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(pictureRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                        height = Dimension.value(pictureHeight)
                        width = Dimension.wrapContent

                    }
                    .clip(RoundedCornerShape(8.dp))

            )

            Column(
                modifier = Modifier.constrainAs(dataCardRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(pictureRef.start)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
                content = content
            )
        }
    }

}

@OptIn(ExperimentalCoilApi::class)
@Preview
@Composable
fun ItemCardPreview(){
    val backColor = AppMaterialTheme.colorScheme.primary.toArgb()
    val previewHandler = AsyncImagePreviewHandler { ColorImage(backColor) }

    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        ItemCard(
            pictureUri = "https://dragonball-api.com/transformaciones/goku_ssj2.webp",
            pictureHeight = 92.dp
        ) {
            Text("Hola Goku SSJ2")
        }
    }
}