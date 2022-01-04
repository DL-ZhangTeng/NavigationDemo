package com.example.navigationdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.zhangteng.base.base.BaseFragment
import com.example.navigationdemo.R
import kotlinx.android.synthetic.main.fragment_blank1.*
import kotlinx.android.synthetic.main.fragment_blank1.textview1
import kotlinx.android.synthetic.main.fragment_blank2.*

class Blank2Fragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = Blank2Fragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blank2, container, false)
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        textview2.setOnClickListener {
            //点击跳转page2
            Navigation.findNavController(it).navigate(R.id.action_blank2Fragment_to_blank3Fragment)
        }
    }
}