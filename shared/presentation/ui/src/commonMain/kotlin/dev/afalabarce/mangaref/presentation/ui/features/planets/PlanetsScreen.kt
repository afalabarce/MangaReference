package dev.afalabarce.mangaref.presentation.ui.features.planets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import dev.afalabarce.mangaref.core.ui.theme.AppMaterialTheme
import dev.afalabarce.mangaref.presentation.viewmodels.features.planets.PlanetsViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetsScreen(bottomAppBarScrollBehavior: BottomAppBarScrollBehavior, viewModel: PlanetsViewModel = koinViewModel()){
    val data = viewModel.planets.collectAsLazyPagingItems()

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