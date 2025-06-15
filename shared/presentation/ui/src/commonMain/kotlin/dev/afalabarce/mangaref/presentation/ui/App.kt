package dev.afalabarce.mangaref.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemKey
import coil3.compose.AsyncImage
import dev.afalabarce.mangaref.core.ui.theme.AppMaterialTheme
import dev.afalabarce.mangaref.presentation.ui.features.common.ItemCard
import dev.afalabarce.mangaref.presentation.ui.features.main.MainScreen
import dev.afalabarce.mangaref.presentation.ui.features.splash.SplashScreen
import dev.afalabarce.mangaref.presentation.viewmodels.features.characters.CharactersViewModel
import dev.afalabarce.mangaref.presentation.viewmodels.features.planets.PlanetsViewModel
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview
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
                ItemCard(
                    pictureUri = character.image,
                    pictureHeight = 192.dp,
                    modifier = Modifier.fillMaxWidth().padding(
                        vertical = AppMaterialTheme.dimens.minBottomSurface
                    )
                ) {
                    Text(text = character.name)
                }
            }
        }
    }
}

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

@Preview
@Composable
fun App() {
    val navController = rememberNavController()

    AppMaterialTheme {
        Surface {
            NavHost(navController = navController, startDestination = MainNavigator.SplashScreen){
                composable<MainNavigator.SplashScreen> {
                    SplashScreen {
                        navController.popBackStack()
                        navController.navigate(MainNavigator.MainScreen)
                    }
                }
                composable<MainNavigator.MainScreen> {
                    MainScreen()
                }
            }
        }
    }
}

@Serializable
sealed class MainNavigator {
    @Serializable
    object SplashScreen: MainNavigator()
    @Serializable
    object MainScreen: MainNavigator()
}