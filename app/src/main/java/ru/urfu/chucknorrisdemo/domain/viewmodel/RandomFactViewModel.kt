package ru.urfu.chucknorrisdemo.domain.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.urfu.chucknorrisdemo.domain.model.FactEntity
import ru.urfu.chucknorrisdemo.domain.repository.FactsRepository

class RandomFactViewModel : ViewModel(), KoinComponent {

    private val repository: FactsRepository by inject()

    private val _randomFact = MutableStateFlow<FactEntity?>(null)
    val randomFact: StateFlow<FactEntity?> = _randomFact.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        getRandomFact()
    }

    fun getRandomFact() {
        val facts = repository.getFacts()
        _randomFact.value = facts.random()
    }

    fun setRefreshing(refreshing: Boolean) {
        _isRefreshing.value = refreshing
    }
}