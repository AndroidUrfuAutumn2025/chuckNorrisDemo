package ru.urfu.chucknorrisdemo.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.urfu.chucknorrisdemo.data.api.ChuckApi
import ru.urfu.chucknorrisdemo.data.datastore.DatastoreManager
import ru.urfu.chucknorrisdemo.data.mapper.ResponseToEntityMapper
import ru.urfu.chucknorrisdemo.domain.entity.JokeCategory
import ru.urfu.chucknorrisdemo.domain.entity.JokeEntity
import ru.urfu.chucknorrisdemo.domain.repository.IChuckRepository

class ChuckRepository(
    private val api: ChuckApi,
    private val mapper: ResponseToEntityMapper,
    private val datastoreManager: DatastoreManager
) : IChuckRepository {
    override suspend fun getCategories(): List<JokeCategory> =
        withContext(Dispatchers.IO) {
            val response = api.getCategories()
            mapper.mapCategory(response)
        }

    override suspend fun getByCategory(category: JokeCategory): JokeEntity =
        withContext(Dispatchers.IO) {
            val response = api.getRandomJoke(category.string)
            mapper.mapJoke(response)
        }

    override suspend fun getLastLoadedJoke(): JokeEntity =
        withContext(Dispatchers.IO) {
            datastoreManager.getJoke().first()
        }

    override suspend fun saveJoke(joke: JokeEntity) =
        withContext(Dispatchers.IO){
            datastoreManager.saveJoke(joke)
        }
}