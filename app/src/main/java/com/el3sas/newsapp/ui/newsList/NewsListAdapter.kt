package com.el3sas.newsapp.ui.newsList

import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.viewbinding.ViewBinding
import com.el3asas.utils.binding.RecyclerPagedDataAdapterBinding
import com.el3sas.entities.ArticlesItem
import com.el3sas.newsapp.databinding.ItemNewsHomeBinding

class NewsListAdapter(
    listener: ItemClickListener,
    repoComparator: ItemCallback<ArticlesItem>,
    override val bindingInflater: (LayoutInflater) -> ViewBinding = ItemNewsHomeBinding::inflate
) : RecyclerPagedDataAdapterBinding<ItemNewsHomeBinding, ArticlesItem>(
    itemClickListener = listener, repoComparator
) {
    override fun onBindViewHolder(holder: MainViewHolder<ItemNewsHomeBinding>, position: Int) {
        holder.binding.apply {
            viewModel = getItem(position)
        }
        holder.bindListener()
    }

    fun getItemData(position: Int) = getItem(position)
}