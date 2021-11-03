package com.salanevich.testapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.salanevich.testapp.R
import com.salanevich.testapp.cache.sorting.Sorting
import com.salanevich.testapp.databinding.FragmentSearchBinding
import com.salanevich.testapp.model.NewsModel
import com.salanevich.testapp.ui.activity.DetailsActivity
import com.salanevich.testapp.ui.adapter.ItemLoadStateAdapter
import com.salanevich.testapp.ui.adapter.NewsAdapter
import com.salanevich.testapp.ui.adapter.SearchHistoryAdapter
import com.salanevich.testapp.vm.SearchViewModel
import com.salanevich.testapp.ui.activity.filter.FilterActivityContract
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var searchAdapter: NewsAdapter
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var currentQuery: String? = null
    private var sortSelected = false
    private var sheetListener: BottomSheetListener? = null
    private lateinit var sortingParameter: Sorting.Parameter
    private var filterActivityLauncher = registerForActivityResult(FilterActivityContract()) { ok ->
        if (ok) {
            searchViewModel.loadFilter()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initSearchViews()
        initFilter()
        initSorting()
        initHistory()

        return root
    }

    private fun initSearchViews() {
        binding.textInputEditText.setOnEditorActionListener { v, actionId, event ->
            if ((actionId == EditorInfo.IME_ACTION_DONE) ||
                ((event.keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN))) {
                val query = v.text.toString()
                searchViewModel.addToHistory(query)
                search(query)
            }
            return@setOnEditorActionListener false
        }
        searchAdapter = NewsAdapter { url ->
            startActivity(DetailsActivity.createIntent(requireContext(), url))
        }
        searchAdapter.addLoadStateListener {
            binding.progressbar.isVisible = it.refresh is LoadState.Loading
            if (it.refresh is LoadState.Error) {
                binding.tvTitle.setText(R.string.message_something_went_wrong)
            }
            if (it.refresh is LoadState.NotLoading && NewsModel.count != -1) {
                binding.tvTitle.text = getString(R.string.news_count, NewsModel.count)
            }
        }
    }

    private fun search(query: String) {
        currentQuery = query
        binding.tvTitle.text = getString(R.string.search_progress)
        binding.list.adapter = searchAdapter.withLoadStateFooter(ItemLoadStateAdapter())
        lifecycleScope.launch {
            searchViewModel.search(query).collectLatest { data ->
                searchAdapter.submitData(data)
            }
        }
    }

    private fun initFilter() {
        binding.btnFilterLayout.setOnClickListener {
            filterActivityLauncher.launch(null)
        }
        searchViewModel.filter.observe(viewLifecycleOwner, { filter ->
            val array = arrayOf(filter.dateFrom != null, filter.dateTo != null, filter.searchIn.isNotEmpty())
            var count = 0
            array.forEach {
                if (it) {
                    count++
                }
            }
            if (count > 0) {
                binding.tvFilterCount.isVisible = true
                binding.tvFilterCount.text = count.toString()
            } else binding.tvFilterCount.isVisible = false
            currentQuery?.let { search(it) }
        })
        searchViewModel.loadFilter()
    }

    private fun initSorting() {
        searchViewModel.sorting.observe(viewLifecycleOwner, { parameter ->
            sortingParameter = parameter
        })
        searchViewModel.loadSorting()
        binding.btnSort.isSelected = sortSelected
        binding.btnSort.setOnClickListener {
            sortSelected = !sortSelected
            binding.btnSort.isSelected = sortSelected
            if (sortSelected) {
                sheetListener?.showBottomSheet(sortingParameter)
            } else {
                sheetListener?.hideBottomSheet()?.let { par ->
                    if (sortingParameter != par) {
                        sortingParameter = par
                        searchViewModel.setSortingParameter(par)
                        currentQuery?.let { search(it) }
                    }
                }
            }
        }
    }

    private fun initHistory() {
        binding.tvTitle.setText(R.string.search_history)
        val adapter = SearchHistoryAdapter {
            binding.textInputEditText.setText(it)
            search(it)
        }
        binding.list.adapter = adapter
        searchViewModel.history.observe(viewLifecycleOwner, {
            adapter.setList(it)
        })
        searchViewModel.loadHistory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sheetListener = context as BottomSheetListener
    }

    override fun onDetach() {
        super.onDetach()
        sheetListener = null
    }

    interface BottomSheetListener {
        fun showBottomSheet(parameter: Sorting.Parameter)
        fun hideBottomSheet(): Sorting.Parameter
    }

}