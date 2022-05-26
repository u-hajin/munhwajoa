package com.doremifa.munhwajoa

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import com.doremifa.munhwajoa.database.Event
import com.doremifa.munhwajoa.databinding.FragmentFavoriteBinding
import org.w3c.dom.Text

class FavoriteRecyclerViewAdapter(
    private val values: List<Event>
) : RecyclerView.Adapter<FavoriteRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentFavoriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.title.text = item.title
        holder.date.text = item.date
        holder.place.text = item.place
        holder.favorite.isChecked = item.favorite
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var title: TextView = binding.title
        var date: TextView = binding.date
        var place: TextView = binding.place
        var favorite: ToggleButton = binding.favoriteToggle
    }

}