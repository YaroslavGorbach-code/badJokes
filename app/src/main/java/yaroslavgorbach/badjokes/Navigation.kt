package yaroslavgorbach.badjokes

import androidx.compose.foundation.text.InternalFoundationTextApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import yaroslavgorbach.badjokes.feature.jokes.ui.JokesUi

sealed class Screen(val route: String) {
    object Jokes : Screen("Jokes")
}

private sealed class LeafScreen(
    private val route: String,
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Jokes : LeafScreen("Jokes")
}

@ExperimentalMaterialApi
@InternalFoundationTextApi
@Composable
internal fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Jokes.route,
        modifier = modifier,
    ) {
        addJokesTopLevel(navController)
    }
}

@ExperimentalMaterialApi
@InternalFoundationTextApi
private fun NavGraphBuilder.addJokesTopLevel(
    navController: NavController,
) {
    navigation(
        route = Screen.Jokes.route,
        startDestination = LeafScreen.Jokes.createRoute(Screen.Jokes),
    ) {
        addJokes(navController, Screen.Jokes)
    }
}

@ExperimentalMaterialApi
@InternalFoundationTextApi
private fun NavGraphBuilder.addJokes(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.Jokes.createRoute(root)) {
        JokesUi()
    }
}

