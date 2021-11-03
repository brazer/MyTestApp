package com.salanevich.testapp.ui.adapter.holder

import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.salanevich.testapp.databinding.LoadStateItemBinding

class LoadStateViewHolder(binding: LoadStateItemBinding): RecyclerView.ViewHolder(binding.root) {

    private val progressBar: ProgressBar = binding.progressBar
    private val errorMessage: TextView = binding.errorMessage

    fun bind(state: LoadState) {
        if (state is LoadState.Error) {
            errorMessage.text = state.error.localizedMessage
        }
        progressBar.isVisible = state is LoadState.Loading
        errorMessage.isVisible = state is LoadState.Error
    }

}