package com.el3sas.domain.useCases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.el3sas.data.di.NewsListRepoForHome
import com.el3sas.domain.pagingDataSources.NewsListDataSource
import com.el3sas.data.repos.NewsListRepo
import javax.inject.Inject

class GetNewsList @Inject constructor(
    @NewsListRepoForHome private val repo: NewsListRepo
) {

    operator fun invoke(
        searchWord: String? = null,
        dateFrom: String? = null,
        dateTo: String? = null,
        sortBy: String = "publishedAt"
    ) = Pager(config = PagingConfig(pageSize = 10)) {
        NewsListDataSource(repo, searchWord, dateFrom, dateTo, sortBy)
    }.flow

}


