package dev.afalabarce.mangaref.presentation.ui.features.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.afalabarce.mangaref.core.ui.theme.AppMaterialTheme
import dev.afalabarce.mangaref.presentation.ui.Res
import dev.afalabarce.mangaref.presentation.ui.characters
import dev.afalabarce.mangaref.presentation.ui.ic_characters
import dev.afalabarce.mangaref.presentation.ui.ic_planet
import dev.afalabarce.mangaref.presentation.ui.planets
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(navController: NavController, bottomAppBarScrollBehavior: BottomAppBarScrollBehavior, modifier: Modifier = Modifier) {
    val items = mapOf(
        Pair(
            Characters,
            Pair(Res.string.characters, Res.drawable.ic_characters)
        ),
        Pair(Planets, Pair(Res.string.planets, Res.drawable.ic_planet))
    )
    val currentRouteState by navController.currentBackStackEntryAsState()

    BottomAppBar(
        modifier = modifier
            .padding(
                bottom = AppMaterialTheme.dimens.minBottomSurface,
                start = AppMaterialTheme.dimens.minStartSurface,
                end = AppMaterialTheme.dimens.minEndSurface
            )
            .height(AppMaterialTheme.dimens.bottomBarHeight)
            .clip(RoundedCornerShape(50)),
        scrollBehavior = bottomAppBarScrollBehavior,
        containerColor = AppMaterialTheme.colorScheme.secondaryContainer,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items.forEach { item ->
                val selected = try { currentRouteState?.destination?.hasRoute(item.key::class) ?: false } catch (e: Exception){ false }
                NavigationBarItem(
                    selected = selected,
                    onClick = { navController.navigate(item.key) },
                    icon = {
                        Icon(
                            imageVector = vectorResource(item.value.second),
                            modifier = Modifier.size(AppMaterialTheme.dimens.smallIconSize),
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(item.value.first)) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AppMaterialTheme.colorScheme.secondary,
                        selectedTextColor = AppMaterialTheme.colorScheme.secondary,
                        unselectedIconColor = AppMaterialTheme.colorScheme.onTertiary,
                        unselectedTextColor = AppMaterialTheme.colorScheme.onTertiary,
                    )
                )
            }
        }
    }
}
