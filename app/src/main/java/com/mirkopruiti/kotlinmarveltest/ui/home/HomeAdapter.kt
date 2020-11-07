package com.mirkopruiti.kotlinmarveltest.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mirkopruiti.kotlinmarveltest.R
import com.mirkopruiti.kotlinmarveltest.remote.model.MarvelCharacter
import com.mirkopruiti.kotlinmarveltest.ui.details.DetailsActivity
import com.mirkopruiti.kotlinmarveltest.utils.GlideApp
import java.util.*

class HomeAdapter : RecyclerView.Adapter<CharacterViewHolder>() {

    var recyclerView: RecyclerView? = null
    var characters: ArrayList<MarvelCharacter> = ArrayList()

    companion object {
        const val EXTRA_CHARACTER = "extra-character"
        const val EXTRA_CHARACTER_IMAGE_TRANSITION_NAME = "extra-character-transition-name"
        const val EXTRA_CHARACTER_NAME_TRANSITION_NAME = "extra-character-name-transition-name"
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(
            R.layout.list_item_home,
                parent, false)

        view.setOnClickListener {
            recyclerView?.let {
                val pos = it.getChildAdapterPosition(view)
                DetailsActivity.startActivity(it.context, characters[pos],
                        view.findViewById(R.id.characterImage),
                        view.findViewById(R.id.characterName))
            }
        }

        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.characterName?.text = characters[position].name

        ViewCompat.setTransitionName(holder.characterImage, characters[position].id.toString())
        ViewCompat.setTransitionName(holder.characterName, "${characters[position].id}-name")

        GlideApp.with(holder.itemView?.context)
                .load(characters[position].thumbnail.path + '.' + characters[position].thumbnail.extension)
                .transition(DrawableTransitionOptions.withCrossFade())
                .dontTransform()
                .error(R.drawable.placeholder)
                .into(holder.characterImage)
    }

    override fun getItemCount() = characters.size
}

class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val characterName: TextView = view.findViewById(R.id.characterName)
    val characterImage: ImageView = view.findViewById(R.id.characterImage)
}
