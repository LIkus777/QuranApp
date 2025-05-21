package com.zaur.domain.al_quran_cloud.models.edition

import com.google.gson.annotations.SerializedName

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface Languages {

    fun code(): Long
    fun status(): String
    fun data(): List<String>

    fun <T> map(mapper: Mapper<T>): T

    data class Base(
        @SerializedName("code") private val code: Long,
        @SerializedName("status") private val status: String,
        @SerializedName("data") private val data: List<String>,
    ) : Languages {
        override fun code(): Long = code
        override fun status(): String = status
        override fun data(): List<String> = data

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(code, status, data)
    }

    interface Mapper<T> {
        fun map(
            code: Long,
            status: String,
            data: List<String>,
        ): T
    }

    object Empty : Languages {
        override fun code(): Long = 0L
        override fun status(): String = ""
        override fun data(): List<String> = emptyList()

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(code(), status(), data())
    }
}