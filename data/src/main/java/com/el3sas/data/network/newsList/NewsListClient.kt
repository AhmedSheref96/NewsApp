package com.el3sas.data.network.newsList

import com.el3sas.data.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsListClient @Inject constructor(
    private val service: NewsListService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun getNewsList(
        searchWord: String? = null,
        dateFrom: String? = null,
        dateTo: String? = null,
        sortBy: String = "publishedAt",
        page: Int? = null
    ) = withContext(dispatcher) {
        service.getNewsList(
            searchWord = searchWord,
            dateFrom = dateFrom,
            dateTo = dateTo,
            sortBy = sortBy,
            page = page
        )
    }

}