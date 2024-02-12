package com.tinkofflab.garbar.movies.listmovie.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.request.ImageRequest
import com.tinkofflab.garbar.R
import com.tinkofflab.garbar.common.MovieDiffCallback
import com.tinkofflab.garbar.databinding.ItemMovieBinding
import com.tinkofflab.garbar.movies.listmovie.data.entity.Film
import java.util.Locale

class ListMoviesAdapter(
    private val listener: FilmsInterface,
) :
    RecyclerView.Adapter<ListMoviesAdapter.MovieViewHolder>() {

    private var filmList: List<Film> = emptyList()

    interface FilmsInterface {
        fun setBookmarksFilm(film: Film)
        fun openDetailMovie(film: Film)
    }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemId(position: Int): Long {
        return filmList[position].hashCode().toLong()
    }

    fun updateList(
        newList: List<Film>,
    ) {
        val diffResult = DiffUtil.calculateDiff(
            MovieDiffCallback(filmList, newList), false
        )
        filmList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val place = filmList[position]
        holder.bind(place)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val bundle = payloads[0] as Bundle
            holder.bind(bundle, filmList[position])
            val place = filmList[position]
            holder.bind(place)
        }
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(film: Film) {
            binding.root.setOnClickListener { listener.openDetailMovie(film) }
            binding.root.setOnLongClickListener {
                listener.setBookmarksFilm(film)
                true
            }
            val request = ImageRequest.Builder(binding.miniPoster.context)
                .data(film.posterUrlPreview)
                .memoryCacheKey(film.posterUrlPreview)
                .diskCacheKey(film.posterUrlPreview)
                .error(R.drawable.ic_error_connect)
                .target(binding.miniPoster)
                .build()
            binding.miniPoster.context.imageLoader.enqueue(request)
            binding.nameFilm.text =
                if (Locale.getDefault().getLanguage()
                        .equals("ru") || film.nameEn == null
                ) film.nameRu.trim() else film.nameEn
            binding.genre.text = film.genres.first().genre
            binding.year.text = binding.root.context.getString(R.string.brackets, film.year)
            binding.favourite.isVisible = film.isFavourite
        }
        fun bind(bundle: Bundle, film: Film) {
            for (key in bundle.keySet()) {
                if (key == "isFavourite") {
                    binding.favourite.isVisible = film.isFavourite
                }
            }
            binding.root.setOnClickListener { listener.openDetailMovie(film) }
            binding.root.setOnLongClickListener {
                listener.setBookmarksFilm(film)
                true
            }
            val request = ImageRequest.Builder(binding.miniPoster.context)
                .data(film.posterUrlPreview)
                .memoryCacheKey(film.posterUrlPreview)
                .diskCacheKey(film.posterUrlPreview)
                .error(R.drawable.ic_error_connect)
                .target(binding.miniPoster)
                .build()
            binding.miniPoster.context.imageLoader.enqueue(request)
            binding.nameFilm.text =
                if (Locale.getDefault().getLanguage()
                        .equals("ru") || film.nameEn == null
                ) film.nameRu.trim() else film.nameEn
            binding.genre.text = film.genres.first().genre
            binding.year.text = binding.root.context.getString(R.string.brackets, film.year)

        }
    }

}
