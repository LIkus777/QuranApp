package com.zaur.features.surah.manager

import com.zaur.data.al_quran_aqc.constans.ReciterList
import com.zaur.domain.al_quran_cloud.use_case.ReciterUseCase

interface ReciterManager {

    fun getReciter(): String?
    fun saveReciter(identifier: String)
    fun getReciterName(): String?

    class Base(
        private val reciterUseCase: ReciterUseCase,
    ) : ReciterManager {
        override fun saveReciter(identifier: String) {
            reciterUseCase.saveReciter(identifier)
        }

        override fun getReciter(): String? = reciterUseCase.getReciter()

        override fun getReciterName(): String? {
            val identifier = getReciter()
            return ReciterList.reciters[identifier] // Берем имя чтеца из списка
        }
    }
}