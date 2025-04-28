package ru.urfu.chucknorrisdemo.domain.repository

import kotlinx.coroutines.flow.Flow

interface INetworkConnectivityObserver {
    fun observe(): Flow<NetworkStatus>
    fun isConnected(): Boolean

    enum class NetworkStatus {
        AVAILABLE,
        UNAVAILABLE,
        LOSING,
        LOST
    }
}