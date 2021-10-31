package com.android.androidjavakotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class RemarksDetailedFragmentKotlin : Fragment() {

    companion object {
        var ARG_REMARK = "remark"
    }

    private lateinit var remark: Remark

    fun RemarksDetailedFragmentKotlin() {}

    fun newInstance(remark: Remark?): RemarksDetailedFragmentKotlin {
        val fragment = com.android.androidjavakotlin.RemarksDetailedFragmentKotlin()
        val args = Bundle()
        args.putParcelable(ARG_REMARK, remark)
        fragment.setArguments(args)
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getArguments() != null) {
            remark = getArguments().getParcelable(ARG_REMARK)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_remarks_detailed, container, false)
        val remarkDescription = view.findViewById<TextView>(R.id.remarkDescription)
        remarkDescription.text = remark.getDescription()
        val remarkName = view.findViewById<TextView>(R.id.remarkName)
        remarkName.text = remark.getName()
        val remarkDate = view.findViewById<TextView>(R.id.remarkDate)
        remarkDate.text = remark.getDate()
        return view
    }
}
