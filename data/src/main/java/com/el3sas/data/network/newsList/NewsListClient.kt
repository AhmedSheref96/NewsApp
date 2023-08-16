package com.el3sas.data.network.newsList

import com.el3sas.data.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsListClient @Inject constructor(
    private val service: NewsListService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun getFilterableNewsList(
        searchWord: String,
        dateFrom: String? = null,
        dateTo: String? = null,
        sortBy: String = "publishedAt",
        page: Int? = null
    ) = withContext(dispatcher) {
        service.getFilterableNewsList(
            searchWord = searchWord,
            dateFrom = dateFrom,
            dateTo = dateTo,
            sortBy = sortBy,
            page = page
        )
    }

    suspend fun getHeadLines(
        country: String? = "eg", page: Int? = null
    ) = withContext(dispatcher) {
        service.getHeadLines(
            country = country,
            page = page
        )
    }

}