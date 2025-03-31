package com.zaur.domain.storage

interface ReciterStorage {
    fun saveSelectedReciter(identifier: String)
    fun getSelectedReciter(): String?
}