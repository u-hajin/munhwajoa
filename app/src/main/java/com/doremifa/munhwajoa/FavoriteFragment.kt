package com.doremifa.munhwajoa

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.doremifa.munhwajoa.database.Event
import com.doremifa.munhwajoa.database.EventViewModel
import com.doremifa.munhwajoa.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private var binding: FragmentFavoriteBinding? = null
    private var columnCount = 1
    private var favoriteList: ArrayList<Event> = arrayListOf()
    private lateinit var eventViewModel: EventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        val view = inflater.inflate(R.layout.fragment_favorite_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = FavoriteRecyclerViewAdapter(favoriteList)
            }
        }
        return view
    }

    private fun getFavorite() {
        eventViewModel = ViewModelProvider(
            this,
            EventViewModel.Factory(requireActivity().application)
        )[EventViewModel::class.java]

        eventViewModel.readFavorite().observe(viewLifecycleOwner) {
            for (event in it) {
                favoriteList.add(event)
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // event 처리
        super.onViewCreated(view, savedInstanceState)
        getFavorite()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}