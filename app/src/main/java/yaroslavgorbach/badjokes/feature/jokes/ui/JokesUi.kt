package yaroslavgorbach.badjokes.feature.jokes.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
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
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import yaroslavgorbach.badJokes.common_ui.theme.BadJokesTheme
import yaroslavgorbach.badJokes.common_ui.theme.Primary
import yaroslavgorbach.badJokes.common_ui.theme.SecondaryText
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
    ) { _ ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(state.isLoading),
            onRefresh = { actioner(JokesAction.LoadJokes) },
            indicator = { refreshState, trigger ->
                SwipeRefreshIndicator(
                    state = refreshState,
                    refreshTriggerDistance = trigger,
                    scale = true,
                    backgroundColor = MaterialTheme.colors.primary,
                    shape = MaterialTheme.shapes.small,
                )
            }
        ) {

            Box(
                Modifier
                    .fillMaxSize()
                    .background(Primary)
                    .verticalScroll(ScrollState(0))
            ) {
                Swiper(
                    items = state.jokes,
                    onItemRemoved = { item, dir ->
                        actioner(JokesAction.RemoveJoke(item))
                    },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                        .height(500.dp)
                        .padding(horizontal = 24.dp)
                ) { joke ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        if (joke.type == JokeType.SINGLE) {
                            Text(
                                style = MaterialTheme.typography.caption,
                                text = joke.joke.toString(),
                                fontSize = 20.sp,
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

                        Row(
                            Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 18.dp)
                        ) {
                            Icon(
                                Icons.Default.Share,
                                contentDescription = null,
                                tint = SecondaryText,
                                modifier = Modifier.clickable {

                                })

                            Spacer(modifier = Modifier.size(40.dp))

                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = null,
                                tint = SecondaryText
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