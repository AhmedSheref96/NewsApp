package com.el3sas.newsapp.ui.newsList


import android.graphics.Rect
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import androidx.viewbinding.ViewBinding
import androidx.fragment.app.viewModels
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.el3asas.utils.binding.FragmentBinding
import com.el3sas.newsapp.databinding.FragmentNewsListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class NewsListFragment(override val bindingInflater: (LayoutInflater) -> ViewBinding = FragmentNewsListBinding::inflate) :
    FragmentBinding<FragmentNewsListBinding>() {

    private val viewModel by viewModels<NewsListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.render()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = viewModel.newsListAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
                ) {
                    outRect.offset(
                        resources.getDimensionPixelOffset(com.intuit.sdp.R.dimen._16sdp),
                        resources.getDimensionPixelOffset(com.intuit.sdp.R.dimen._5sdp)
                    )
                }
            })
        }
    }

    private fun CoroutineScope.render() {
        launch {
            viewModel.viewState.collect {
                when (it) {
                    is NewsListViewState.GetNewsListData -> {
                        Timber.d("")
                        binding.loader.isVisible = false
                        viewModel.newsListAdapter.submitData(it.pagedData)
                        binding.dataGroup.isVisible = true
                    }

                    is NewsListViewState.Idle -> {
                        binding.loader.isVisible = false
                        viewModel.handleIntents(NewsListIntent.GetAllList)
                        binding.dataGroup.isVisible = true
                    }

                    is NewsListViewState.Loading -> {
                        binding.loader.isVisible = true
                        binding.dataGroup.isVisible = false
                    }

                    is NewsListViewState.Error -> {
                        binding.loader.isVisible = false
                        binding.dataGroup.isVisible = false
                    }
                }
            }
        }
    }
}