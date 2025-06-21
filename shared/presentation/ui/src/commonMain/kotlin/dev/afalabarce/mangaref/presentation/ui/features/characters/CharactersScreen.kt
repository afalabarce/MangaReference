package dev.afalabarce.mangaref.presentation.ui.features.characters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemKey
import dev.afalabarce.mangaref.core.ui.theme.AppMaterialTheme
import dev.afalabarce.mangaref.domain.models.features.characters.DragonBallCharacter
import dev.afalabarce.mangaref.presentation.ui.Res
import dev.afalabarce.mangaref.presentation.ui.affiliation_title
import dev.afalabarce.mangaref.presentation.ui.features.common.ItemCard
import dev.afalabarce.mangaref.presentation.ui.ki_title
import dev.afalabarce.mangaref.presentation.ui.max_ki_title
import dev.afalabarce.mangaref.presentation.ui.race_title
import dev.afalabarce.mangaref.presentation.viewmodels.features.characters.CharactersViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(bottomAppBarScrollBehavior: BottomAppBarScrollBehavior, viewModel: CharactersViewModel = koinViewModel()) {
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
                    character = character
                )
            }
        }
    }
}

@Composable
fun CharacterItem(character: DragonBallCharacter, modifier: Modifier = Modifier){
    ItemCard(
        pictureUri = character.image,
        pictureHeight = 192.dp,
        modifier = modifier
    ) {
        Text(text = character.name, style = AppMaterialTheme.typography.titleMedium)
        Text(text = "${stringResource(Res.string.ki_title)} ${character.ki}", style = AppMaterialTheme.typography.bodyMedium)
        Text(text = "${stringResource(Res.string.max_ki_title)} ${character.maxKi}", style = AppMaterialTheme.typography.bodyMedium)
        Text(text = "${stringResource(Res.string.race_title)} ${character.race}", style = AppMaterialTheme.typography.bodyMedium)
        Text(text = "${stringResource(Res.string.affiliation_title)} ${character.affiliation}", style = AppMaterialTheme.typography.bodyMedium)
    }
}