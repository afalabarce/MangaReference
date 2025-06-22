package dev.afalabarce.mangaref.presentation.ui.features.common

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalHazeMaterialsApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun ItemCard(
    pictureUri: String,
    pictureHeight: Dp,
    modifier: Modifier = Modifier,
    pictureTransitionKey: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onClick:() -> Unit = {},
    content: @Composable (ColumnScope.() -> Unit) = {}
) {
    ElevatedCard(modifier = modifier, onClick = onClick) {
        ConstraintLayout(
            modifier = Modifier.padding(AppMaterialTheme.dimens.minEndSurface).fillMaxSize()
        ) {
            val (pictureRef, dataCardRef) = createRefs()
            with(sharedTransitionScope) {
                AsyncImage(
                    model = pictureUri,
                    contentDescription = null,
                    modifier = Modifier.sharedElement(
                        sharedContentState =  sharedTransitionScope.rememberSharedContentState(key = pictureTransitionKey),
                        animatedVisibilityScope = animatedContentScope
                    )
                        .constrainAs(pictureRef) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                            height = Dimension.value(pictureHeight)
                            width = Dimension.wrapContent

                        }
                        .clip(RoundedCornerShape(8.dp))

                )
            }
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