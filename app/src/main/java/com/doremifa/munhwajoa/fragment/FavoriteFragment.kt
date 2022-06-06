package com.doremifa.munhwajoa.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.doremifa.munhwajoa.activity.DetailActivity
import com.doremifa.munhwajoa.R
import com.doremifa.munhwajoa.adapter.FavoriteRecyclerViewAdapter
import com.doremifa.munhwajoa.database.Event
import com.doremifa.munhwajoa.database.EventViewModel
import com.doremifa.munhwajoa.databinding.FragmentFavoriteBinding
import kotlinx.coroutines.*

class FavoriteFragment : Fragment() {

    private var binding: FragmentFavoriteBinding? = null
    private var columnCount = 1
    private var favoriteList = ArrayList<Event>()
    private lateinit var eventViewModel: EventViewModel
    private lateinit var updateAdapter: RecyclerView.Adapter<*>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        val view = inflater.inflate(R.layout.fragment_favorite_list, container, false)
        eventViewModel = ViewModelProvider(
            this,
            EventViewModel.Factory(requireActivity().application)
        )[EventViewModel::class.java]

        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                MainScope().launch {
                    withContext(Dispatchers.IO) {
                        var favorite = eventViewModel.readFavorite()

                        for (event in favorite) {
                            favoriteList.add(event)
                        }
                    }
                    adapter = FavoriteRecyclerViewAdapter(favoriteList)

                    (adapter as FavoriteRecyclerViewAdapter).itemClickListener =
                        object : FavoriteRecyclerViewAdapter.OnItemClickListener {
                            override fun OnItemClick(data: Event) {
                                val intent = Intent(context, DetailActivity::class.java)
                                intent.putExtra("event", data)
                                startActivity(intent)
                            }

                            override fun favoriteToggleClick(data: Event, position: Int) {
                                data.favorite = false
                                eventViewModel.deleteFavorite(data)
                                favoriteList.removeAt(position)
                                updateAdapter.notifyItemRemoved(position)
                            }
                        }
                    updateAdapter = adapter as RecyclerView.Adapter<*>
                }
            }
        }
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onResume() {
        super.onResume()
        updateFavoriteList()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateFavoriteList() {
        MainScope().launch {
            withContext(Dispatchers.IO) {
                var favorite = eventViewModel.readFavorite()
                favoriteList.clear()
                for (event in favorite) {
                    favoriteList.add(event)
                }
            }
            updateAdapter.notifyDataSetChanged()
        }
    }

}