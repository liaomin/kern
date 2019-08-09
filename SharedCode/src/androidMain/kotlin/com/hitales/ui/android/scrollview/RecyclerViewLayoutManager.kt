package com.hitales.ui.android.scrollview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hitales.ui.Orientation
import com.hitales.ui.recycler.CollectionViewLayout
import com.hitales.ui.recycler.LayoutAttribute
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.Frame

inline fun <reified T> ArrayList<T>.addDifferent(o:ArrayList<T>){
    o.forEach {
        if(!this.contains(it)){
            this.add(it)
        }
    }
}

open abstract class RecyclerViewLayoutManager (recyclerView: RecyclerView) : BasicLayoutManager(recyclerView) {

    var orientation = Orientation.VERTICAL

    val visibleFrame:Frame = Frame.zero()

    //预加载界面大小 >= visibleFrame
    val showFrame:Frame = Frame.zero()

    var showAttributes:ArrayList<LayoutAttribute> =  ArrayList()


    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)
        val scale = 1f / PixelUtil.getScale()
        visibleFrame.set(scrollX.toFloat(),scrollY.toFloat(),width.toFloat(),height.toFloat())
        visibleFrame.scale(scale)
        showFrame.reset()
        layoutChildren(recycler)
    }

    open fun layoutChildren(recycler: RecyclerView.Recycler){
        val removeList = ArrayList<LayoutAttribute>()
        showAttributes.forEach {
            if(it.view != null && !visibleFrame.intersect(it.frame)){
                removeAndRecycleView(it.view as View,recycler)
                cachedLayoutAttributes(it)
                removeList.add(it)
            }
        }
        showAttributes.removeAll(removeList)
        var diffFrame = showFrame
        if(showFrame.valid()){
            diffFrame = visibleFrame
        }
//        val result = getAttributesForFrame(showFrame.clone(),diffFrame)
//        showFrame.set(result.minX,result.minY,result.maxX - result.minX,result.maxY - result.minY)
//        showAttributes.addAll(result.attributes)
//        val pl = paddingLeft
//        val pr = paddingRight
//        val pt = paddingTop
//        val pb = paddingBottom
//        val scale =  PixelUtil.getScale()
//        result.attributes.forEach {
//            val frame = it.frame
//            frame.scale(scale)
//            val scrap = recycler.getViewForPosition(it.position)
//            addView(scrap)
//
//            val exactlyWidth = frame.width.toInt()
//            val exactlyHeight = frame.height.toInt()
//            if(scrap.isLayoutRequested || scrap.width != exactlyWidth || scrap.height != exactlyHeight){
//                val w = android.view.View.MeasureSpec.makeMeasureSpec(exactlyWidth, android.view.View.MeasureSpec.EXACTLY)
//                val h = android.view.View.MeasureSpec.makeMeasureSpec(exactlyHeight, android.view.View.MeasureSpec.EXACTLY)
//                scrap.measure(w,h)
//            }
//
//            val itemWidth = getDecoratedMeasuredWidth(scrap)
//            val itemHeight = getDecoratedMeasuredHeight(scrap)
//            val l = pl + frame.x.toInt()
//            val t = pt+ frame.y.toInt()
//
//            layoutDecorated(scrap, l , t , l+itemWidth, t+itemHeight)
//        }
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
        visibleFrame.set(scrollX.toFloat(),scrollY.toFloat(),width.toFloat(),height.toFloat())
        if(tempArray[0] != 0){
            layoutChildren(recycler)
        }
    }

    abstract fun getAttributesForPosition(position:Int):LayoutAttribute

    abstract fun cachedLayoutAttributes(layoutAttribute: LayoutAttribute)

    abstract fun getCachedLayoutAttributes():LayoutAttribute

}