package yaroslavgorbach.badjokes.feature.jokes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.badjokes.data.JokesRepo
import yaroslavgorbach.badjokes.data.local.model.JokeEntity
import yaroslavgorbach.badjokes.feature.jokes.model.JokesAction
import yaroslavgorbach.badjokes.feature.jokes.model.JokesViewState
import javax.inject.Inject

@HiltViewModel
class JokesViewModel @Inject constructor(
    private val jokesRepo: JokesRepo,
) : ViewModel() {

    private val pendingActions = MutableSharedFlow<JokesAction>()

    private val jokes: MutableStateFlow<MutableList<JokeEntity>> = MutableStateFlow(ArrayList())

    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val state: StateFlow<JokesViewState> = combine(
        jokes,
        isLoading
    ) { jokes, isLoading ->
        JokesViewState(jokes = jokes, isLoading = isLoading)
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = JokesViewState.Empty
    )

    init {
        viewModelScope.launch {
            loadJokes()
        }

        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {

                }
            }
        }
    }

    private suspend fun loadJokes() {
        jokes.emit(jokesRepo.observe().toMutableList())
    }

    fun submitAction(action: JokesAction) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }

}



