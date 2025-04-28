package ru.urfu.chucknorrisdemo.domain.repository

import ru.urfu.chucknorrisdemo.domain.entity.JokeCategory
import ru.urfu.chucknorrisdemo.domain.entity.JokeEntity

interface IChuckRepository {
    suspend fun getCategories(): List<JokeCategory>
    suspend fun getByCategory(category: JokeCategory): JokeEntity
    suspend fun getLastLoadedJoke(): JokeEntity
    suspend fun saveJoke(joke: JokeEntity)
}