package com.movies.movieappmaster

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movies.movieappmaster.views.MovieAdapter
import com.movies.movieappmaster.views.MoviesLoadStateAdapter
import com.movies.movieappmaster.viewmodel.SearchMoviesViewModel
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchMoviesActivity : AppCompatActivity()
{

    private var searchJob: Job? = null
    private lateinit var viewModel: SearchMoviesViewModel
    private val adapter = MovieAdapter()
    private lateinit var movie_reccyleview:RecyclerView;
    private lateinit var progressBar:ProgressBar;
    private lateinit var retryButton:Button;
    private lateinit var searchRepo:EditText;
    private lateinit var emptyList:TextView;


    private fun search(query: String)
    {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch(Dispatchers.IO) {
            viewModel.searchRepo(query).collectLatest {

                launch(Dispatchers.Main) {
                    adapter.submitData(it)

                }
                //System.out.println(it)
                 //adapter.submitData(UiModel(it))
            }

        }
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
       // binding = MainActivitySearch.inflate(layoutInflater)
       // val view = binding.root
      //  setContentView(view)
     setContentView(R.layout.activity_main)
        // get the view model
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(this))
            .get(SearchMoviesViewModel::class.java)
        movie_reccyleview=findViewById(R.id.list);
        progressBar=findViewById(R.id.progress_bar);
        retryButton=findViewById(R.id.retry_button);
        searchRepo=findViewById(R.id.search_repo);
        emptyList=findViewById(R.id.emptyList);
       val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
       movie_reccyleview.addItemDecoration(decoration)
        initAdapter()

        val manager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)

        movie_reccyleview.setLayoutManager( manager);
        movie_reccyleview.adapter=adapter;
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        search(query);
        initSearch(query)

       retryButton.setOnClickListener {
            adapter.retry()
        }
    }
    private fun initSearch(query: String)
    {
        searchRepo.setText(query)

        searchRepo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        searchRepo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(searchRepo.windowToken, 0)

                updateRepoListFromInput()


                true
            } else {
                false
            }
        }
    }

    private fun updateRepoListFromInput() {
       searchRepo.text.trim().let {
            if (it.isNotEmpty()) {
                search(it.toString())
            }
        }
    }

    private fun initAdapter()
    {
        movie_reccyleview.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MoviesLoadStateAdapter { adapter.retry() },
            footer = MoviesLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->

            // show empty list
            val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            showEmptyList(isListEmpty)

            // Only show the list if refresh succeeds.
           movie_reccyleview.isVisible = loadState.mediator?.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
           progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            retryButton.isVisible = loadState.mediator?.refresh is LoadState.Error
            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    this,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            emptyList.visibility = View.VISIBLE
           movie_reccyleview.visibility = View.GONE
        } else {
            emptyList.visibility = View.GONE
            movie_reccyleview.visibility = View.VISIBLE
        }
    }
    companion object
    {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Friends"

    }
}