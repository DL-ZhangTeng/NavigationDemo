package com.example.navigationdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationdemo.adapter.Blank4Adapter
import com.example.navigationdemo.bean.Blank4Bean
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.zhangteng.base.base.BaseListFragment

class Blank4Fragment : BaseListFragment<Blank4Bean, Blank4Adapter>() {

    companion object {
        fun newInstance() = Blank4Fragment()
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
        return inflater.inflate(R.layout.fragment_blank4, container, false)
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        super.initView(view, savedInstanceState)
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        refreshData(true)
    }

    override fun createAdapter(): Blank4Adapter {
        return Blank4Adapter(mList as MutableList<Blank4Bean?>)
    }

    override fun getRecyclerView(): RecyclerView? {
        return view?.findViewById(R.id.recyclerView)
    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout? {
        return view?.findViewById(R.id.smartRefreshLayout)
    }

    override fun loadData(i: Int) {
        val data = ArrayList<Blank4Bean>().apply {
            add(Blank4Bean())
            add(Blank4Bean())
            add(Blank4Bean())
            add(Blank4Bean())
            add(Blank4Bean())
            add(Blank4Bean())
            add(Blank4Bean())
            add(Blank4Bean())
            add(Blank4Bean())
            add(Blank4Bean())
            add(Blank4Bean())
            add(Blank4Bean())
            add(Blank4Bean())
            add(Blank4Bean())
            add(Blank4Bean())
            add(Blank4Bean())
            add(Blank4Bean())
            add(Blank4Bean())
            add(Blank4Bean())
            add(Blank4Bean())
            add(Blank4Bean())
        }
        showDataSuccess(data.size, data)
    }

    override fun setLayoutManager() {
        setLinearLayoutManager(LinearLayoutManager.VERTICAL)
    }
}