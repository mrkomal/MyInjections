package com.example.myinjections.view.adapters

import android.content.ContentProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myinjections.R
import com.example.myinjections.network.model.UsefulLink
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.usefullinks_viewpager_item.view.*
import org.w3c.dom.Text

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
        holder.bind(currentPage.title, currentPage.subject, currentPage.image_url, currentPage.sample_text)

        // open website when item is clicked
        holder.itemView.setOnClickListener {
            val uri = Uri.parse(currentPage.link)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            holder.itemView.context.also {
                it.startActivity(intent)
            }
        }
    }

    class UsefulLinksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val titleTextView: TextView = itemView.page_title_textview
        private val subjectTextView: TextView = itemView.subject_textview
        private val imageView: ImageView = itemView.post_image
        private val sampleTextTextView: TextView = itemView.sample_text

        fun bind(title: String, subject: String, imageUrl: String, sampleText: String) {
            titleTextView.text = title.plus(" ")
            subjectTextView.text = subject.plus(" ")
            Picasso.get()
                .load(imageUrl)
                .error(R.drawable.sharp_error_black_36dp)
                .fit()
                .into(imageView)
            sampleTextTextView.text = sampleText
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