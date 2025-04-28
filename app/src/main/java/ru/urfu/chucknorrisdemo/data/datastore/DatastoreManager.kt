package ru.urfu.chucknorrisdemo.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.urfu.chucknorrisdemo.domain.entity.JokeEntity

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "last_joke")

class DatastoreManager(
    private val context: Context
) {
    private val dataStore = context.dataStore

    companion object {
        val ID = stringPreferencesKey("id")
        val URL = stringPreferencesKey("url")
        val VALUE = stringPreferencesKey("value")
    }

    suspend fun saveJoke(joke: JokeEntity) {
        dataStore.edit { preferences ->
            preferences[ID] = joke.id
            preferences[URL] = joke.url
            preferences[VALUE] = joke.value
        }
    }

    suspend fun getJoke(): Flow<JokeEntity> =
        dataStore.data
            .map { preferences ->
                JokeEntity(
                    id = preferences[ID] ?: "BUmvu__NSX2mivfyYCXTpw",
                    url = preferences[URL] ?: "",
                    value = preferences[VALUE] ?: "God help the man who disagrees with Chuck Norris. Or not, because God's afraid of him, too."
                )
            }
}