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
import com.doremifa.munhwajoa.databinding.FragmentGroupBinding

class GroupFragment : Fragment() {

    private var binding: FragmentGroupBinding? = null
    private var columnCount = 1
    private var eventList: ArrayList<Event> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupBinding.inflate(layoutInflater, container, false)
        val view = inflater.inflate(R.layout.fragment_group_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = GroupRecyclerViewAdapter(eventList)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 파일 읽어서 eventList에 추가해주기
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}