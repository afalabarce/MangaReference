package dev.afalabarce.mangaref.presentation.ui.features.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberBottomAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.afalabarce.mangaref.core.ui.theme.AppMaterialTheme
import dev.afalabarce.mangaref.presentation.ui.CharactersScreen
import dev.afalabarce.mangaref.presentation.ui.PlanetsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
    val navController = rememberNavController()
    val bottomAppBarScrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior(rememberBottomAppBarState())
    Scaffold(
        modifier = Modifier.fillMaxSize().background(AppMaterialTheme.colorScheme.background),
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                bottomAppBarScrollBehavior = bottomAppBarScrollBehavior
            )
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.fillMaxSize().padding(bottom = paddingValues.calculateBottomPadding()),
            navController = navController,
            startDestination = Characters
        ){
            composable <Characters>{
                CharactersScreen(bottomAppBarScrollBehavior = bottomAppBarScrollBehavior)
            }
            composable <Planets>{
                PlanetsScreen(bottomAppBarScrollBehavior = bottomAppBarScrollBehavior)
            }
        }
    }
}