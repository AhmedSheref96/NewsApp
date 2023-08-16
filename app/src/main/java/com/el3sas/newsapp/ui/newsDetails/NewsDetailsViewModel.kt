package com.el3sas.newsapp.ui.newsDetails

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor() : ViewModel() {

    private val _viewState = MutableStateFlow<NewsDetailsViewState>(NewsDetailsViewState.Idle)
    val viewState = _viewState.asStateFlow()

    fun handleIntent(newsDetailsIntent: NewsDetailsIntent) {
        when (newsDetailsIntent) {
            is NewsDetailsIntent.GetDetails -> {
                _viewState.value =
                    NewsDetailsViewState.GetDetails(newsDetailsIntent.navArgs.newsData)
            }

            is NewsDetailsIntent.ReloadDetails -> {
                _viewState.value =
                    NewsDetailsViewState.GetDetails(newsDetailsIntent.navArgs.newsData)
            }

            is NewsDetailsIntent.GetMoreDetails -> {
                _viewState.value = NewsDetailsViewState.OpenOnBrowser(newsDetailsIntent.url)
            }
        }
    }


}