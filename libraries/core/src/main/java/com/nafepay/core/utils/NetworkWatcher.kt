package com.nafepay.core.utils

import android.Manifest
import android.app.Application
import android.content.Context
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.ExperimentalCoroutinesApi
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine

@ExperimentalCoroutinesApi
class NetworkWatcher
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE) constructor(
    application: Application
) {

    private val connectivityManager =
        application.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

    // general availability of Internet over any type
    var isOnline = false
        get() {
            updateFields()
            return field
        }

    var isOverWifi = false
        get() {
            updateFields()
            return field
        }

    var isOverCellular = false
        get() {
            updateFields()
            return field
        }

    var isOverEthernet = false
        get() {
            updateFields()
            return field
        }

    companion object {
        @Volatile
        private var INSTANCE: NetworkWatcher? = null

        fun getInstance(application: Application): NetworkWatcher {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = NetworkWatcher(application)
                }
                return INSTANCE!!
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun updateFields() {

        val networkAvailability =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        if (networkAvailability != null &&
            networkAvailability.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            networkAvailability.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        ) {
            //has network
            isOnline = true

            // wifi
            isOverWifi =
                networkAvailability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)

            // cellular
            isOverCellular =
                networkAvailability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)

            // ethernet
            isOverEthernet =
                networkAvailability.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } else {
            isOnline = false
            isOverWifi = false
            isOverCellular = false
            isOverEthernet = false
        }
    }

    fun watchNetwork(): Flow<Boolean> = watchWifi()
        .combine(watchCellular()) { wifi, cellular -> wifi || cellular }
        .combine(watchEthernet()) { wifiAndCellular, ethernet -> wifiAndCellular || ethernet }

    fun watchNetworkFlow(): Flow<Boolean> = watchNetwork()

    fun watchWifi(): Flow<Boolean> = callbackFlowForType(NetworkCapabilities.TRANSPORT_WIFI)

    fun watchWifiFlow() = watchWifi()

    fun watchCellular(): Flow<Boolean> = callbackFlowForType(NetworkCapabilities.TRANSPORT_CELLULAR)

    fun watchCellularFlow() = watchCellular()

    fun watchEthernet(): Flow<Boolean> = callbackFlowForType(NetworkCapabilities.TRANSPORT_ETHERNET)

    fun watchEthernetFlow() = watchEthernet()

    //@IntRange(start = 0, endInclusive = 7)
    private fun callbackFlowForType( type: Int) = callbackFlow {

        trySend(false).isSuccess

        val networkRequest = NetworkRequest.Builder()
            .addTransportType(type)
            .build()

        val callback = object : ConnectivityManager.NetworkCallback() {


            override fun onLost(network: Network) {
                trySend(false).isSuccess
            }

            override fun onUnavailable() {
                trySend(false).isSuccess
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                // do nothing
            }

            override fun onAvailable(network: Network) {
                trySend(true).isSuccess
            }
        }

        connectivityManager.registerNetworkCallback(networkRequest, callback)

        awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
    }
}