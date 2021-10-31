package com.android.androidjavakotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class About : Fragment() {
    fun About() {
        // Required empty public constructor
    }

    fun newInstance(): About? {
        val fragment = About()
        val args = Bundle()
        fragment.setArguments(args)
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                     savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }
}