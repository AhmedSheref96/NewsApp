package com.el3sas.data.repos

import com.el3sas.entities.NewsListResponse

interface NewsListRepo {

    suspend fun getNewsList(
        searchWord: String? = null,
        dateFrom: String? = null,
        dateTo: String? = null,
        sortBy: String = "publishedAt",
        page: Int? = null
    ): Result<NewsListResponse>

}