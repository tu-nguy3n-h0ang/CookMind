package com.multiverse.cookmind.util

sealed class Outcome<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Loading<T>(data: T? = null) : Outcome<T>(data) // May contains old data before load new data
    class Success<T>(data: T) : Outcome<T>(data) // Always contains new data from api returns
    class Error<T>(message: String, data: T? = null) : Outcome<T>(data, message) // Contains old data and message detail about error
}