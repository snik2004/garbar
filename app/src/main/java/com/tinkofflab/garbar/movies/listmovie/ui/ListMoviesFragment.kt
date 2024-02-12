package com.tinkofflab.garbar.movies.listmovie.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tinkofflab.garbar.R
import com.tinkofflab.garbar.databinding.FragmentListFilmsBinding
import com.tinkofflab.garbar.movies.detail.ui.DetailFilmFragment
import com.tinkofflab.garbar.movies.listmovie.data.entity.Film
import com.tinkofflab.garbar.movies.listmovie.domain.ListScreenState
import com.tinkofflab.garbar.movies.listmovie.domain.ViewMode
import org.koin.android.ext.android.inject
import timber.log.Timber


class ListMoviesFragment : Fragment(R.layout.fragment_list_films),
    ListMoviesAdapter.FilmsInterface {

    private var viewMode: ViewMode = ViewMode.All
    private val binding: FragmentListFilmsBinding by viewBinding(FragmentListFilmsBinding::bind)
    private val viewModel: ListMoviesViewModel by inject()
    private val adapter: ListMoviesAdapter by lazy {
        ListMoviesAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        binding.recycler.adapter = adapter
        viewModel.listFilmsLD.observe(viewLifecycleOwner, this::renderListMovies)
    }

    private fun renderListMovies(state: ListScreenState) {
        loadingState(state.isLoading)
        if (state.error != null) {
            binding.error.setIcon(R.drawable.ic_error_connect)
            binding.error.setText(requireContext().getString(R.string.error_message))
            binding.error.isVisible = true
            binding.data.isVisible = false

        } else {
            binding.error.isVisible = false
            binding.data.isVisible = true
            adapter.updateList(if (state.viewMode == ViewMode.All) state.films else state.bookmarks)
        }
    }

    private fun loadingState(isLoading: Boolean) {
        binding.progressLoading.isVisible = isLoading
    }

    private fun initUi() {
        binding.error.setRetryListener { viewModel.getMovies() }
        binding.popular.setOnClickListener {
            binding.resultSearch.text = getString(R.string.popular)
            viewModel.returnToAllFilm()
            viewMode = ViewMode.All
        }
        binding.favourite.setOnClickListener {
            binding.resultSearch.text = getString(R.string.bookmarks)
            viewModel.getAllFilmsFromDb(ViewMode.Bookmarks)
            viewMode = ViewMode.Bookmarks
        }
    }

    override fun setBookmarksFilm(film: Film) {
        if (film.isFavourite) {
            viewModel.deleteBookmarksFilm(film)
        } else {
            viewModel.setBookmarksFilm(film)
        }
    }

    override fun openDetailMovie(film: Film) {
        val ifFromBookmarks = viewMode != ViewMode.All || film.isFavourite
        val fragment = DetailFilmFragment.newInstance(film.filmId, ifFromBookmarks)
        requireActivity().supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment, "DetailFilmFragment")
            addToBackStack(null)
        }
    }
}