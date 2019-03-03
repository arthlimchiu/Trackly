package com.imakeanapp.data.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.imakeanapp.domain.connectivity.InternetConnectivityManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityManagerImpl @Inject constructor(private val context: Context) : InternetConnectivityManager {
    override fun hasInternetConnection(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }
}