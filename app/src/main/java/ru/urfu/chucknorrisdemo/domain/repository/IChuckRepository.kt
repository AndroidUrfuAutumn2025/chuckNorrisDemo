package ru.urfu.chucknorrisdemo.domain.repository

interface IChuckRepository {
    suspend fun getJokeByCategory(category: String): String
}
