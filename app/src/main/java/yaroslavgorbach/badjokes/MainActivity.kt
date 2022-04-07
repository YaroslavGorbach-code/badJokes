package yaroslavgorbach.badjokes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import yaroslavgorbach.badJokes.common_ui.theme.BadJokesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BadJokesTheme {


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BadJokesTheme {

    }
}