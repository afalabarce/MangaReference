package dev.afalabarce.mangaref.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.afalabarce.mangaref.core.common.platform.getPlatform
import dev.afalabarce.mangaref.core.ui.theme.AppMaterialTheme
import mangareference.shared.presentation.ui.generated.resources.Res
import mangareference.shared.presentation.ui.generated.resources.app_name
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalResourceApi::class)
@Preview
@Composable
fun App() {
    AppMaterialTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val platform = getPlatform()
                val appName = stringResource(Res.string.app_name)
                Text("$appName ${platform.platformData}")
            }
        }
    }
}