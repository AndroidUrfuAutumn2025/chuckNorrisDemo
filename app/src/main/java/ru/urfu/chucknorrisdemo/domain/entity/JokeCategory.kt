package ru.urfu.chucknorrisdemo.domain.entity

enum class JokeCategory(val string: String) {
    ANIMAL("animal"),
    CAREER("career"),
    CELEBRITY("celebrity"),
    DEV("dev"),
    EXPLICIT("explicit"),
    FASHION("fashion"),
    FOOD("food"),
    HISTORY("history"),
    MONEY("money"),
    MOVIE("movie"),
    MUSIC("music"),
    POLITICAL("political"),
    RELIGION("religion"),
    SCIENCE("science"),
    SPORT("sport"),
    TRAVEL("travel"),
    OTHER("other");

    companion object {
        fun getByValue(type: String?) =
            JokeCategory.entries.find { it.name.equals(type, ignoreCase = true) }
                ?: ru.urfu.chucknorrisdemo.domain.entity.JokeCategory.OTHER
    }
}