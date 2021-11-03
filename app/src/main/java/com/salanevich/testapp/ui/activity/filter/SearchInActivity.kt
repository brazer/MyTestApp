package com.salanevich.testapp.ui.activity.filter

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.salanevich.testapp.cache.filter.Filter
import com.salanevich.testapp.databinding.ActivitySearchInBinding
import com.salanevich.testapp.vm.filter.SearchInViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchInActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, SearchInActivity::class.java)
    }

    private val viewModel: SearchInViewModel by viewModel()
    private lateinit var binding: ActivitySearchInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.searchIn.observe(this, { list ->
            list.forEach { item ->
                when (item) {
                    Filter.SearchIn.TITLE -> binding.titleSwitcher.isChecked = true
                    Filter.SearchIn.DESC -> binding.descSwitcher.isChecked = true
                    Filter.SearchIn.CONTENT -> binding.contentSwitcher.isChecked = true
                }
            }
        })
        viewModel.initData()
        binding.titleSwitcher.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.putTitle()
            } else viewModel.removeTitle()
        }
        binding.descSwitcher.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.putDesc()
            } else viewModel.removeDesc()
        }
        binding.contentSwitcher.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.putContent()
            } else viewModel.removeContent()
        }
        binding.btnBack.setOnClickListener { finish() }
        binding.btnClear.setOnClickListener {
            binding.titleSwitcher.isChecked = false
            binding.descSwitcher.isChecked = false
            binding.contentSwitcher.isChecked = false
            viewModel.clearData()
        }
        binding.btnApply.setOnClickListener {
            viewModel.applyData()
            finish()
        }
    }

}