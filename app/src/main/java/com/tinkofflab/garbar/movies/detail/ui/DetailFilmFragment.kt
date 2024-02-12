package com.tinkofflab.garbar.movies.detail.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.imageLoader
import coil.request.ImageRequest
import coil.size.Scale
import com.tinkofflab.garbar.R
import com.tinkofflab.garbar.databinding.FragmentDetailFilmBinding
import com.tinkofflab.garbar.movies.detail.domain.DetailsScreenState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.Locale

class DetailFilmFragment: Fragment(R.layout.fragment_detail_film) {

    private val binding: FragmentDetailFilmBinding by viewBinding(FragmentDetailFilmBinding::bind)
    private val filmId: Int by lazy {
        arguments?.getInt(FILM_UID_KEY) ?: 0
    }
    private val isFromDb: Boolean by lazy {
        arguments?.getBoolean(FILM_FROM_DB_KEY) ?: false
    }
    private val viewModel: DetailsFilmViewModel by viewModel{
        parametersOf(filmId, isFromDb)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        viewModel.stateFilmsLD.observe(viewLifecycleOwner, this::renderFilm)
    }

    private fun renderFilm(state: DetailsScreenState) {
        loadingState(state.isLoading)
        if (state.error != null) {
            binding.error.setIcon(R.drawable.ic_error_connect)
            binding.error.setText(requireContext().getString(R.string.error_message))
            binding.error.isVisible = true
            binding.data.isVisible = false

        } else {
            binding.error.isVisible = false
            binding.data.isVisible = true
            if (state.film != null) {
                val request = ImageRequest.Builder(binding.poster.context)
                    .data(state.film.posterUrl)
                    .scale(Scale.FIT)
                    .memoryCacheKey(state.film.posterUrlPreview)
                    .diskCacheKey(state.film.posterUrlPreview)
                    .error(R.drawable.ic_error_connect)
                    .target(binding.poster)
                    .build()
                binding.poster.context.imageLoader.enqueue(request)
                binding.nameFilm.text =
                    if (Locale.getDefault().getLanguage()
                            .equals("ru") || state.film.nameEn == null
                    ) state.film.nameRu.trim() else state.film.nameEn
                binding.description.text = state.film.description

                binding.genre.text = state.film.genres.joinToString(separator = ", ") {it.genre}
                binding.country.text = state.film.countries.joinToString(separator = ", ") {it.country}
            }
        }
    }

    private fun loadingState(isLoading: Boolean) {
        binding.progressLoading.isVisible = isLoading
    }


    companion object {
        private const val FILM_UID_KEY = "film_uid"
        private const val FILM_FROM_DB_KEY = "from_db"
        fun newInstance(filmId: Int, isFromDb: Boolean): DetailFilmFragment {
            val args = Bundle().apply {
                putInt(FILM_UID_KEY, filmId)
                putBoolean(FILM_FROM_DB_KEY, isFromDb)
            }

            val fragment = DetailFilmFragment()
            fragment.arguments = args
            return fragment
        }
    }
}