package com.el3sas.data.reposImpl

import com.el3sas.data.network.newsList.NewsListClient
import com.el3sas.data.repos.NewsListRepo
import com.el3sas.entities.NewsListResponse
import javax.inject.Inject

class NewsListRepoImpl @Inject constructor(private val newsListClient: NewsListClient) :
    NewsListRepo {

    override suspend fun getFilterableNewsList(
        searchWord: String, dateFrom: String?, dateTo: String?, sortBy: String, page: Int?
    ): Result<NewsListResponse> = try {
        val response =
            newsListClient.getFilterableNewsList(searchWord, dateFrom, dateTo, sortBy, page)
        if (response.status == "ok") Result.success(response)
        else if (response.code?.contains("maximumResultsReached") == true) {
            Result.failure(
                Throwable(
                    "Maximum Results Reached"
                )
            )
        } else Result.failure(Throwable(response.message))
    } catch (e: Exception) {
        Result.failure(Throwable(e.message))
    }

    override suspend fun getHeadlineNewsList(
        country: String, page: Int?
    ): Result<NewsListResponse> = try {
        val response = newsListClient.getHeadLines(country, page)
        if (response.status == "ok") Result.success(response)
        else if (response.code?.contains("maximumResultsReached") == true) {
            Result.failure(
                Throwable(
                    "Maximum Results Reached"
                )
            )
        } else Result.failure(Throwable(response.message))
    } catch (e: Exception) {
        Result.failure(Throwable(e.message))
    }

}