package ru.urfu.chucknorrisdemo.domain.repository

import ru.urfu.chucknorrisdemo.data.mock.MockData

class FactsRepository {
    fun getFacts() = MockData.facts
}