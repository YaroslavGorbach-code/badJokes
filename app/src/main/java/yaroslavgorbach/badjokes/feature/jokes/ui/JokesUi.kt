package yaroslavgorbach.badjokes.feature.jokes.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import yaroslavgorbach.badJokes.common_ui.theme.BadJokesTheme
import yaroslavgorbach.badJokes.common_ui.ui.swipeCard.Swiper
import yaroslavgorbach.badjokes.data.local.model.JokeEntity
import yaroslavgorbach.badjokes.data.remote.model.JokeResponse
import yaroslavgorbach.badjokes.feature.jokes.model.JokesAction
import yaroslavgorbach.badjokes.feature.jokes.model.JokesViewState
import yaroslavgorbach.badjokes.feature.jokes.presentation.JokesViewModel

@Composable
fun JokesUi(
) {
    JokesUi(
        viewModel = hiltViewModel(),
    )
}

@Composable
internal fun JokesUi(
    viewModel: JokesViewModel,
) {
    JokesUi(
        state = viewModel.state.collectAsState().value,
        actioner = viewModel::submitAction,
    )
}

@Composable
internal fun JokesUi(
    state: JokesViewState,
    actioner: (JokesAction) -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        Swiper(
            items = state.jokes,
            onItemRemoved = { item, dir -> },
            modifier = Modifier.align(
                Alignment.Center
            )
        ) {
            Card(backgroundColor = Color.Red, modifier = Modifier.size(200.dp)) {
                Text(text = it.joke.toString(), modifier = Modifier.align(Alignment.Center))
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ExercisesPreview() {
    BadJokesTheme {
        JokesUi(
            state = JokesViewState.Empty,
            actioner = {},
        )
    }
}