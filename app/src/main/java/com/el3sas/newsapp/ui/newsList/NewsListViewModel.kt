package com.el3sas.newsapp.ui.newsList

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.paging.cachedIn
import androidx.recyclerview.widget.DiffUtil
import com.el3asas.utils.binding.RecyclerPagedDataAdapterBinding
import com.el3asas.utils.utils.navigate
import com.el3sas.domain.useCases.GetNewsList
import com.el3sas.entities.ArticlesItem
import com.el3sas.newsapp.helpers.NewsListLoadStateAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(private val getNewsList: GetNewsList) : ViewModel(),
    RecyclerPagedDataAdapterBinding.ItemClickListener {

    private val _viewState = MutableStateFlow<NewsListViewState>(NewsListViewState.Idle)
    val viewState: StateFlow<NewsListViewState>
        get() = _viewState.asStateFlow()

    val newsListAdapter = NewsListAdapter(this, object : DiffUtil.ItemCallback<ArticlesItem>() {
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem.title == newItem.title && oldItem.source?.name == newItem.source?.name && oldItem.urlToImage == newItem.urlToImage
        }
    }).apply {
        withLoadStateFooter(footer = NewsListLoadStateAdapter(this))
    }

    private fun getFilterableData(
        searchWord: String,
        dateFrom: String? = null,
        dateTo: String? = null,
        sortBy: String = "publishedAt"
    ) {
        viewModelScope.launch {
            getNewsList(
                searchWord = searchWord, dateFrom = dateFrom, dateTo = dateTo, sortBy = sortBy
            ).cachedIn(viewModelScope).collectLatest {
                Timber.d("getFilterableData $it")
                _viewState.value = NewsListViewState.GetNewsListData(it)
            }
        }
    }

    private fun getHeadLinesData(
        country: String = "eg"
    ) {
        viewModelScope.launch {
            getNewsList(country = country).cachedIn(viewModelScope).collectLatest {
                Timber.d("getHeadLinesData $it")
                _viewState.value = NewsListViewState.GetNewsListData(it)
            }
        }
    }

    fun handleIntents(intent: NewsListIntent) {
        Timber.d("intent  = $intent")
        when (intent) {
            is NewsListIntent.GetAllList, is NewsListIntent.Refresh -> {
                Timber.d("NewsListIntent.GetFilteredList")
                getHeadLinesData()
            }

            is NewsListIntent.GetFilteredList -> {
                Timber.d("NewsListIntent.GetFilteredList")
                getFilterableData(intent.searchWord, intent.dateFrom, intent.dateTo, intent.sortBy)
            }
        }
    }

    override fun onItemClickListener(v: View, pos: Int) {
        navigate(
            v.findNavController(),
            NewsListFragmentDirections.actionNewsListFragmentToNewsDetailsFragment(
                newsListAdapter.getItemData(pos)!!
            )
        )
    }

}