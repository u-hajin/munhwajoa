package com.doremifa.munhwajoa

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doremifa.munhwajoa.database.Event
import com.doremifa.munhwajoa.databinding.GroupRowBinding

class GroupAdapter(
    val items: ArrayList<Event>
) : RecyclerView.Adapter<GroupAdapter.ViewHolder>(), Filterable {

    private lateinit var context: Context

    var filteredEvent = ArrayList<Event>()
    var itemFilter = ItemFilter()

    init {
        filteredEvent.addAll(items)
    }

    override fun getFilter(): Filter {
        return itemFilter
    }


    inner class ItemFilter : Filter() {

        //입력받은 문자열 처리
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterString = charSequence.toString()
            val results = FilterResults()

            //검색이 필요없을 경우를 위해 원본 배열을 복제
            var filteredList: ArrayList<Event> = ArrayList()

            //공백제외 아무런 값이 없을 경우 -> 원본 배열
            if (filterString.trim { it <= ' ' }.isEmpty()) {
                results.values = items
                results.count = items.size
                return results

            }else{
                filteredList.clear()
                for (item in items) {
                    if (item.title.contains(filterString)) {
                        filteredList.add(item)
                    }
                }
                if(filteredList.isEmpty()) {
                    results.values = filteredList
                    results.count = 0
                    return results
                }
            }
            results.values = filteredList
            results.count = filteredList.size
            return results
        }

        //처리 결과
        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
            filteredEvent.clear()
            filteredEvent.addAll(filterResults.values as ArrayList<Event>)
            notifyDataSetChanged()
        }
    }

    interface OnItemClickListener {
        fun OnItemClick(data: Event)
        fun favoriteToggleClick(data: Event)
    }

    var itemClickListener: OnItemClickListener? = null

    inner class ViewHolder(val binding: GroupRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                itemClickListener?.OnItemClick(filteredEvent[adapterPosition])
            }

            binding.favoriteToggle.setOnClickListener {
                itemClickListener?.favoriteToggleClick(filteredEvent[adapterPosition])
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
        if(filteredEvent.isNotEmpty()) {
            holder.binding.apply {
                title.text = filteredEvent[position].title
                date.text = filteredEvent[position].date
                place.text = filteredEvent[position].place
                Glide.with(eventImage)
                    .load(filteredEvent[position].image)
                    .override(400, 400)
                    .fitCenter()
                    .into(eventImage)

                favoriteToggle.isChecked = filteredEvent[position].favorite
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun notifyChange() {
        filteredEvent.clear()
        filteredEvent.addAll(items)
        this.notifyDataSetChanged()
    }

}