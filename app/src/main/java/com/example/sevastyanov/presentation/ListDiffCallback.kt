package com.example.sevastyanov.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.sevastyanov.domain.entities.FilmInList

class ListDiffCallback(
    private val oldList: List<FilmInList>,
    private val newList: List<FilmInList>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.filmId == newItem.filmId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

}