package ru.urfu.chucknorrisdemo.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.urfu.chucknorrisdemo.presentation.viewModel.ChuckViewModel

@Composable
fun ChuckScreen() {
    val viewModel = koinViewModel<ChuckViewModel>()
    val viewState = viewModel.viewState

    var expanded by remember { mutableStateOf(false) }

    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Button(onClick = { expanded = viewState.isConnected }) {
                Text(text = viewState.selectedCategory?.string ?: "Выбрать категорию")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                viewState.categories.forEach {
                    Text(
                        text = it.string,
                        Modifier
                            .clickable {
                                viewModel.onCategoryClicked(it)
                                expanded = false
                            }
                            .padding(8.dp)
                    )
                    Divider()
                }

            }
            viewState.joke?.let {
                if (!viewState.isConnected) {
                    expanded = false
                    Text(
                        text = "No internet connection, but there is the last available joke:",
                        fontWeight = FontWeight.Bold)
                }
                Text(text = it.value)
            }
        }
    }
}

@Preview
@Composable
fun ChuckScreenPreview() {
    ChuckScreen()
}
