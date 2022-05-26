package com.doremifa.munhwajoa

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doremifa.munhwajoa.database.Event
import com.doremifa.munhwajoa.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private var binding: FragmentFavoriteBinding? = null
    private var columnCount = 1
    private var favoriteList: ArrayList<Event> = arrayListOf()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // db에 있는 favorite 가져와서 favoriteList에 추가해주기

        // test
        var event = Event(
            "피아노7",
            "클래식",
            "송파구",
            "2022-05-22",
            "아트홀",
            "어린이",
            "5000원",
            "유유",
            "피아노",
            "url",
            "url",
            "2022-05-11"
        )

        favoriteList.add(event)
        favoriteList.add(event)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}