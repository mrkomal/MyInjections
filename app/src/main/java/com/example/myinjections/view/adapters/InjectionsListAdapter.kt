package com.example.myinjections.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myinjections.R
import com.example.myinjections.room.model.InjectionInfo
import kotlinx.android.synthetic.main.injections_recycler_view_item.view.*


class InjectionsListAdapter: ListAdapter<InjectionInfo,
        InjectionsListAdapter.InjectionsListViewHolder>(
    InjectionsComparator()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InjectionsListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.injections_recycler_view_item,
            parent,
            false)
        return InjectionsListViewHolder(
            view
        )
    }

    override fun onBindViewHolder(
        holder: InjectionsListViewHolder,
        position: Int
    ) {
        val currentInfo = getItem(position)
        holder.bind( (position + 1), currentInfo.name, currentInfo.date, currentInfo.dose,
            currentInfo.isObligatory, currentInfo.illnessInformation)
    }

    class InjectionsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val injectionId: TextView = itemView.id_textView
        val injectionName: TextView = itemView.name_textView
        val injectionDate: TextView = itemView.year_textView
        val injectionDose: TextView = itemView.dose_textView
        val isInjectionObligatory: TextView = itemView.obligatory_textview
        val illnessInformation: TextView = itemView.illness_textview

        fun bind(id: Int?, name: String?, date: Int?, dose: Double?, isObligatory: Boolean?,
                    illness: String?) {

            //italic font that is used in application doesn't fit in TextViews (last letter is cut)
            //to avoid that a space has to be added after each word that will be displayed
            val space = " "

            injectionId.text = id.toString().plus(space)
            injectionName.text= name.plus(space)
            injectionDate.text = date.toString().plus(space)
            injectionDose.text = dose.toString().plus(" ml").plus(space)
            illnessInformation.text = illness.toString().plus(space)

            val obligatoryText = "Obligatory"
            val optionalText = "Optional"

            if(isObligatory == true){
                isInjectionObligatory.text = obligatoryText.plus(space)
            } else {
                isInjectionObligatory.text = optionalText.plus(space)
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