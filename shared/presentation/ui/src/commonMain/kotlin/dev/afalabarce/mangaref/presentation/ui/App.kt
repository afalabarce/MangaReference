package dev.afalabarce.mangaref.presentation.ui

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.afalabarce.mangaref.core.ui.theme.AppMaterialTheme
import dev.afalabarce.mangaref.presentation.ui.features.main.MainScreen
import dev.afalabarce.mangaref.presentation.ui.features.splash.SplashScreen
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview


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