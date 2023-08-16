package com.el3sas.newsapp.ui.newsList

sealed class NewsListIntent {
    data object GetAllList : NewsListIntent()
    data class GetFilteredList(
        val searchWord: String,
        val dateFrom: String? = null,
        val dateTo: String? = null,
        val sortBy: String = "publishedAt"
    ) : NewsListIntent()

    data object Refresh : NewsListIntent()

}