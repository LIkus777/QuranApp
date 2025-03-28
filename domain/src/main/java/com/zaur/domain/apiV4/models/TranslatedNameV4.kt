package com.zaur.domain.apiV4.models

import com.google.gson.annotations.SerializedName
import com.zaur.domain.apiV4.models.recitations.LanguageNameV4

data class TranslatedNameV4(
    val name: String, @SerializedName("language_name") val languageNameV4: LanguageNameV4
)