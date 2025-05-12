package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.storage.ReciterStorage

/**
* @author Zaur
* @since 2025-05-12
*/

interface ReciterUseCase {

    fun getReciter(): String?
    fun saveReciter(identifier: String)

    class Base(
        private val reciterStorage: ReciterStorage,
    ) : ReciterUseCase {
        override fun saveReciter(identifier: String) {
            reciterStorage.saveSelectedReciter(identifier)
        }

        override fun getReciter(): String? = reciterStorage.getSelectedReciter()
    }

}