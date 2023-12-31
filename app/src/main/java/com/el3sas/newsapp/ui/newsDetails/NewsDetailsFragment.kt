package com.el3sas.newsapp.ui.newsDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.el3asas.utils.binding.FragmentBinding
import com.el3sas.entities.ArticlesItem
import com.el3sas.newsapp.R
import com.el3sas.newsapp.databinding.FragmentNewsDetailsBinding
import com.el3sas.newsapp.helpers.bindImgWithGlide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class NewsDetailsFragment(override val bindingInflater: (LayoutInflater) -> ViewBinding = FragmentNewsDetailsBinding::inflate) :
    FragmentBinding<FragmentNewsDetailsBinding>() {

    private val viewModel by viewModels<NewsDetailsViewModel>()
    private val args by navArgs<NewsDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.render()
        binding.openInBrowser.setOnClickListener {
            viewModel.handleIntent(NewsDetailsIntent.GetMoreDetails(viewModel.viewState.value.articlesItem?.url))
        }
    }

    private fun CoroutineScope.render() {
        launch {
            viewModel.viewState.collectLatest {
                Timber.d("viewState = $it")
                when (it) {
                    is NewsDetailsViewState.Idle -> {
                        binding.dataGroup.isVisible = false
                        binding.loader.isVisible = true
                        viewModel.handleIntent(NewsDetailsIntent.GetDetails(args))
                    }

                    is NewsDetailsViewState.Error -> {}

                    is NewsDetailsViewState.GetDetails -> {
                        binding.dataGroup.isVisible = true
                        binding.loader.isVisible = false
                        it.articlesItem?.let { it1 -> updateViews(it1) }
                    }

                    is NewsDetailsViewState.OpenOnBrowser -> {
                        Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(it.url.toString())
                            startActivity(Intent.createChooser(this, "Open In"))
                        }
                    }
                }
            }
        }
    }

    private fun updateViews(articlesItem: ArticlesItem) {
        binding.apply {
            date.text = articlesItem.publishedAt?.let {
                convertUtcToFormattedDate(it)
            }
            bindImgWithGlide(img, articlesItem.urlToImage)
            title.text = articlesItem.title
            description.text = articlesItem.description
            content.text = articlesItem.content
            author.text = getString(R.string.auther) + articlesItem.author
            source.text = articlesItem.source?.name
        }
    }

    private fun convertUtcToFormattedDate(utcTimestamp: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.US)
        val date = inputFormat.parse(utcTimestamp)
        return outputFormat.format(date)
    }
}