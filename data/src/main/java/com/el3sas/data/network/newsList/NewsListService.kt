package com.el3sas.data.network.newsList


import com.el3sas.entities.NewsListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsListService {

    @GET("everything")
    suspend fun getNewsList(
        @Query("searchWord") searchWord: String? = null,
        @Query("q") title: String = "apple",
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("page") page: Int? = null
    ): NewsListResponse


}