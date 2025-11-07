package ru.urfu.chucknorrisdemo.data.local

import android.content.Context

class JokeStorage(context: Context) {
    private val prefs = context.getSharedPreferences("jokes", Context.MODE_PRIVATE)

    fun saveJoke(joke: String) {
        prefs.edit().putString("last_joke", joke).apply()
    }

    fun getLastJoke(): String? = prefs.getString("last_joke", null)
}
