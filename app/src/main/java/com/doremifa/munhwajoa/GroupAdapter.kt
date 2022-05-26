package com.doremifa.munhwajoa

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doremifa.munhwajoa.database.Event
import com.doremifa.munhwajoa.databinding.GroupRowBinding

class GroupAdapter(val items: ArrayList<Event>) : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {

    private lateinit var context: Context

    interface OnItemClickListener {
        fun OnItemClick(data: Event)
    }

    var itemClickListener: OnItemClickListener? = null

    inner class ViewHolder(val binding: GroupRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                itemClickListener?.OnItemClick(items[adapterPosition])
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = GroupRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        context = parent.context

        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.binding.apply {
            title.text = items[position].title
            date.text = items[position].date
            place.text = items[position].place
            favoriteToggle.isChecked = items[position].favorite
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

}