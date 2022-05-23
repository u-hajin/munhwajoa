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
        val item = values[position] // item은 event 객체
        // holder.title.text = item.title // item(event)의 타이틀 textView에 띄워주기
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentGroupBinding) : RecyclerView.ViewHolder(binding.root) {
        // binding.~해서 fragment_group.xml에 있는 여러 imageView, textView 등등 event 정보 띄워줄 id 가져오기
        // val title: TextView = binding.title
    }

}