package ru.urfu.chucknorrisdemo.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.urfu.chucknorrisdemo.data.RetrofitClient
import ru.urfu.chucknorrisdemo.data.local.JokeStorage

sealed class UiState {
    object Loading : UiState()
    data class Success(val joke: String) : UiState()
    data class Error(val message: String) : UiState()
}

class ChuckViewModel(app: Application) : AndroidViewModel(app) {
    private val api = RetrofitClient.api
    private val storage = JokeStorage(app)

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadCategories() {
        viewModelScope.launch {
            try {
                _categories.value = api.getCategories()
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Ошибка загрузки категорий: ${e.message}")
            }
        }
    }

    fun loadJoke(category: String?) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val joke = api.getRandomJoke(category).value
                _uiState.value = UiState.Success(joke)
                storage.saveJoke(joke)
            } catch (e: Exception) {
                val last = storage.getLastJoke()
                if (last != null)
                    _uiState.value = UiState.Success("(Оффлайн)\n\n$last")
                else
                    _uiState.value = UiState.Error("Ошибка при загрузке: ${e.message}")
            }
        }
    }
}
