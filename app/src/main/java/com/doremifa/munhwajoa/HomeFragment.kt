package com.doremifa.munhwajoa

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doremifa.munhwajoa.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private var select: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonListener()
    }

    private fun buttonListener() {
        binding!!.apply {
            entire.setOnClickListener {
                select = entire.text.toString()
                val intent = Intent(activity, GroupActivity::class.java)
                intent.putExtra("codeName", select)
                startActivity(intent)
            }

            classic.setOnClickListener {
                select = classic.text.toString()
                val intent = Intent(activity, GroupActivity::class.java)
                intent.putExtra("codeName", select)
                startActivity(intent)
            }

            concert.setOnClickListener {
                select = concert.text.toString()
                val intent = Intent(activity, GroupActivity::class.java)
                intent.putExtra("codeName", select)
                startActivity(intent)
            }

            musical.setOnClickListener {
                select = musical.text.toString()
                val intent = Intent(activity, GroupActivity::class.java)
                intent.putExtra("codeName", select)
                startActivity(intent)
            }

            dance.setOnClickListener {
                select = dance.text.toString()
                val intent = Intent(activity, GroupActivity::class.java)
                intent.putExtra("codeName", select)
                startActivity(intent)
            }

            exhibition.setOnClickListener {
                select = exhibition.text.toString()
                val intent = Intent(activity, GroupActivity::class.java)
                intent.putExtra("codeName", select)
                startActivity(intent)
            }

            lecture.setOnClickListener {
                select = lecture.text.toString()
                val intent = Intent(activity, GroupActivity::class.java)
                intent.putExtra("codeName", select)
                startActivity(intent)
            }

            play.setOnClickListener {
                select = play.text.toString()
                val intent = Intent(activity, GroupActivity::class.java)
                intent.putExtra("codeName", select)
                startActivity(intent)
            }

            etc.setOnClickListener {
                select = etc.text.toString()
                val intent = Intent(activity, GroupActivity::class.java)
                intent.putExtra("codeName", select)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}