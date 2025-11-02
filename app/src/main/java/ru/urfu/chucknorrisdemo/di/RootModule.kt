package ru.urfu.chucknorrisdemo.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.urfu.chucknorrisdemo.domain.repository.FactsRepository
import ru.urfu.chucknorrisdemo.domain.viewmodel.GalleryViewModel
import ru.urfu.chucknorrisdemo.domain.viewmodel.RandomFactViewModel

val rootModule = module {
    singleOf(::FactsRepository)
    singleOf(::GalleryViewModel)
    singleOf(::RandomFactViewModel)
}