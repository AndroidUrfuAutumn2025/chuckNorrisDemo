package ru.urfu.chucknorrisdemo.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.urfu.chucknorrisdemo.data.mapper.ResponseToEntityMapper
import ru.urfu.chucknorrisdemo.data.repository.ChuckRepository
import ru.urfu.chucknorrisdemo.domain.repository.IChuckRepository
import ru.urfu.chucknorrisdemo.presentation.viewModel.ChuckViewModel

val rootModule = module {
    factory { ResponseToEntityMapper() }
    single<IChuckRepository> { ChuckRepository(get(), get(), get()) }

    viewModel { ChuckViewModel(get(), get()) }
}