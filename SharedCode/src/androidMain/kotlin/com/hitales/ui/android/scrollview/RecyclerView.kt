package com.hitales.ui.android.scrollview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hitales.ui.Platform
import com.hitales.utils.Size


open class RecyclerView {

    interface SectionListAdapter {
        fun getSectionCount():Int
        fun getItemCount(sectionIndex:Int):Int
        fun getItemType(sectionIndex:Int,index:Int):Int
        fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrollView.ViewHolder
        fun onBindViewHolder(holder: ScrollView.ViewHolder, position: Int)
        fun getItemSize(size:Size,sectionIndex:Int,index:Int)
    }

    val mRecyclerView = object : ScrollView(){
        override fun setup() {
            this@RecyclerView.setup()
        }

        override fun onDataSetChanged() {
            this@RecyclerView.onDataSetChanged()
        }
    }

    val adapter:SectionListAdapter? = null

    var style
        get() = mRecyclerView.style
        set(value) {mRecyclerView.style = value}


    protected fun setup(){
        mRecyclerView.layoutManager = RecyclerViewViewLayoutManager(mRecyclerView)
        mRecyclerView.adapter = object : RecyclerView.Adapter<ScrollView.ViewHolder>(){

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrollView.ViewHolder {
                return ScrollView.ViewHolder(View(Platform.getApplication()))
            }

            override fun getItemCount(): Int {
                return adapter?.getItemCount(0)?:0
            }

            override fun getItemViewType(position: Int): Int {
                return position
            }

            override fun onBindViewHolder(holder: ScrollView.ViewHolder, position: Int) {
            }
        }
    }


    protected fun onDataSetChanged(){
        mRecyclerView.adapter =  mRecyclerView.adapter
    }


}