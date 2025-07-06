package dev.afalabarce.mangaref.presentation.ui.features.characters

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBarScrollBehavior
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import dev.afalabarce.mangaref.presentation.ui.affiliation_title
import dev.afalabarce.mangaref.presentation.ui.description_label
import dev.afalabarce.mangaref.presentation.ui.features.common.CHARACTER_PICTURE_TRANSITION_KEY
import dev.afalabarce.mangaref.presentation.ui.features.common.asKiValue
import dev.afalabarce.mangaref.presentation.ui.gender_label
import dev.afalabarce.mangaref.presentation.ui.generic_ki_levels_section_title
import dev.afalabarce.mangaref.presentation.ui.ic_dragon_back
import dev.afalabarce.mangaref.presentation.ui.ic_dragon_ball_ol
import dev.afalabarce.mangaref.presentation.ui.ic_planet
import dev.afalabarce.mangaref.presentation.ui.ic_transformation
import dev.afalabarce.mangaref.presentation.ui.ki_title
import dev.afalabarce.mangaref.presentation.ui.max_ki_title
import dev.afalabarce.mangaref.presentation.ui.race_title
import dev.afalabarce.mangaref.presentation.ui.tab_general
import dev.afalabarce.mangaref.presentation.ui.tab_planets
import dev.afalabarce.mangaref.presentation.ui.tab_transformations
import dev.afalabarce.mangaref.presentation.viewmodels.features.characters.details.CharacterDetailsViewModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
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
    viewModel: CharacterDetailsViewModel = koinViewModel(),
    onBack: () -> Unit
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
        val (back,picture, dataCard) = createRefs()

        Image(
            painter = painterResource(Res.drawable.ic_dragon_back),
            contentDescription = null,
            modifier = Modifier.clip(CircleShape).constrainAs(back){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                width = Dimension.value(64.dp)
                height = Dimension.value(64.dp)
            }.zIndex(Float.MAX_VALUE)
                .clickable(enabled = true){
                    onBack()
                },
        )
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
                }.zIndex(Float.MAX_VALUE/2),

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
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
        )
    }
}

@Composable
private fun TabInfo(character: DragonBallCharacter, modifier: Modifier = Modifier) {
    val tabNavController = rememberNavController()
    var tabHeight by remember { mutableStateOf(0.dp) }
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
            modifier = Modifier.fillMaxSize().graphicsLayer{
                tabHeight = size.height.dp
            },
            navController = tabNavController,
            startDestination = CharacterSection.GENERAL.name
        ) {
            composable(CharacterSection.GENERAL.name) { stackEntry ->
                GeneralDataInfo(character, Modifier.fillMaxSize())
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
private fun GeneralDataInfo(character: DragonBallCharacter, modifier: Modifier) {
    val scrollState = rememberScrollState()
    Column(modifier = modifier.verticalScroll(scrollState)) {
        ConstraintLayout(modifier = Modifier
            .padding(all = AppMaterialTheme.dimens.appMargin)
        ) {
            val tipsSeparation = AppMaterialTheme.dimens.appMargin
            val (descriptionLabel, description, race, raceLabel, gender, genderLabel) = createRefs()
            val (kiLevelsSection, rectangleKiSection, kiLevelTitle, kiLevel, maxKiLevelTitle, maxKiLevel) = createRefs()
            val (affiliationTitle, affiliation) = createRefs()

            Text(
                text = stringResource(resource = Res.string.description_label),
                modifier = Modifier.padding(
                    vertical = AppMaterialTheme.dimens.appMargin
                ).constrainAs(descriptionLabel) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, margin = tipsSeparation)
                    end.linkTo(parent.end, margin = tipsSeparation)
                    width = Dimension.fillToConstraints
                }
            )
            Text(
                text = character.description,
                fontSize = 10.sp,
                modifier = Modifier.constrainAs(description) {
                    top.linkTo(descriptionLabel.bottom)
                    start.linkTo(descriptionLabel.start)
                    end.linkTo(descriptionLabel.end)
                }.border(
                    width = 1.dp,
                    shape = RoundedCornerShape(AppMaterialTheme.dimens.cornerRadius),
                    color = AppMaterialTheme.colorScheme.onSecondaryContainer
                ).padding(AppMaterialTheme.dimens.cornerRadius)
            )
            Text(
                text = stringResource(resource = Res.string.gender_label),
                modifier = Modifier.padding(
                    vertical = AppMaterialTheme.dimens.appMargin
                ).constrainAs(genderLabel) {
                    top.linkTo(description.bottom)
                    start.linkTo(parent.start, margin = tipsSeparation)
                    end.linkTo(raceLabel.start, margin = tipsSeparation)
                    width = Dimension.fillToConstraints
                }
            )
            Text(
                text = character.gender,
                modifier = Modifier.clip(RoundedCornerShape(50)).constrainAs(gender) {
                    top.linkTo(genderLabel.bottom)
                    start.linkTo(genderLabel.start)
                    end.linkTo(genderLabel.end)
                    width = Dimension.fillToConstraints
                }.border(
                    width = 1.dp,
                    color = AppMaterialTheme.colorScheme.onSecondaryContainer,
                    shape = RoundedCornerShape(50)
                ),
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(resource = Res.string.race_title),
                modifier = Modifier.padding(
                    vertical = AppMaterialTheme.dimens.appMargin
                ).constrainAs(raceLabel) {
                    top.linkTo(genderLabel.top)
                    start.linkTo(genderLabel.end, margin = tipsSeparation)
                    end.linkTo(parent.end, margin = tipsSeparation)
                    width = Dimension.fillToConstraints
                }
            )
            Text(
                text = character.race,
                modifier = Modifier.clip(RoundedCornerShape(0.5f)).constrainAs(race) {
                    top.linkTo(raceLabel.bottom)
                    start.linkTo(raceLabel.start)
                    end.linkTo(raceLabel.end)
                    width = Dimension.fillToConstraints
                }.border(
                    width = 1.dp,
                    color = AppMaterialTheme.colorScheme.onSecondaryContainer,
                    RoundedCornerShape(50)
                ),
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(resource = Res.string.generic_ki_levels_section_title),
                modifier = Modifier.padding(
                    vertical = AppMaterialTheme.dimens.appMargin
                ).constrainAs(kiLevelsSection) {
                    top.linkTo(gender.bottom)
                    start.linkTo(parent.start, margin = tipsSeparation)
                    end.linkTo(parent.end, margin = tipsSeparation)
                    width = Dimension.fillToConstraints
                },
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = stringResource(resource = Res.string.ki_title),
                modifier = Modifier.constrainAs(kiLevelTitle){
                    top.linkTo(kiLevelsSection.bottom)
                    start.linkTo(parent.start, margin = tipsSeparation)
                    end.linkTo(maxKiLevelTitle.start, margin = tipsSeparation)
                    width = Dimension.fillToConstraints
                }
            )
            Text(
                text = stringResource(Res.string.max_ki_title),
                modifier = Modifier.constrainAs(maxKiLevelTitle){
                    top.linkTo(kiLevelTitle.top)
                    start.linkTo(kiLevelTitle.end, margin = tipsSeparation)
                    end.linkTo(parent.end, margin = tipsSeparation)
                    width = Dimension.fillToConstraints
                }
            )
            Text(
                text = character.ki,
                modifier = Modifier.constrainAs(kiLevel){
                    top.linkTo(kiLevelTitle.bottom)
                    start.linkTo(kiLevelTitle.start)
                    end.linkTo(kiLevelTitle.end)
                    width = Dimension.fillToConstraints
                }
            )
            Text(
                text = character.maxKi,
                modifier = Modifier.constrainAs(maxKiLevel){
                    top.linkTo(maxKiLevelTitle.bottom)
                    start.linkTo(maxKiLevelTitle.start)
                    end.linkTo(maxKiLevelTitle.end)
                    width = Dimension.fillToConstraints
                }
            )
            Box(
                modifier = Modifier.constrainAs(rectangleKiSection) {
                    top.linkTo(gender.bottom, margin = tipsSeparation)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(kiLevel.bottom, margin = tipsSeparation.times(-1))
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }.border(
                    width = 1.dp,
                    color = AppMaterialTheme.colorScheme.onSecondaryContainer,
                    shape = RoundedCornerShape(AppMaterialTheme.dimens.cornerRadius)
                )
            )
            Text(
                text = stringResource(resource = Res.string.affiliation_title),
                modifier = Modifier.padding(
                    vertical = AppMaterialTheme.dimens.appMargin
                ).constrainAs(affiliationTitle) {
                    top.linkTo(rectangleKiSection.bottom, margin = tipsSeparation)
                    start.linkTo(parent.start, margin = tipsSeparation)
                    end.linkTo(parent.end, margin = tipsSeparation)
                    width = Dimension.fillToConstraints
                }
            )
            Text(
                text = character.affiliation,
                modifier = Modifier.constrainAs(affiliation) {
                    top.linkTo(affiliationTitle.bottom)
                    start.linkTo(parent.start, margin = tipsSeparation)
                    width = Dimension.wrapContent
                }.border(
                    width = 1.dp,
                    color = AppMaterialTheme.colorScheme.onSecondaryContainer,
                    shape = RoundedCornerShape(50)
                ).padding(horizontal = AppMaterialTheme.dimens.cornerRadius.times(2))
            )
        }
    }
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