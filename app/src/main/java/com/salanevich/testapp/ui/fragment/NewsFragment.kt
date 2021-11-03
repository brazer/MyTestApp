package com.salanevich.testapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.salanevich.testapp.R
import com.salanevich.testapp.databinding.FragmentNewsBinding
import com.salanevich.testapp.ui.activity.DetailsActivity
import com.salanevich.testapp.ui.adapter.ItemLoadStateAdapter
import com.salanevich.testapp.ui.adapter.NewsAdapter
import com.salanevich.testapp.vm.NewsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : Fragment() {

    private val newsViewModel: NewsViewModel by viewModel()
    private lateinit var adapter: NewsAdapter
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupViews()
        initData()

        return root
    }

    private fun setupViews() {
        val newsListView: RecyclerView = binding.listNews
        adapter = NewsAdapter({ url ->
            startActivity(DetailsActivity.createIntent(requireContext(), url))
        }, {})
        adapter.addLoadStateListener {
            binding.progressbar.isVisible = it.refresh is LoadState.Loading
            if (it.refresh is LoadState.Error) {
                Toast.makeText(requireContext(), R.string.message_something_went_wrong, Toast.LENGTH_LONG).show()
            }
        }
        newsListView.adapter = adapter.withLoadStateFooter(ItemLoadStateAdapter())
    }

    private fun initData() {
        lifecycleScope.launch {
            newsViewModel.fetchData().collectLatest { data ->
                adapter.submitData(data)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}