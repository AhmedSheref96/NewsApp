package com.el3sas.data.network

import com.el3sas.data.BuildConfig

object EndPoint {

    const val baseUrl: String = BuildConfig.baseUrl

    const val connectTimeout = 1L
    const val writeTimeout = 1L
    const val readTimeout = 1L
    const val callTimeout = 1L

}