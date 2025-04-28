package ru.urfu.chucknorrisdemo.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.urfu.chucknorrisdemo.data.datastore.DatastoreManager

val datastoreModule = module {
    single { DatastoreManager(androidContext()) }
}