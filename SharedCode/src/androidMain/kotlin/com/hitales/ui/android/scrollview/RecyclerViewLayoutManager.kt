package com.hitales.ui.android.scrollview

import androidx.recyclerview.widget.RecyclerView
import com.hitales.ui.recycler.LayoutAttribute
import com.hitales.utils.Frame

inline fun <reified T> ArrayList<T>.addDifferent(o:List<T>,cb:(t:T)->Unit){
    o.forEach {
        if(!this.contains(it)){
            this.add(it)
            cb(it)
        }
    }
}

open abstract class RecyclerViewLayoutManager (scrollView: ScrollView) : BasicLayoutManager(scrollView) {

    /**
     * 显示界面大小
     */
    val showFrame:Frame = Frame.identity()

    /**
     * 显示的属性
     */
    val showAttributes = ArrayList<LayoutAttribute>()

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)
        showFrame.reset()
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        val result =  super.scrollHorizontallyBy(dx, recycler, state)
        onScrolled(recycler)
        return result
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        val result =  super.scrollVerticallyBy(dy, recycler, state)
        onScrolled(recycler)
        return result
    }

    protected open fun onScrolled(recycler: RecyclerView.Recycler){
    }

}