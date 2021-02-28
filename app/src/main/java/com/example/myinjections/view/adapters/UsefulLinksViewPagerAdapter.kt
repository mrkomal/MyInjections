package com.example.myinjections.view.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myinjections.network.model.UsefulLink

class UsefulLinksViewPagerAdapter: ListAdapter<UsefulLink,
        UsefulLinksViewPagerAdapter.UsefulLinksViewHolder>(UsefulLinksComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsefulLinksViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: UsefulLinksViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class UsefulLinksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}

    class UsefulLinksComparator(): DiffUtil.ItemCallback<UsefulLink>() {
        override fun areItemsTheSame(oldItem: UsefulLink, newItem: UsefulLink): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: UsefulLink, newItem: UsefulLink): Boolean {
            TODO("Not yet implemented")
        }
    }
}