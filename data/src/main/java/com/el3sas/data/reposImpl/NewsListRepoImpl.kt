package com.el3sas.data.reposImpl

import com.el3sas.data.network.newsList.NewsListClient
import com.el3sas.data.repos.NewsListRepo
import com.el3sas.entities.NewsListResponse
import javax.inject.Inject

class NewsListRepoImpl @Inject constructor(private val newsListClient: NewsListClient) :
    NewsListRepo {

    override suspend fun getNewsList(
        searchWord: String?, dateFrom: String?, dateTo: String?, sortBy: String, page: Int?
    ): Result<NewsListResponse> = try {
        val response = newsListClient.getNewsList(searchWord, dateFrom, dateTo, sortBy,page)
        if (response.status == "ok") Result.success(response)
        else Result.failure(Throwable(response.message))
    } catch (e: Exception) {
        Result.failure(Throwable(e.message))
    }

}