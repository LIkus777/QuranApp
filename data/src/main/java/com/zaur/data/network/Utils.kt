package com.zaur.data.network

import retrofit2.HttpException
import kotlinx.coroutines.delay

suspend fun <T> retryWithBackoff(
    maxAttempts: Int = 10,
    initialDelay: Long = 500,
    factor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelay
    repeat(maxAttempts - 1) {
        try {
            return block()
        } catch (e: HttpException) {
            if (e.code() != 429) throw e
        } catch (e: Exception) {
            throw e
        }
        delay(currentDelay)
        currentDelay = (currentDelay * factor).toLong()
    }
    return block() // финальная попытка без задержки
}
