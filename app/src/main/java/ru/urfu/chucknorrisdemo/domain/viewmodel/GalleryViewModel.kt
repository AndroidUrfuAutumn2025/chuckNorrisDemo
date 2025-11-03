package ru.urfu.chucknorrisdemo.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.urfu.chucknorrisdemo.domain.repository.FactsRepository

class GalleryViewModel : ViewModel(), KoinComponent {

    private val repository: FactsRepository by inject()

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    val facts = repository.getFacts()

    fun updateCurrentPage(page: Int) {
        _currentPage.value = page
    }
}