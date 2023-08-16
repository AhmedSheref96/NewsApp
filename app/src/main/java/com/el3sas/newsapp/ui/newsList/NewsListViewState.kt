package com.el3sas.newsapp.ui.newsList

import androidx.paging.PagingData
import com.el3sas.entities.ArticlesItem


sealed class NewsListViewState {

    data object Idle : NewsListViewState()
    data object Loading : NewsListViewState()
    data class GetNewsListData(val pagedData: PagingData<ArticlesItem>) : NewsListViewState()
    data class Error(val throwable: Throwable? = null) : NewsListViewState()

}