package ru.urfu.chucknorrisdemo.domain.model

data class Joke(
    val id: String,
    val text: String,
    val categories: List<String>
)
