package dev.afalabarce.mangaref.presentation.ui.features.characters

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import dev.afalabarce.mangaref.core.ui.theme.AppMaterialTheme
import dev.afalabarce.mangaref.domain.models.features.characters.DragonBallCharacter
import dev.afalabarce.mangaref.presentation.ui.Res
import dev.afalabarce.mangaref.presentation.ui.features.common.CHARACTER_PICTURE_TRANSITION_KEY
import dev.afalabarce.mangaref.presentation.ui.features.main.Characters
import dev.afalabarce.mangaref.presentation.ui.ic_dragon_ball_ol
import dev.afalabarce.mangaref.presentation.ui.ic_planet
import dev.afalabarce.mangaref.presentation.ui.ic_transformation
import dev.afalabarce.mangaref.presentation.ui.tab_general
import dev.afalabarce.mangaref.presentation.ui.tab_planets
import dev.afalabarce.mangaref.presentation.ui.tab_transformations
import dev.afalabarce.mangaref.presentation.viewmodels.features.characters.details.CharacterDetailsViewModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun CharacterDetailsScreen(
    bottomAppBarScrollBehavior: BottomAppBarScrollBehavior,
    characterId: Long,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel: CharacterDetailsViewModel = koinViewModel()
) {
    val currentCharacter by viewModel.character.collectAsStateWithLifecycle()
    LaunchedEffect(currentCharacter.isEmptyCharacter()) {
        if (currentCharacter.isEmptyCharacter()) {
            viewModel.loadCharacterById(characterId)
        }
    }

    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .padding(top = AppMaterialTheme.dimens.statusBarSize, bottom = AppMaterialTheme.dimens.minBottomSurface)
        .padding(horizontal = AppMaterialTheme.dimens.startSurface)
    ) {
        val (picture, dataCard) = createRefs()

        with(sharedTransitionScope) {
            AsyncImage(
                model = currentCharacter.image,
                contentDescription = null,
                modifier = Modifier.sharedElement(
                    sharedContentState = sharedTransitionScope.rememberSharedContentState(key = "$CHARACTER_PICTURE_TRANSITION_KEY$characterId"),
                    animatedVisibilityScope = animatedContentScope
                ).constrainAs(picture) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.wrapContent
                    height = Dimension.percent(0.45f)
                }.zIndex(Float.MAX_VALUE),

            )

            ElevatedCard(
                modifier = Modifier
                    .clip(RoundedCornerShape(AppMaterialTheme.dimens.cornerRadius))
                    .constrainAs(dataCard) {
                    top.linkTo(picture.bottom, margin = (-60).dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }.zIndex(Float.MIN_VALUE)
                    .padding(
                        top = AppMaterialTheme.dimens.minTopSurface,
                        start = AppMaterialTheme.dimens.minStartSurface,
                        end = AppMaterialTheme.dimens.minEndSurface,
                        bottom = AppMaterialTheme.dimens.minBottomSurface,
                    ),
                shape = AppMaterialTheme.shapes.medium,
                colors = CardDefaults.elevatedCardColors(containerColor = AppMaterialTheme.colorScheme.secondaryContainer)

            ) {
                CharacterData(character = currentCharacter)
            }
        }
    }
}

@Composable
private fun CharacterData(character: DragonBallCharacter) {

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (characterName, tabPager) = createRefs()

        Text(
            text = character.name,
            modifier = Modifier.constrainAs(characterName) {
                top.linkTo(parent.top, margin = 65.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            style = AppMaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = AppMaterialTheme.colorScheme.onSecondaryContainer,
            textAlign = TextAlign.Center
        )

        TabInfo(
            character = character,
            modifier = Modifier.constrainAs(tabPager) {
                top.linkTo(characterName.bottom, margin = 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
        )
    }
}

@Composable
private fun TabInfo(character: DragonBallCharacter, modifier: Modifier = Modifier) {
    val tabNavController = rememberNavController()
    var selectedTab by rememberSaveable {
        mutableIntStateOf(CharacterSection.GENERAL.ordinal)
    }
    Column(modifier = modifier) {
        PrimaryTabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = selectedTab,
            containerColor = Color.Transparent,
            contentColor = LocalContentColor.current,
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(selectedTab, matchContentSize = true),
                    width = Dp.Unspecified,
                    color = AppMaterialTheme.colorScheme.secondary,
                )
            },
        ) {
            CharacterSection.entries.forEachIndexed { index, section ->
                Tab(
                    selected = selectedTab == index,
                    onClick = {
                        tabNavController.navigate(section.name)
                        selectedTab = index
                        //tabNavController.popBackStack()
                    },
                    selectedContentColor = AppMaterialTheme.colorScheme.secondary,
                    unselectedContentColor = AppMaterialTheme.colorScheme.onSecondaryContainer,
                    text = {
                        Text(
                            text = stringResource(section.stringResource),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = AppMaterialTheme.typography.titleSmall
                        )
                    },
                    icon = {
                        Icon(
                            modifier = Modifier.size(AppMaterialTheme.dimens.smallIconSize),
                            imageVector = vectorResource(section.iconResource),
                            contentDescription = stringResource(section.stringResource),
                        )
                    }
                )
            }
        }
        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = tabNavController,
            startDestination = CharacterSection.GENERAL.name
        ) {
            composable(CharacterSection.GENERAL.name) { stackEntry ->
                GeneralDataInfo(character)
            }
            composable(CharacterSection.TRANSFORMATIONS.name) { stackEntry ->
                TransformationsDataInfo(character)
            }
            composable(CharacterSection.PLANETS.name) { stackEntry ->
                PlanetsDataInfo(character)
            }
        }
    }


}

@Composable
private fun GeneralDataInfo(character: DragonBallCharacter) {
    Text(text = character.name)
}

@Composable
private fun TransformationsDataInfo(character: DragonBallCharacter) {
    LazyRow(modifier = Modifier.fillMaxSize()) {
        items(character.transformations, key = { it.id }) {
            ElevatedCard (modifier = Modifier.padding(AppMaterialTheme.dimens.minBottomSurface)){
                AsyncImage(
                    model = it.image,
                    contentDescription = null,
                    modifier = Modifier.height(200.dp)
                )
                Text(text = it.name, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
private fun PlanetsDataInfo(character: DragonBallCharacter) {
    Text(text = character.name)
}

enum class CharacterSection(val stringResource: StringResource, val iconResource: DrawableResource, val isTint: Boolean) {
    GENERAL (Res.string.tab_general, Res.drawable.ic_dragon_ball_ol, false),
    TRANSFORMATIONS(Res.string.tab_transformations, Res.drawable.ic_transformation, true),
    PLANETS(Res.string.tab_planets, Res.drawable.ic_planet, true),
}