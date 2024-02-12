package com.tinkofflab.garbar.common

import androidx.recyclerview.widget.DiffUtil
import com.tinkofflab.garbar.movies.listmovie.data.entity.Film

class MovieDiffCallback(private val oldList: List<Film>, private val newList: List<Film>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].filmId == newList[newItemPosition].filmId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldFilm = oldList[oldItemPosition]
        val newFilm = newList[newItemPosition]

        if (oldFilm.isFavourite != newFilm.isFavourite) {
            return newFilm.isFavourite
        }
        return super.getChangePayload(oldItemPosition, newItemPosition)

    }
}

