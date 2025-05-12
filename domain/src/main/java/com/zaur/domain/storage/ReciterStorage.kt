package com.zaur.domain.storage

/**
* @author Zaur
* @since 2025-05-12
*/

interface ReciterStorage {
    fun saveSelectedReciter(identifier: String)
    fun getSelectedReciter(): String?
}