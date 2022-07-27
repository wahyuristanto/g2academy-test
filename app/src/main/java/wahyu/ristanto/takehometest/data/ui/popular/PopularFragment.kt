package wahyu.ristanto.takehometest.data.ui.popular

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import wahyu.ristanto.takehometest.R
import wahyu.ristanto.takehometest.data.model.ArticleItem
import wahyu.ristanto.takehometest.databinding.FragmentPopularBinding

@AndroidEntryPoint
class PopularFragment : Fragment(R.layout.fragment_popular), PopularAdapter.OnItemClickListener {
    private val viewModel: PopularViewModel by viewModels()
    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPopularBinding.bind(view)
        val adapter = PopularAdapter(this)
        binding.apply {
            rvNews.setHasFixedSize(true)
            rvNews.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PopularLoadStateAdapter { adapter.retry() },
                footer = PopularLoadStateAdapter { adapter.retry() }
            )
            btnTryAgain.setOnClickListener {
                adapter.retry()
            }
        }
        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                rvNews.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnTryAgain.isVisible = loadState.source.refresh is LoadState.Error
                tvFailed.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    rvNews.isVisible = false
                    tvNotFound.isVisible = true
                } else {
                    tvNotFound.isVisible = false
                }
            }
        }
        viewModel.articles.observe(viewLifecycleOwner, {
            adapter.submitData(lifecycle, it)
        })

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    binding.rvNews.scrollToPosition(0)
                    viewModel.searchBerita(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    binding.rvNews.scrollToPosition(0)
                    viewModel.searchBerita(query)
                }
                return true
            }

        })
    }

    override fun onItemClick(itm: ArticleItem) {

    }
}