package com.el3sas.domain.useCases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.el3sas.data.di.NewsListRepoForHome
import com.el3sas.domain.pagingDataSources.FilterableNewsListDataSource
import com.el3sas.data.repos.NewsListRepo
import com.el3sas.domain.pagingDataSources.HeadLineNewsListDataSource
import javax.inject.Inject

class GetNewsList @Inject constructor(
    @NewsListRepoForHome private val repo: NewsListRepo
) {

    operator fun invoke(
        searchWord: String,
        dateFrom: String? = null,
        dateTo: String? = null,
        sortBy: String = "publishedAt"
    ) = Pager(config = PagingConfig(pageSize = 10)) {
        FilterableNewsListDataSource(repo, searchWord, dateFrom, dateTo, sortBy)
    }.flow

    operator fun invoke(country: String) = Pager(config = PagingConfig(pageSize = 10)) {
        HeadLineNewsListDataSource(repo, country)
    }.flow

}