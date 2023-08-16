package com.el3sas.newsapp.ui.newsDetails

sealed class NewsDetailsIntent {
    data class GetDetails(val navArgs: NewsDetailsFragmentArgs) : NewsDetailsIntent()
    data class ReloadDetails(val navArgs: NewsDetailsFragmentArgs) : NewsDetailsIntent()
    data class GetMoreDetails(val url: String? = null) : NewsDetailsIntent()
}