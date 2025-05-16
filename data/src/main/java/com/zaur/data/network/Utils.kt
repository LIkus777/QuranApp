package com.zaur.data.network

import retrofit2.HttpException
import kotlinx.coroutines.delay

/**
* @author Zaur
* @since 2025-05-12
*/

suspend fun <T> retryWithBackoff(
    maxAttempts: Int = 15,
    initialDelay: Long = 100,
    factor: Double = 1.1,
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
