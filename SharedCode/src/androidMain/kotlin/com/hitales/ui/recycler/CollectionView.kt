package com.hitales.ui.recycler

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hitales.ui.Animation
import com.hitales.ui.LayoutParams
import com.hitales.ui.ScrollView
import com.hitales.ui.android.AndroidRecyclerView
import com.hitales.ui.android.scrollview.RecyclerViewLayoutManager
import com.hitales.ui.android.scrollview.addDifferent
import com.hitales.ui.utils.PixelUtil

actual open class CollectionView : ScrollView {

    companion object {
        const val HEADER_TYPE = -0xFFFFF0
        const val FOOTER_TYPE = -0xFFFFF1
        const val HEADER_INDEX = -1
        const val FOOTER_INDEX = -2
    }

    actual constructor(layoutParams: LayoutParams?,layout: CollectionViewLayout):super(layoutParams){
        this.layout = layout
        layout.collectionView = this
        getWidget().setup()
//        getWidget().recycledViewPool.setMaxRecycledViews(0,100)
    }

    var adapterHelper:AdapterHelper? = null

    actual var dataSource:CollectionViewDataSource? = null
        set(value) {
            field = value
            adapterHelper = AdapterHelper(this)
        }

    actual var layout: CollectionViewLayout
        set(value) {
            field = value
        }


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
                showAttributes.removeAll(showAttributes.filter {
                    val test = showFrame.intersect(it.frame)
                    if( !test && it.view != null){
                        removeAndRecycleView(it.view as View,recycler)
                        it.view = null
                    }
                    !test
                })

                val attributes = layout.getLayoutAttributesInFrame(showFrame)
                showAttributes.addDifferent(attributes){
                    val frame = it.frame
                    if(it.view == null){
                        val scrap = recycler.getViewForPosition(it.position)
                        val cell = scrap.tag as CollectionViewCell
                        val clp = cell.contentView.layoutParams
                        if(clp == null){
                            cell.contentView.layoutParams = LayoutParams(frame.width,frame.height)
                        }else{
                            clp.width = frame.width
                            clp.height = frame.height
                        }
                        cell.contentView.frame.set(frame)
                        cell.tag = scrap
                        cell.applyAttribute(it)

                        it.view = scrap

                        addView(scrap)

                        val exactlyWidth = PixelUtil.toPixelFromDIP(frame.width).toInt()
                        val exactlyHeight = PixelUtil.toPixelFromDIP(frame.height).toInt()
                        if(scrap.isLayoutRequested || scrap.width != exactlyWidth || scrap.height != exactlyHeight){
                            val w = android.view.View.MeasureSpec.makeMeasureSpec(exactlyWidth, android.view.View.MeasureSpec.EXACTLY)
                            val h = android.view.View.MeasureSpec.makeMeasureSpec(exactlyHeight, android.view.View.MeasureSpec.EXACTLY)
                            scrap.measure(w,h)
                        }

                        val itemWidth = getDecoratedMeasuredWidth(scrap)
                        val itemHeight = getDecoratedMeasuredHeight(scrap)
                        val l =  PixelUtil.toPixelFromDIP(frame.x).toInt() - scrollX
                        val t =  PixelUtil.toPixelFromDIP(frame.y).toInt() - scrollY

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
            return adapterHelper.getCount()
        }
        return 0
    }

    open fun getItemViewType(position: Int): Int {
        return adapterHelper?.getItemViewType(position)?:0
    }

    open fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.hitales.ui.android.scrollview.ScrollView.ViewHolder {
        var view:com.hitales.ui.recycler.CollectionViewCell? = null
        when (viewType){
            HEADER_TYPE -> view = dataSource!!.createHeaderView(this)
            FOOTER_TYPE -> view = dataSource!!.createFooterView(this)
            else -> {
                view = dataSource!!.createItemView(this,viewType)
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
            HEADER_TYPE -> dataSource!!.onBindHeaderView(this,info.section,holder.v!!)
            FOOTER_TYPE -> dataSource!!.onBindFooterView(this,info.section,holder.v!!)
            else -> {
                dataSource!!.onBindItemView(this,info.section,info.row,viewType,holder.v!!)
            }
        }
        holder.v.collectionView = this
    }

    fun onItemPress(section:Int,row:Int,cell:CollectionViewCell){
        val delegate = this.delegate
        if(delegate != null && delegate is CollectionViewDelegate){
            delegate.onItemPress(this,section,row,cell)
        }
    }

    fun onItemLongPress(section:Int,row:Int,cell:CollectionViewCell){
        val delegate = this.delegate
        if(delegate != null && delegate is CollectionViewDelegate){
            delegate.onItemLongPress(this,section,row,cell)
        }
    }

    override fun addSubView(view: com.hitales.ui.View, index: Int) {
        throw RuntimeException("unSupport")
    }

    actual fun getCell(section: Int, row: Int): CollectionViewCell? {
        return null
    }

    actual fun setLayoutWithAnimation(layout: CollectionViewLayout, animation: Animation) {
    }

}