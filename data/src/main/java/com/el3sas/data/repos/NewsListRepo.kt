package com.el3sas.data.repos

import com.el3sas.entities.NewsListResponse

interface NewsListRepo {

    suspend fun getFilterableNewsList(
        searchWord: String,
        dateFrom: String? = null,
        dateTo: String? = null,
        sortBy: String = "publishedAt",
        page: Int? = null
    ): Result<NewsListResponse>

    suspend fun getHeadlineNewsList(country: String, page: Int?): Result<NewsListResponse>
}