package yaroslavgorbach.badjokes.feature.jokes.model

import yaroslavgorbach.badJokes.common_ui.utill.UiMessage
import yaroslavgorbach.badjokes.R
import yaroslavgorbach.badjokes.data.local.model.JokeEntity

data class JokesViewState(
    val jokes: List<JokeEntity> = emptyList(),
    val isLoading: Boolean = true,
    val message: UiMessage<JokesUiMessage>? = null,
    val chips: List<Chip> = emptyList()
) {
    companion object {
        val Empty = JokesViewState()
    }

    data class Chip(val chipType: ChipType, val isChosen: Boolean = false) {

        enum class ChipType(val titleRes: Int, val title: String) {
            ANY(R.string.category_any, "Any"),
            PROGRAMMING(R.string.category_programming, "Programming"),
            MISC(R.string.category_misc, "Misc"),
            DARK(R.string.category_dark, "Dark"),
            PUN(R.string.category_pun, "Pun"),
            SPOOKY(R.string.category_spooky, "Spooky"),
            CHRISTMAS(R.string.category_christmas, "Christmas"),
        }

        companion object {
            val Chips: List<Chip> = listOf(
                Chip(chipType = ChipType.ANY, true),
                Chip(chipType = ChipType.PROGRAMMING),
                Chip(chipType = ChipType.MISC),
                Chip(chipType = ChipType.DARK),
                Chip(chipType = ChipType.PUN),
                Chip(chipType = ChipType.SPOOKY),
                Chip(chipType = ChipType.CHRISTMAS),
            )
        }
    }


}
