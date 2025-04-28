package ru.urfu.chucknorrisdemo.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ru.urfu.chucknorrisdemo.domain.repository.INetworkConnectivityObserver

class NetworkConnectivityObserver(
    private val context: Context
) : INetworkConnectivityObserver {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun isConnected(): Boolean {
        val networkCapabilities = connectivityManager.activeNetwork?.let { connectivityManager.getNetworkCapabilities(it) }
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    override fun observe(): Flow<INetworkConnectivityObserver.NetworkStatus> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(INetworkConnectivityObserver.NetworkStatus.LOST) }
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(INetworkConnectivityObserver.NetworkStatus.AVAILABLE) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(INetworkConnectivityObserver.NetworkStatus.UNAVAILABLE) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(INetworkConnectivityObserver.NetworkStatus.LOSING) }
                }
            }

            connectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }
}