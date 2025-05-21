package com.zaur.domain.al_quran_cloud.models.edition

import com.google.gson.annotations.SerializedName

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface Types {

    fun <T> map(mapper: Mapper<T>): T

    fun code(): Long
    fun status(): String
    fun types(): List<String>

    class Base(
        @SerializedName("code") private val code: Long,
        @SerializedName("status") private val status: String,
        @SerializedName("data") private val types: List<String>,
    ) : Types {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(code, status, types)
        override fun code(): Long = code
        override fun status(): String = status
        override fun types(): List<String> = types
    }

    interface Mapper<T> {
        fun map(
            code: Long,
            status: String,
            data: List<String>,
        ): T
    }

    object Empty : Types {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(code(), status(), types())
        override fun code(): Long = 0L
        override fun status(): String = ""
        override fun types(): List<String> = emptyList()
    }
}