package ru.urfu.chucknorrisdemo.presentation.state

import ru.urfu.chucknorrisdemo.domain.entity.JokeCategory
import ru.urfu.chucknorrisdemo.domain.entity.JokeEntity

interface ChuckScreenState {
    val categories: List<JokeCategory>
    val selectedCategory: JokeCategory?
    val joke: JokeEntity?

    val error: String?
    val isLoading: Boolean
    val isConnected: Boolean
}