package com.mirkopruiti.kotlinmarveltest.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mirkopruiti.kotlinmarveltest.R
import com.mirkopruiti.kotlinmarveltest.remote.model.MarvelComics
import com.mirkopruiti.kotlinmarveltest.utils.GlideApp
import java.util.ArrayList

class DetailsComicsAdapter : RecyclerView.Adapter<ComicsViewHolder>() {

    var recyclerView: RecyclerView? = null
    var comics: ArrayList<MarvelComics> = ArrayList()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(
            R.layout.list_item_comics,
                parent, false)

        return ComicsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) {

        holder.comicsName?.text = comics[position].title

        holder.comicsPrice?.text = "USD ${comics[position].prices!![0].price}"

        ViewCompat.setTransitionName(holder.comicsImage, comics[position].id.toString())
        ViewCompat.setTransitionName(holder.comicsName, "${comics[position].id}-name")

        GlideApp.with(holder.itemView?.context)
                .load(comics[position].thumbnail!!.path + '.' + comics[position].thumbnail!!.extension)
                .transition(DrawableTransitionOptions.withCrossFade())
                .dontTransform()
                .error(R.drawable.placeholder)
                .into(holder.comicsImage)
    }

    override fun getItemCount() = comics.size
}

class ComicsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val comicsName: TextView = view.findViewById(R.id.comicsName)
    val comicsImage: ImageView = view.findViewById(R.id.comicsImage)
    val comicsPrice: TextView = view.findViewById(R.id.comicsPrice)
}