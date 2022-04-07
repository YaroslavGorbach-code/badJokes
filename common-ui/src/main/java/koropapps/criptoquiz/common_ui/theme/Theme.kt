package koropapps.criptoquiz.common_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Primary,
    primaryVariant = Primary,
    secondary = Secondary,
)

private val LightColorPalette = lightColors(
    primary = Primary,
    primaryVariant = Primary,
    secondary = Secondary,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    */
)

@Composable
fun CryptoTheme(isDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
//    val colors = if (isDarkTheme) {
//        DarkColorPalette
//    } else {
//        LightColorPalette
//    }

    // TODO: 3/9/2022 implement dark theme later

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = if (isDarkTheme) DarkColorPalette.background else LightColorPalette.background,
        darkIcons = isDarkTheme.not()
    )

    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}