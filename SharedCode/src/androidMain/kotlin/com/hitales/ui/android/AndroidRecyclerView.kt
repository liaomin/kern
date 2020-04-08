package com.hitales.ui.android

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hitales.android.R
import com.hitales.ui.Platform
import com.hitales.ui.recycler.CollectionView

class AndroidRecyclerView : AndroidScrollView {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    companion object {
        fun createFromXLM(view: CollectionView):AndroidRecyclerView{
            val  scrollView = LayoutInflater.from(Platform.getApplication()).inflate(R.layout.recycler_view,null) as AndroidRecyclerView
            scrollView.mView = view
            return scrollView
        }
    }

     fun setup() {
        val collectionView:CollectionView = mView as CollectionView
        layoutManager = collectionView.getAndroidLayoutManager()
        adapter = object : RecyclerView.Adapter<com.hitales.ui.android.scrollview.ScrollView.ViewHolder>(){

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.hitales.ui.android.scrollview.ScrollView.ViewHolder {
                return collectionView.onCreateViewHolder(parent,viewType)
            }

            override fun getItemCount(): Int {
                return collectionView.getItemCount()
            }

            override fun getItemViewType(position: Int): Int {
                return collectionView.getItemViewType(position)
            }

            override fun onBindViewHolder(holder: com.hitales.ui.android.scrollview.ScrollView.ViewHolder, position: Int) {
                collectionView.onBindViewHolder(holder,position)
            }
        }
    }


    override fun onDataSetChanged() {
        this.adapter =  this.adapter
    }

}