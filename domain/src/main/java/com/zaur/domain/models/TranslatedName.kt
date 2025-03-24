package com.zaur.domain.models

import com.google.gson.annotations.SerializedName
import com.zaur.domain.models.recitations.LanguageName

data class TranslatedName(
    val name: String, @SerializedName("language_name") val languageName: LanguageName
)