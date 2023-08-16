package com.el3sas.newsapp.ui.newsList


import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import androidx.viewbinding.ViewBinding
import androidx.fragment.app.viewModels
import android.view.LayoutInflater
import android.view.View
import com.el3asas.utils.binding.FragmentBinding
import com.el3sas.newsapp.databinding.FragmentNewsListBinding

@AndroidEntryPoint
class NewsListFragment(override val bindingInflater: (LayoutInflater) -> ViewBinding = FragmentNewsListBinding::inflate) :
    FragmentBinding<FragmentNewsListBinding>() {

    private val viewModel by viewModels<NewsListViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}