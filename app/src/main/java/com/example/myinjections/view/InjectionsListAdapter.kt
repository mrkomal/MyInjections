package com.example.myinjections.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myinjections.R
import com.example.myinjections.room.model.InjectionInfo
import kotlinx.android.synthetic.main.injections_recyclerview_item.view.*

class InjectionsListAdapter: ListAdapter<InjectionInfo,
        InjectionsListAdapter.InjectionsListViewHolder>(InjectionsComparator()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InjectionsListAdapter.InjectionsListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.injections_recyclerview_item,
            parent,
            false)
        return InjectionsListViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: InjectionsListAdapter.InjectionsListViewHolder,
        position: Int
    ) {
        val currentInfo = getItem(position)
        holder.bind(currentInfo.id, currentInfo.name, currentInfo.date, currentInfo.dose, currentInfo.isObligatory)
    }

    class InjectionsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val injectionId: TextView = itemView.id_textView
        val injectionName: TextView = itemView.name_textView
        val injectionDate: TextView = itemView.year_textView
        val injectionDose: TextView = itemView.dose_textView4
        val isInjectionObligatory: TextView = itemView.obligatory_textview

        fun bind(id: Int?, name: String?, date: String?, dose: Double?, isObligatory: String?) {
            injectionId.text = id.toString()
            injectionName.text= name
            injectionDate.text = date
            injectionDose.text = dose.toString()

            val obligatoryText = "Obligatory"
            val optionalText = "Optional"

            if(isObligatory == "Yes"){
                isInjectionObligatory.text = obligatoryText
            } else {
                isInjectionObligatory.text = optionalText
            }
        }
    }

    class InjectionsComparator : DiffUtil.ItemCallback<InjectionInfo>() {

        override fun areItemsTheSame(oldItem: InjectionInfo, newItem: InjectionInfo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: InjectionInfo, newItem: InjectionInfo): Boolean {
            return oldItem.id == newItem.id
        }
    }

}