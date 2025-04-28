package ru.urfu.chucknorrisdemo.core.networkUtils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.urfu.chucknorrisdemo.domain.repository.INetworkConnectivityObserver

class NetworkManager(
    private val connectivityObserver: INetworkConnectivityObserver
) {
    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    init {
        _isConnected.value = connectivityObserver.isConnected()
        observeNetwork()
    }

    private fun observeNetwork() {
        CoroutineScope(Dispatchers.IO).launch {
            connectivityObserver.observe().collect { status ->
                _isConnected.value = status == INetworkConnectivityObserver.NetworkStatus.AVAILABLE
            }
        }
    }
}