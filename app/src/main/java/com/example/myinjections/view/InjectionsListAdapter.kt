package com.example.myinjections.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.myinjections.R
import com.example.myinjections.room.model.InjectionInfo

class InjectionsListAdapter: ListAdapter<InjectionInfo,
        InjectionsListAdapter.InjectionsListViewHolder>(InjectionsComparator()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InjectionsListAdapter.InjectionsListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.injection_recyclerview_item,
            parent,
            false)
        return InjectionListViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: InjectionsListAdapter.InjectionsListViewHolder,
        position: Int
    ) {
        TODO("Not yet implemented")
    }


}