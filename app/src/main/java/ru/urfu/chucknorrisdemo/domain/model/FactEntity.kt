package ru.urfu.chucknorrisdemo.domain.model

data class FactEntity(
    val name: String,
    val startYear: Int,
    val endYear: Int,
    val imageUrl: String,
    val description: String
)