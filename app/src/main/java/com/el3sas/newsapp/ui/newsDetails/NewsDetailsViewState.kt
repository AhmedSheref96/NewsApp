package com.el3sas.newsapp.ui.newsDetails

import com.el3sas.entities.ArticlesItem

sealed class NewsDetailsViewState(open val articlesItem: ArticlesItem? = null) {

    data object Idle : NewsDetailsViewState()
    data class OpenOnBrowser(val url: String? = null) : NewsDetailsViewState()
    data class GetDetails(override val articlesItem: ArticlesItem? = null) : NewsDetailsViewState()
    data class Error(val throwable: Throwable? = null) : NewsDetailsViewState()

}