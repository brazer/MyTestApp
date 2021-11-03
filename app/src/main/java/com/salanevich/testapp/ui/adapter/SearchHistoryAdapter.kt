package com.salanevich.testapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.salanevich.testapp.databinding.ItemHistorySearchBinding

class SearchHistoryAdapter(
    private val onClickAction: (String) -> Unit
): RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder>() {

    private var list: List<String> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<String>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val binding = ItemHistorySearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHistoryViewHolder(binding, onClickAction)
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class SearchHistoryViewHolder(
        binding: ItemHistorySearchBinding,
        private val onClickAction: (String) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        private val itemText = binding.tvText

        fun bind(text: String) {
            itemText.text = text
            itemView.setOnClickListener { onClickAction(text) }
        }

    }

}