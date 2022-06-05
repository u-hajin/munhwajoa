package com.doremifa.munhwajoa

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import com.bumptech.glide.Glide
import com.doremifa.munhwajoa.database.Event
import com.doremifa.munhwajoa.databinding.FragmentFavoriteBinding

class FavoriteRecyclerViewAdapter(
    private val values: List<Event>
) : RecyclerView.Adapter<FavoriteRecyclerViewAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

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
        Glide.with(context)
            .load(item.image)
            .override(400, 400)
            .fitCenter()
            .into(holder.image)
        holder.title.text = item.title
        holder.date.text = item.date
        holder.place.text = item.place
        holder.favorite.isChecked = item.favorite

    }

    override fun getItemCount(): Int = values.size

    interface OnItemClickListener {
        fun OnItemClick(data: Event)
        fun favoriteToggleClick(data: Event, position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    inner class ViewHolder(binding: FragmentFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var image: ImageView = binding.eventImage
        var title: TextView = binding.title
        var date: TextView = binding.date
        var place: TextView = binding.place
        var favorite: ToggleButton = binding.favoriteToggle

        init {
            binding.favorite.setOnClickListener {
                itemClickListener?.OnItemClick(values[adapterPosition])
            }
            binding.favoriteToggle.setOnClickListener {
                itemClickListener?.favoriteToggleClick(values[adapterPosition], adapterPosition)
            }
        }
    }

}