package com.example.myinjections.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myinjections.R
import com.example.myinjections.network.model.UsefulLink
import kotlinx.android.synthetic.main.usefullinks_viewpager_item.view.*

class UsefulLinksViewPagerAdapter: ListAdapter<UsefulLink,
        UsefulLinksViewPagerAdapter.UsefulLinksViewHolder>(UsefulLinksComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsefulLinksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.usefullinks_viewpager_item,
            parent,
            false
        )

        return UsefulLinksViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsefulLinksViewHolder, position: Int) {
        val currentPage = getItem(position)
        holder.bind(currentPage.title, currentPage.subject)
    }

    class UsefulLinksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView = itemView.page_title_textview
        val subjectTextView: TextView = itemView.subject_textview

        fun bind(title: String, subject: String) {
            titleTextView.text = title.plus(" ")
            subjectTextView.text = subject.plus(" ")
        }
    }

    class UsefulLinksComparator(): DiffUtil.ItemCallback<UsefulLink>() {
        override fun areItemsTheSame(oldItem: UsefulLink, newItem: UsefulLink): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: UsefulLink, newItem: UsefulLink): Boolean {
            return oldItem.id == newItem.id
        }
    }
}