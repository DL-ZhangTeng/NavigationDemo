package com.example.navigationdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.zhangteng.base.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_blank1.*

class Blank1Fragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = Blank1Fragment()
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
        return inflater.inflate(R.layout.fragment_blank1, container, false)
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        textview1.setOnClickListener {
            //点击跳转page2
            Navigation.findNavController(it).navigate(R.id.action_blank1Fragment_to_blank2Fragment)
        }
    }
}