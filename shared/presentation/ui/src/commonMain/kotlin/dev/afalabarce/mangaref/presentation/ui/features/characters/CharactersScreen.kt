package dev.afalabarce.mangaref.presentation.ui.features.characters

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemKey
import dev.afalabarce.mangaref.core.ui.theme.AppMaterialTheme
import dev.afalabarce.mangaref.domain.models.features.characters.DragonBallCharacter
import dev.afalabarce.mangaref.presentation.ui.Res
import dev.afalabarce.mangaref.presentation.ui.affiliation_title
import dev.afalabarce.mangaref.presentation.ui.features.common.CHARACTER_NAME_TRANSITION_KEY
import dev.afalabarce.mangaref.presentation.ui.features.common.CHARACTER_PICTURE_TRANSITION_KEY
import dev.afalabarce.mangaref.presentation.ui.features.common.ItemCard
import dev.afalabarce.mangaref.presentation.ui.ic_dragon_ball
import dev.afalabarce.mangaref.presentation.ui.ic_dragon_ball_female
import dev.afalabarce.mangaref.presentation.ui.ic_dragon_ball_male
import dev.afalabarce.mangaref.presentation.ui.ki_title
import dev.afalabarce.mangaref.presentation.ui.max_ki_title
import dev.afalabarce.mangaref.presentation.ui.race_title
import dev.afalabarce.mangaref.presentation.viewmodels.features.characters.CharactersViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun CharactersScreen(
    bottomAppBarScrollBehavior: BottomAppBarScrollBehavior,
    viewModel: CharactersViewModel = koinViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onCharacterClick: (DragonBallCharacter) -> Unit
) {
    val data = viewModel.characters.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(
            top = AppMaterialTheme.dimens.statusBarSize
        ).padding(
            horizontal = AppMaterialTheme.dimens.startSurface
        ).nestedScroll(bottomAppBarScrollBehavior.nestedScrollConnection),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            count = data.itemCount,
            key = data.itemKey { character -> character.id }
        ) { index ->
            data[index]?.let { character ->
                CharacterItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = AppMaterialTheme.dimens.minBottomSurface),
                    character = character,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope,
                ){ currentCharacter ->
                    onCharacterClick(currentCharacter)
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CharacterItem(
    character: DragonBallCharacter,
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onClick: (DragonBallCharacter) -> Unit
){
    ItemCard(
        pictureUri = character.image,
        pictureHeight = 192.dp,
        modifier = modifier,
        pictureTransitionKey = "$CHARACTER_PICTURE_TRANSITION_KEY${character.id}",
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        onClick = { onClick(character) }
    ) {
        with(sharedTransitionScope) {
            Text(
                text = character.name,
                style = AppMaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.sharedElement(
                    sharedContentState = sharedTransitionScope.rememberSharedContentState(key = "$CHARACTER_NAME_TRANSITION_KEY${character.id}"),
                    animatedVisibilityScope = animatedContentScope
                )
            )
            Text(
                text = "${stringResource(Res.string.ki_title)} ${character.ki}",
                style = AppMaterialTheme.typography.bodyMedium
            )
            Text(
                text = "${stringResource(Res.string.max_ki_title)} ${character.maxKi}",
                style = AppMaterialTheme.typography.bodyMedium
            )
            Text(
                text = "${stringResource(Res.string.race_title)} ${character.race}",
                style = AppMaterialTheme.typography.bodyMedium
            )
            Text(
                text = "${stringResource(Res.string.affiliation_title)} ${character.affiliation}",
                style = AppMaterialTheme.typography.bodyMedium
            )
            Image(
                modifier = Modifier
                    .width(AppMaterialTheme.dimens.smallIconSize)
                    .padding(top = AppMaterialTheme.dimens.minTopSurface),
                painter = painterResource(
                    when (character.gender) {
                        "Male" -> Res.drawable.ic_dragon_ball_male
                        "Female" -> Res.drawable.ic_dragon_ball_female
                        else -> Res.drawable.ic_dragon_ball
                    }
                ),
                contentDescription = null
            )
        }
    }
}