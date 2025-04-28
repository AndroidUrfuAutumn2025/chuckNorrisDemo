package ru.urfu.chucknorrisdemo.data.mapper

import ru.urfu.chucknorrisdemo.data.model.JokeResponse
import ru.urfu.chucknorrisdemo.domain.entity.JokeCategory
import ru.urfu.chucknorrisdemo.domain.entity.JokeEntity

class ResponseToEntityMapper {
    fun mapCategory(response: List<String>) =
        response.map { category ->
            JokeCategory.getByValue(category)
        }.toList()

    fun mapJoke(response: JokeResponse) =
        JokeEntity(
            response.id.orEmpty(),
            response.url.orEmpty(),
            response.value.orEmpty()
        )
}