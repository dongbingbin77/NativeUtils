package com.dongbingbin.nativeutils.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dongbingbin.nativeutils.R
import kotlinx.android.synthetic.main.fragment_pwd_layout.*

class PwdFragment : Fragment() {
    var temp_name: String? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_pwd_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        close_self.setOnClickListener {
            closeSelf()
        }
    }

    fun closeSelf(){
        if(activity is CloseSelf){
            (activity as CloseSelf).onCloseSelf()
        }
        activity?.onBackPressed()

    }
}