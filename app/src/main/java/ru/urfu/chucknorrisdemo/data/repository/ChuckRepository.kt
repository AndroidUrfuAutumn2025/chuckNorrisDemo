package ru.urfu.chucknorrisdemo.data.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.urfu.chucknorrisdemo.data.api.ChuckApi
import ru.urfu.chucknorrisdemo.domain.repository.IChuckRepository

class ChuckRepository(
    private val api: ChuckApi,
    private val context: Context
) : IChuckRepository {

    private val prefs = context.getSharedPreferences("chuck_prefs", Context.MODE_PRIVATE)

    override suspend fun getJokeByCategory(category: String): String = withContext(Dispatchers.IO) {
        try {
            val joke = api.getRandomJoke(category).value
            prefs.edit().putString("last_joke", joke).apply()
            joke
        } catch (e: Exception) {
            prefs.getString("last_joke", "Ошибка при загрузке шутки") ?: "Нет данных"
        }
    }
}
