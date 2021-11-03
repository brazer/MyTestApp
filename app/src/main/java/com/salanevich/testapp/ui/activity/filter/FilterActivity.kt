package com.salanevich.testapp.ui.activity.filter

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.salanevich.testapp.R
import com.salanevich.testapp.databinding.ActivityFilterBinding
import com.salanevich.testapp.ui.view.DateTextWatcher
import com.salanevich.testapp.utils.dateFormatter
import com.salanevich.testapp.vm.filter.FilterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*

class FilterActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, FilterActivity::class.java)
    }

    private val viewModel: FilterViewModel by viewModel()
    private lateinit var binding: ActivityFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFilter()
        initViews()
        viewModel.initDates()
    }

    private fun initFilter() {
        viewModel.fromDate.observe(this, { value ->
            if (value != null) {
                val date = Date(value)
                binding.etDateFrom.setText(dateFormatter.format(date))
            } else binding.etDateFrom.text = null
        })
        viewModel.toDate.observe(this, { value ->
            if (value != null) {
                val date = Date(value)
                binding.etDateTo.setText(dateFormatter.format(date))
            } else binding.etDateTo.text = null
        })
        viewModel.searchIn.observe(this, { list ->
            when {
                list.isEmpty() -> {
                    binding.tvSearchInValue.text = getString(R.string.none)
                }
                list.size == 3 -> {
                    binding.tvSearchInValue.text = getString(R.string.all)
                }
                else -> {
                    val values = list.map { getString(it.id) }
                    binding.tvSearchInValue.text = TextUtils.join(",", values)
                }
            }
        })
    }

    private fun initViews() {
        binding.etDateFrom.addTextChangedListener(DateTextWatcher(binding.etDateFrom))
        binding.etDateTo.addTextChangedListener(DateTextWatcher(binding.etDateTo))
        binding.tvSearchInValue.setOnClickListener {
            startActivity(SearchInActivity.createIntent(applicationContext))
        }
        binding.btnBack.setOnClickListener { finish() }
        binding.btnClear.setOnClickListener {
            binding.etDateFrom.setText("")
            binding.etDateTo.setText("")
            binding.tvSearchInValue.text = getString(R.string.none)
        }
        binding.btnApply.setOnClickListener {
            val from = binding.etDateFrom.text.toString()
            val to = binding.etDateTo.text.toString()
            val searchIn = binding.tvSearchInValue.text.toString()
            try {
                viewModel.applyFilter(from, to, searchIn, applicationContext)
                setResult(Activity.RESULT_OK)
                finish()
            } catch (e: Exception) {
                Timber.e(e)
                Toast.makeText(applicationContext, R.string.message_something_went_wrong, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.initSearchIn()
    }
}