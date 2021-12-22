package com.corbstech.guardian.articles.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.corbstech.guardian.articles.data.model.Article
import com.corbstech.guardian.articles.list.item.SectionItemView
import com.corbstech.guardian.common.list.AdapterListener
import com.corbstech.guardian.common.list.ListAdapter
import com.corbstech.guardian.common.list.RecyclerItemClicked
import com.corbstech.guardian.databinding.ArticleListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleListFragment : Fragment(), AdapterListener {

    private lateinit var binding: ArticleListFragmentBinding
    private val viewModel: ArticleListViewModel by viewModels()

    private val listAdapter by lazy {
        ListAdapter(
            listener = this,
            itemViewTypes = listOf(
                SectionItemView,
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ArticleListFragmentBinding.inflate(inflater, container, false)
        initSectionsRecyclerView()
        with(viewModel) {
            state.observe(viewLifecycleOwner) { state ->
                render(state)
            }
            syncData()
        }
        return binding.root
    }

    private fun render(state: ArticlesState) {
        binding.swipeRefreshLayout.isRefreshing = state.refreshing
        listAdapter.submitList(
            state.sections
        )
    }

    private fun initSectionsRecyclerView() {
        with(binding) {
            swipeRefreshLayout.setOnRefreshListener { viewModel.syncData() }
            sectionsRecyclerview.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = listAdapter
            }
        }
    }

    override fun clickListener(item: RecyclerItemClicked) {
        when (item) {
            is Article -> {
                item.url.let { url ->
                    findNavController().navigate(
                        ArticleListFragmentDirections
                            .actionArticleListFragmentToArticleDetailFragment(url)
                    )
                }
            }
        }
    }
}
