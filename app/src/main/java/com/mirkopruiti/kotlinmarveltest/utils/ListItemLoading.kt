package com.mirkopruiti.kotlinmarveltest.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirkopruiti.kotlinmarveltest.R
import com.paginate.recycler.LoadingListItemCreator

class ListItemLoading : LoadingListItemCreator {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder? {
        val inflater = LayoutInflater.from(parent?.context)
        val view = inflater.inflate(R.layout.loading_list_item, parent, false)
        return object : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {}
    }
}
