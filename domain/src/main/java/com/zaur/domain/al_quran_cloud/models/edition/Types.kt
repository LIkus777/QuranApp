package com.zaur.domain.al_quran_cloud.models.edition

import com.google.gson.annotations.SerializedName

interface Types {

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        @SerializedName("code") private val code: Long,
        @SerializedName("status") private val status: String,
        @SerializedName("data") private val data: List<String>,
    ) : Types {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(code, status, data)
    }

    interface Mapper<T> {
        fun map(
            code: Long,
            status: String,
            data: List<String>,
        ): T
    }
}