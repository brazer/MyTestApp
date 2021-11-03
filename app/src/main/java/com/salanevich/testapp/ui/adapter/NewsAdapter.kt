package com.salanevich.testapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.salanevich.testapp.R
import com.salanevich.testapp.databinding.ItemNewsBinding
import com.salanevich.testapp.model.NewsModel

class NewsAdapter(
    private val openItemAction: (String) -> Unit
): PagingDataAdapter<NewsModel, NewsAdapter.NewsViewHolder>(NewsComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding, openItemAction)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class NewsViewHolder(
        binding: ItemNewsBinding,
        private val openItemAction: (String) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        private val ivPicture = binding.ivPicture
        private val tvTitle = binding.tvTitle
        private val tvDetails = binding.tvDetails

        fun bind(item: NewsModel) {
            ivPicture.load(item.urlPicture) {
                placeholder(R.drawable.placeholder)
                error(R.drawable.placeholder)
            }
            tvTitle.text = item.title
            tvDetails.text = item.details
            itemView.setOnClickListener { openItemAction(item.url) }
        }

    }

    object NewsComparator: DiffUtil.ItemCallback<NewsModel>() {
        override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
            return oldItem == newItem
        }
    }

}