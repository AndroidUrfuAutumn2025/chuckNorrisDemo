package ru.urfu.chucknorrisdemo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.urfu.chucknorrisdemo.presentation.screen.FactsScreen
import ru.urfu.chucknorrisdemo.ui.theme.FactsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FactsAppTheme {
                FactsScreen()
            }
        }
    }
}