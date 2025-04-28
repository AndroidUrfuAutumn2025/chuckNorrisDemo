package ru.urfu.chucknorrisdemo.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.urfu.chucknorrisdemo.core.coroutineUtils.launchLoadingAndError
import ru.urfu.chucknorrisdemo.core.networkUtils.NetworkManager
import ru.urfu.chucknorrisdemo.domain.entity.JokeCategory
import ru.urfu.chucknorrisdemo.domain.entity.JokeEntity
import ru.urfu.chucknorrisdemo.domain.repository.IChuckRepository
import ru.urfu.chucknorrisdemo.presentation.state.ChuckScreenState

class ChuckViewModel(
    private val repository: IChuckRepository,
    private val networkManager: NetworkManager
) : ViewModel() {
    private val mutableState = MutableChuckState()
    val viewState = mutableState as ChuckScreenState

    init {
        observeNetworkChanges()
    }

    private fun observeNetworkChanges() {
        viewModelScope.launch {
            networkManager.isConnected
                .collect { isConnected ->
                    if (isConnected) {
                        update()
                    }
                    else {
                        if (mutableState.joke != null) {
                            repository.saveJoke(mutableState.joke!!)
                        }
                        else {
                            mutableState.joke = repository.getLastLoadedJoke()
                        }
                    }
                    mutableState.isConnected = isConnected
                }
        }
    }

    private fun update() {
        viewModelScope.launchLoadingAndError(
            handleError = { mutableState.error = it.localizedMessage },
            updateLoading = { mutableState.isLoading = it }
        ) {
            mutableState.categories = repository.getCategories()
            mutableState.selectedCategory?.let {
                mutableState.joke = repository.getByCategory(
                    mutableState.selectedCategory ?: JokeCategory.OTHER
                )
            }
        }
    }

    fun onCategoryClicked(category: JokeCategory) {
        mutableState.selectedCategory = category
        update()
    }

    private class MutableChuckState : ChuckScreenState {
        override var categories: List<JokeCategory> by mutableStateOf(emptyList())
        override var selectedCategory: JokeCategory? by mutableStateOf(null)
        override var joke: JokeEntity? by mutableStateOf(null)

        override var error: String? by mutableStateOf(null)
        override var isLoading: Boolean by mutableStateOf(false)
        override var isConnected: Boolean by mutableStateOf(false)
    }
}