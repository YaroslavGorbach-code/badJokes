package yaroslavgorbach.badJokes.common_ui.ui.swipeCard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember


@Composable
fun rememberSwipeController(): SwipeController {
    return remember { SwipeControllerImpl() }
}

interface SwipeController {
    var currentCardController: CardController?
    fun swipeRight()
    fun swipeLeft()
}

class SwipeControllerImpl : SwipeController {
    override var currentCardController: CardController? = null

    override fun swipeRight() {
        currentCardController?.swipeRight()
    }

    override fun swipeLeft() {
        currentCardController?.swipeLeft()
    }
}