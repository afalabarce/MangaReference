package dev.afalabarce.mangaref.presentation.ui.features.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.savedstate.SavedState
import dev.afalabarce.mangaref.core.ui.theme.AppMaterialTheme
import dev.afalabarce.mangaref.domain.models.features.characters.DragonBallCharacter
import dev.afalabarce.mangaref.domain.models.features.planets.DragonBallPlanet
import dev.afalabarce.mangaref.presentation.ui.features.characters.CharacterDetailsScreen
import dev.afalabarce.mangaref.presentation.ui.features.characters.CharactersScreen
import dev.afalabarce.mangaref.presentation.ui.features.planets.PlanetDetailsScreen
import dev.afalabarce.mangaref.presentation.ui.features.planets.PlanetsScreen
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.CupertinoMaterials
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.rememberHazeState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val bottomAppBarScrollBehavior =
        BottomAppBarDefaults.exitAlwaysScrollBehavior(rememberBottomAppBarState())
    val hazeSource = rememberHazeState()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize().background(AppMaterialTheme.colorScheme.background)
            .hazeSource(hazeSource),
        bottomBar = {
            if (currentBackStackEntry?.destination?.hasRoute(Characters::class) == true ||
                currentBackStackEntry?.destination?.hasRoute(Planets::class) == true) {
                BottomNavigationBar(
                    modifier = Modifier.hazeEffect(
                        state = hazeSource,
                        style = CupertinoMaterials.ultraThin()
                    ),
                    navController = navController,
                    bottomAppBarScrollBehavior = bottomAppBarScrollBehavior
                )
            }
        }
    ) {
        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            startDestination = Characters
        ) {
            composable<Characters> {
                CharactersScreen(bottomAppBarScrollBehavior = bottomAppBarScrollBehavior) { clickedCharacter ->
                    navController.navigate(CharacterDetails(clickedCharacter.id))
                }
            }
            composable<Planets> {
                PlanetsScreen(bottomAppBarScrollBehavior = bottomAppBarScrollBehavior)
            }

            composable<CharacterDetails> { stackEntry ->
                val currentCharacter = stackEntry.toRoute<CharacterDetails>()
                CharacterDetailsScreen(
                    bottomAppBarScrollBehavior = bottomAppBarScrollBehavior,
                    characterId = currentCharacter.characterId
                )
            }

            composable<PlanetDetails> { stackEntry ->
                val currentPlanet = stackEntry.toRoute<PlanetDetails>()
                PlanetDetailsScreen(
                    bottomAppBarScrollBehavior = bottomAppBarScrollBehavior,
                    planetId = currentPlanet.planetId
                )
            }
        }
    }
}