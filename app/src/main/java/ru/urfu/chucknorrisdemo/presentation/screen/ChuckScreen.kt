package ru.urfu.chucknorrisdemo.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.urfu.chucknorrisdemo.presentation.viewModel.ChuckViewModel
import ru.urfu.chucknorrisdemo.presentation.viewModel.UiState

@Composable
fun ChuckScreen(vm: ChuckViewModel = viewModel()) {
    val uiState by vm.uiState.collectAsState()
    val categories by vm.categories.collectAsState()

    var selectedCategory by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        vm.loadCategories()
        vm.loadJoke(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Chuck Norris Jokes", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        if (categories.isNotEmpty()) {
            DropdownMenuBox(
                categories = categories,
                selectedCategory = selectedCategory,
                onSelect = {
                    selectedCategory = it
                    vm.loadJoke(it)
                }
            )
        }

        Spacer(Modifier.height(24.dp))

        when (uiState) {
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Success -> Text((uiState as UiState.Success).joke)
            is UiState.Error -> Text((uiState as UiState.Error).message, color = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
fun DropdownMenuBox(
    categories: List<String>,
    selectedCategory: String?,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true }) {
            Text(selectedCategory ?: "Выберите категорию")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            categories.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        onSelect(it)
                        expanded = false
                    }
                )
            }
        }
    }
}
