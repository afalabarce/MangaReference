package dev.afalabarce.mangaref.presentation.ui.features.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.afalabarce.mangaref.core.ui.theme.AppMaterialTheme
import dev.afalabarce.mangaref.presentation.viewmodels.features.characters.details.CharacterDetailsViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailsScreen(bottomAppBarScrollBehavior: BottomAppBarScrollBehavior, characterId: Long, viewModel: CharacterDetailsViewModel = koinViewModel()) {
    val currentCharacter by viewModel.character.collectAsStateWithLifecycle()
    LaunchedEffect(currentCharacter.isEmptyCharacter()) {
        if (currentCharacter.isEmptyCharacter()) {
            viewModel.loadCharacterById(characterId)
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = AppMaterialTheme.dimens.statusBarSize)
        .padding(horizontal = AppMaterialTheme.dimens.startSurface)
    ) {
        Text(text = currentCharacter.name)
    }
}