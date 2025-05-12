package com.zaur.domain.base

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import java.lang.reflect.Type

/**
* @author Zaur
* @since 2025-05-12
*/

class SajdaAdapter : JsonDeserializer<Boolean?> {
    override fun deserialize(
        json: com.google.gson.JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): Boolean? {
        return when {
            json?.isJsonPrimitive == true -> json.asBoolean
            json?.isJsonObject == true -> {
                val obj = json.asJsonObject
                obj["recommended"]?.asBoolean ?: obj["obligatory"]?.asBoolean ?: false
            }
            else -> false
        }
    }
}