package dev.afalabarce.mangaref.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.collectAsLazyPagingItems
import coil3.Image
import coil3.compose.AsyncImage
import dev.afalabarce.mangaref.core.ui.theme.AppMaterialTheme
import dev.afalabarce.mangaref.presentation.viewmodels.features.characters.CharactersViewModel
import dev.afalabarce.mangaref.presentation.viewmodels.features.planets.PlanetsViewModel
import okio.ByteString.Companion.decodeBase64
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CharactersScreen(viewModel: CharactersViewModel = koinViewModel()) {
    val data = viewModel.characters.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(
            top = AppMaterialTheme.dimens.statusBarSize
        ).padding(
            horizontal = AppMaterialTheme.dimens.startSurface
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            count = data.itemCount,
            key = { index -> data[index]?.id ?: index }
        ) { index ->
            data[index]?.let { character ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(
                        vertical = AppMaterialTheme.dimens.minBottomSurface
                    )
                ) {
                    AsyncImage(
                        modifier = Modifier.height(256.dp).align(Alignment.CenterHorizontally),
                        model = character.image,
                        contentDescription = character.name,
                        alignment = Alignment.Center
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = AppMaterialTheme.dimens.mediumMarginEndSurface),
                        text = character.name,
                        textAlign = TextAlign.Center,
                        color = AppMaterialTheme.colorScheme.onBackground
                    )
                }

            }
        }
    }
}

@Composable
fun PlanetsScreen(viewModel: PlanetsViewModel = koinViewModel()){
    val data = viewModel.planets.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(
            top = AppMaterialTheme.dimens.statusBarSize
        ).padding(
            horizontal = AppMaterialTheme.dimens.startSurface
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            count = data.itemCount,
            key = { index -> data[index]?.id ?: index }
        ) { index ->
            data[index]?.let { character ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(
                        vertical = AppMaterialTheme.dimens.minBottomSurface
                    )
                ) {
                    AsyncImage(
                        modifier = Modifier.height(256.dp).align(Alignment.CenterHorizontally),
                        model = character.image,
                        contentDescription = character.name,
                        alignment = Alignment.Center
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = AppMaterialTheme.dimens.mediumMarginEndSurface),
                        text = character.name,
                        textAlign = TextAlign.Center,
                        color = AppMaterialTheme.colorScheme.onBackground
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Preview
@Composable
fun App() {
    val viewModel: CharactersViewModel = koinViewModel()

    AppMaterialTheme {
        Surface {
            PlanetsScreen()
        }
    }
}