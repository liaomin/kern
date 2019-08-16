package com.hitales.ui.recycler

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hitales.ui.Orientation
import com.hitales.ui.ScrollView
import com.hitales.ui.android.AndroidRecyclerView
import com.hitales.ui.android.scrollview.RecyclerViewLayoutManager
import com.hitales.ui.android.scrollview.addDifferent
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.Frame
import com.hitales.utils.WeakReference
import java.lang.RuntimeException

actual open class CollectionView : ScrollView {

    companion object {
        val HEADER_TYPE = -0xFFFFF0
        val FOOTER_TYPE = -0xFFFFF1
        val HEADER_INDEX = -1
        val FOOTER_INDEX = -2
    }

    actual constructor(frame: Frame,layout: CollectionViewLayout):super(frame){
        this.layout = layout
        layout.collectionViewRef = WeakReference(this)
        getWidget().setup1()
        getWidget().recycledViewPool.setMaxRecycledViews(0,100)
    }

    var adapterHelper:AdapterHelper? = null

    actual var adapter: CollectionViewAdapter? = null
        set(value) {
            field = value
            if(value != null){
                adapterHelper = AdapterHelper(value,this)
            }else{
                adapterHelper = null
            }
        }

    actual val layout: CollectionViewLayout


    override fun onOrientationChanged() {
        reloadData()
    }

    actual fun reloadData() {
        getWidget().onDataSetChanged()
    }

    override fun createWidget(): View {
        return AndroidRecyclerView.createFromXLM(this)
    }

    override fun getWidget(): AndroidRecyclerView {
        return super.getWidget() as AndroidRecyclerView
    }

    open fun getAndroidLayoutManager():RecyclerViewLayoutManager{

        return object : RecyclerViewLayoutManager(getWidget()){

            override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
                detachAndScrapAttachedViews(recycler)
                showAttributes.forEach {
                    it.view = null
                }
                showAttributes.clear()
                layout.prepareLayout()
                update(recycler)
            }

            fun update(recycler: RecyclerView.Recycler){
                showFrame.set(this@CollectionView.scrollX,this@CollectionView.scrollY,frame.width,frame.height)
                val attributes = layout.getLayoutAttributesInFrame(showFrame)
                showAttributes.addDifferent(attributes)
                showAttributes.removeAll(showAttributes.filter {
                    val test = showFrame.intersect(it.frame)
                    if( !test && it.view != null){
                        removeAndRecycleView(it.view as View,recycler)
                        it.view = null
                    }
                    !test
                })

                showAttributes.forEach {
                    val frame = it.frame
                    if(it.view == null){
                        val scrap = recycler.getViewForPosition(it.position)
                        val cell = scrap.tag as CollectionViewCell
                        cell.contentView.frame.set(frame)
                        cell.contentView.onFrameChanged()
                        cell.tag = scrap
                        cell.applyAttribute(it)

                        var lp = cell.contentView.getWidget().layoutParams as ViewGroup.MarginLayoutParams
                        it.view = scrap

                        addView(scrap)

                        val exactlyWidth = lp.width
                        val exactlyHeight = lp.height
                        if(scrap.isLayoutRequested || scrap.width != exactlyWidth || scrap.height != exactlyHeight){
                            val w = android.view.View.MeasureSpec.makeMeasureSpec(exactlyWidth, android.view.View.MeasureSpec.EXACTLY)
                            val h = android.view.View.MeasureSpec.makeMeasureSpec(exactlyHeight, android.view.View.MeasureSpec.EXACTLY)
                            scrap.measure(w,h)
                        }

                        val itemWidth = getDecoratedMeasuredWidth(scrap)
                        val itemHeight = getDecoratedMeasuredHeight(scrap)
                        val l = lp.leftMargin - scrollX
                        val t = lp.topMargin - scrollY

                        layoutDecorated(scrap, l , t , l+itemWidth, t+itemHeight)
                    }
                }
                layout.getContentSize(contextSize)
                contextSize.scale(PixelUtil.getScale())
            }

            override fun onScrollChange(recycler: RecyclerView.Recycler, dx: Int, dy: Int) {
//                calculate(recycler,dx.toFloat(),dy.toFloat())
                layout.onScroll(this@CollectionView.scrollX,this@CollectionView.scrollY)
                update(recycler)
            }
        }
    }

    open fun getItemCount(): Int {
        val adapterHelper = this.adapterHelper
        if(adapterHelper != null){
            return adapterHelper.itemCount
        }
        return 0
    }

    open fun getItemViewType(position: Int): Int {
        return adapterHelper?.getItemViewType(position)?:0
    }

    open fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.hitales.ui.android.scrollview.ScrollView.ViewHolder {
        var view:com.hitales.ui.recycler.CollectionViewCell? = null
        when (viewType){
            HEADER_TYPE -> view = adapter!!.createHeaderView(this)
            FOOTER_TYPE -> view = adapter!!.createFooterView(this)
            else -> {
                view = adapter!!.createItemView(this,viewType)
            }
        }
        if(view == null){
            throw RuntimeException("view is null for type:$viewType")
        }
        return com.hitales.ui.android.scrollview.ScrollView.ViewHolder(view.contentView.getWidget(),view)
    }


    open fun onBindViewHolder(holder: com.hitales.ui.android.scrollview.ScrollView.ViewHolder, position: Int) {
        val info  = adapterHelper!!.positionInfoArray[position]
        val viewType = adapterHelper!!.getItemViewType(position)
        holder.view.tag = holder.v
        when (viewType){
            HEADER_TYPE -> adapter!!.onBindHeaderView(this,info.section,holder.v!!)
            FOOTER_TYPE -> adapter!!.onBindFooterView(this,info.section,holder.v!!)
            else -> {
                adapter!!.onBindItemView(this,info.section,info.row,viewType,holder.v!!)
            }
        }
    }

}