package yaroslavgorbach.badjokes.feature.jokes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import yaroslavgorbach.badJokes.common_ui.theme.BadJokesTheme
import yaroslavgorbach.badJokes.common_ui.theme.Primary
import yaroslavgorbach.badJokes.common_ui.ui.swipeCard.Swiper
import yaroslavgorbach.badjokes.data.remote.model.JokeType
import yaroslavgorbach.badjokes.feature.jokes.model.JokesAction
import yaroslavgorbach.badjokes.feature.jokes.model.JokesUiMessage
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
        clearMessage = viewModel::clearMessage
    )
}

@Composable
internal fun JokesUi(
    state: JokesViewState,
    actioner: (JokesAction) -> Unit,
    clearMessage: (id: Long) -> Unit
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    state.message?.let { uiMessage ->
        when (uiMessage.message) {
            JokesUiMessage.LoadingFailed -> {
                LaunchedEffect(key1 = uiMessage.id, block = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(message = "Loading failed")
                    }
                })
            }
        }
        clearMessage(uiMessage.id)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Primary)
        ) {
            Swiper(
                items = state.jokes,
                onItemRemoved = { item, dir ->
                    actioner(JokesAction.RemoveJoke(item))
                },
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 24.dp, vertical = 100.dp)
            ) { joke ->
                Box(modifier = Modifier.fillMaxSize()) {
                    if (joke.type == JokeType.SINGLE) {
                        Text(
                            style = MaterialTheme.typography.caption,
                            text = joke.joke.toString(),
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }

                    if (joke.type == JokeType.TWOPART) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        ) {
                            Text(
                                style = MaterialTheme.typography.caption,
                                text = joke.setup.toString(),
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                fontSize = 18.sp,
                                style = MaterialTheme.typography.caption,
                                text = joke.delivery.toString(),
                            )
                        }
                    }
                }
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
            clearMessage = {}
        )
    }
}