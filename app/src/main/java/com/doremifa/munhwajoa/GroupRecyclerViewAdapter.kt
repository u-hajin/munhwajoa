package com.doremifa.munhwajoa

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.doremifa.munhwajoa.database.Event
import com.doremifa.munhwajoa.databinding.FragmentGroupBinding

class GroupRecyclerViewAdapter(
    private val values: List<Event>
) : RecyclerView.Adapter<GroupRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentGroupBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentGroupBinding) : RecyclerView.ViewHolder(binding.root) {

        override fun toString(): String {
            return super.toString()
        }
    }

}