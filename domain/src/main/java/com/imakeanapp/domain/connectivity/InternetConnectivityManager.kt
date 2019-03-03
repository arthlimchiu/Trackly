package com.imakeanapp.domain.connectivity

/**
 * Represents a basic interface to be implemented by classes
 * dealing with internet connectivity.
 */
interface InternetConnectivityManager {

    /**
     * Returns true if device has internet connection and false if it doesn't
     * have an internet connection
     * @return true or false
     */
    fun hasInternetConnection(): Boolean
}