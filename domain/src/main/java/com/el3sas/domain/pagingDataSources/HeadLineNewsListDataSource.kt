package com.el3sas.domain.pagingDataSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.el3sas.data.repos.NewsListRepo
import com.el3sas.entities.ArticlesItem

class HeadLineNewsListDataSource constructor(
    private val repo: NewsListRepo,
    private val country: String,
) : PagingSource<Int, ArticlesItem>() {

    override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> =
        try {
            val key = params.key ?: 1

            val response = repo.getHeadlineNewsList(
                country,
                key
            )
            response.fold(onSuccess = {
                LoadResult.Page(
                    it.articles?.filterNotNull() ?: emptyList(),
                    nextKey = if (it.articles.isNullOrEmpty().not()) key + 1 else null,
                    prevKey = if (key != 0) key - 1 else null
                )
            }, onFailure = {
                if (it.message?.contains("Maximum Results Reached") == true) {
                    LoadResult.Page(
                        data = emptyList(),
                        nextKey = null,
                        prevKey = if (key != 0) key - 1 else null
                    )
                } else LoadResult.Error(it)
            })

        } catch (e: Exception) {
            LoadResult.Error(Throwable(e.message ?: ""))
        }
}