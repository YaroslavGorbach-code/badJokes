package yaroslavgorbach.badjokes.feature.jokes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.badJokes.common_ui.utill.UiMessage
import yaroslavgorbach.badJokes.common_ui.utill.UiMessageManager
import yaroslavgorbach.badjokes.data.JokesRepo
import yaroslavgorbach.badjokes.data.local.model.JokeEntity
import yaroslavgorbach.badjokes.feature.jokes.model.JokesAction
import yaroslavgorbach.badjokes.feature.jokes.model.JokesUiMessage
import yaroslavgorbach.badjokes.feature.jokes.model.JokesViewState
import javax.inject.Inject

@HiltViewModel
class JokesViewModel @Inject constructor(
    private val jokesRepo: JokesRepo,
) : ViewModel() {

    private val pendingActions = MutableSharedFlow<JokesAction>()

    private val jokes: MutableStateFlow<MutableList<JokeEntity>> = MutableStateFlow(ArrayList())

    private val uiMessageManager: UiMessageManager<JokesUiMessage> = UiMessageManager()

    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val chips: MutableStateFlow<List<JokesViewState.Chip>> =
        MutableStateFlow(JokesViewState.Chip.Chips)

    val state: StateFlow<JokesViewState> = combine(
        jokes,
        isLoading,
        uiMessageManager.message,
        chips
    ) { jokes, isLoading, message, chips ->
        JokesViewState(jokes = jokes, isLoading = isLoading, message = message, chips = chips)
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
                    is JokesAction.RemoveJoke -> {

                        jokes.update { listOfJokes ->
                            listOfJokes.drop(1).toMutableList()
                        }

                        if (jokes.value.size == 0) {
                            loadJokes(5)
                        }
                    }
                    is JokesAction.LoadJokes -> {
                        loadJokes(5)
                    }
                    is JokesAction.ChipChosen -> {
                        chips.update {
                            it.map { chip ->
                                if (chip.chipType == action.chip.chipType) {
                                    chip.copy(isChosen = true)
                                } else {
                                    chip.copy(isChosen = false)
                                }
                            }
                        }
                        loadJokes(category = action.chip.chipType.title)
                    }
                    JokesAction.ShareJoke -> {
                        uiMessageManager.emitMessage(UiMessage(JokesUiMessage.ShareJoke(jokes.value.first())))
                    }
                }
            }
        }
    }

    private suspend fun loadJokes(
        size: Int = 5,
        category: String = JokesViewState.Chip.ChipType.ANY.title
    ) {
        isLoading.emit(true)
        jokesRepo.getJokes(size, category)
            .onSuccess { joke ->
                jokes.emit(joke.toMutableList())
                isLoading.emit(false)
            }.onFailure {
                uiMessageManager.emitMessage(UiMessage(JokesUiMessage.LoadingFailed))
                isLoading.emit(false)
            }
    }

    fun submitAction(action: JokesAction) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }

    fun clearMessage(id: Long) {
        viewModelScope.launch {
            uiMessageManager.clearMessage(id)
        }
    }

}



