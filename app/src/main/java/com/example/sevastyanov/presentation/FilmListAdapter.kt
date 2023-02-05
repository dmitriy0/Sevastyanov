package com.example.sevastyanov.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sevastyanov.R
import com.example.sevastyanov.domain.entities.FilmInList
import com.squareup.picasso.Picasso

class FilmListAdapter() : RecyclerView.Adapter<FilmListAdapter.ViewHolder>() {

    var onItemClick: ((filmInList: FilmInList) -> Unit)? = null
    var onItemLongClick: ((filmInList: FilmInList) -> Unit)? = null

    var topList = listOf<FilmInList>()
        set(value) {
            val callback = ListDiffCallback(topList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imageView)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvGenre: TextView = view.findViewById(R.id.tvGenre)
        val tvYear: TextView = view.findViewById(R.id.tvYear)
        val layout: CardView = view.findViewById(R.id.layout)
        val isFavourite: ImageView = view.findViewById(R.id.isFavourite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = topList[position]
        with(holder) {
            tvName.text = item.nameRu
            tvGenre.text = item.genres[0].genre
            tvYear.text = item.year.toString()
            Picasso.get().load(item.posterUrlPreview).into(image)
            isFavourite.visibility = if (item.isFavourite) View.VISIBLE else View.INVISIBLE
            layout.setOnClickListener {
                onItemClick?.invoke(item)
            }

            layout.setOnLongClickListener {
                onItemLongClick?.invoke(item)
                true
            }
        }
    }

    override fun getItemCount() = topList.size
}