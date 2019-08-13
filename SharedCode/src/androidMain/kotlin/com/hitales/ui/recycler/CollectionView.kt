package com.hitales.ui.recycler

import android.graphics.Rect
import android.graphics.pdf.PdfDocument
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hitales.ui.Orientation
import com.hitales.ui.ScrollView
import com.hitales.ui.android.AndroidRecyclerView
import com.hitales.ui.android.scrollview.RecyclerViewLayoutManager
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

    actual var orientation: Orientation = Orientation.VERTICAL
        set(value) {
            field = value
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

        val weakRef = WeakReference(layout)

        return object : RecyclerViewLayoutManager(getWidget()){

            var lastPageLayoutInfo = CollectionViewLayout.PageLayoutInfo(weakRef)

            var currentPageLayoutInfo = CollectionViewLayout.PageLayoutInfo(weakRef)

            var nextPageLayoutInfo = CollectionViewLayout.PageLayoutInfo(weakRef)

            override fun getAttributesForPosition(position:Int): LayoutAttribute {
                val l = getCachedLayoutAttributes()
                val info = adapterHelper!!.getInfo(position)
                l.section = info.section
                l.row = info.row

                return l
            }

            open fun calculateShowFrame(){
                val scale = PixelUtil.getScale()
                if(orientation == Orientation.VERTICAL){
                    val startY = lastPageLayoutInfo.frame.y
                    showFrame.set(0f,startY,currentPageLayoutInfo.frame.width,nextPageLayoutInfo.frame.getBottom() - startY)
                    showFrame.scale(scale)
                }
            }

            override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
                detachAndScrapAttachedViews(recycler)
                visibleFrame.set(scrollX.toFloat(),scrollY.toFloat(),width.toFloat(),height.toFloat())
                showFrame.reset()
                resetPage(recycler,lastPageLayoutInfo)
                resetPage(recycler,currentPageLayoutInfo)
                resetPage(recycler,nextPageLayoutInfo)
                contextSize.reset()
                layout.prepareLayout()
                layout(recycler)
            }

            open fun layout(recycler: RecyclerView.Recycler){
                layout.getPageLayoutInfo(lastPageLayoutInfo,currentPageLayoutInfo,nextPageLayoutInfo)
                layoutPages(recycler)
            }

            open fun calculate(recycler: RecyclerView.Recycler,dx:Float,dy:Float){
                var cFrame = nextPageLayoutInfo.frame
                if(!cFrame.valid()){
                    val scale = PixelUtil.getScale()
                    if(orientation == Orientation.VERTICAL){
                        val startY = cFrame.y*scale
                        val endY = (cFrame.getBottom() - cFrame.height/2) *scale
                        val bottom = visibleFrame.getBottom()
                        if(bottom + dy > endY && !nextPageLayoutInfo.isEmpty()){
                            val temp = lastPageLayoutInfo
                            resetPage(recycler,lastPageLayoutInfo)
                            lastPageLayoutInfo = currentPageLayoutInfo
                            currentPageLayoutInfo = nextPageLayoutInfo
                            nextPageLayoutInfo = temp
                            layout(recycler)
                        }
                    }
                }
            }

            open fun resetPage(recycler: RecyclerView.Recycler,page:CollectionViewLayout.PageLayoutInfo){
                page.attributes.forEach {
                    removeAndRecycleView(it.view as View,recycler)
                }
                page.reset()
            }

            open fun layoutPages(recycler: RecyclerView.Recycler){
                layoutPage(recycler,lastPageLayoutInfo)
                layoutPage(recycler,currentPageLayoutInfo)
                layoutPage(recycler,nextPageLayoutInfo)
                val scale = PixelUtil.getScale()
                layout.getContentSize(contextSize)
                contextSize.set(nextPageLayoutInfo.frame.getRight(),nextPageLayoutInfo.frame.getBottom())
                contextSize.scale(scale)
                calculateShowFrame()
            }

            open fun layoutPage(recycler: RecyclerView.Recycler,pageInfo: CollectionViewLayout.PageLayoutInfo){
//                val scale = PixelUtil.getScale()
                if(!pageInfo.isLayout){
                    pageInfo.attributes.forEach {
                        val frame = it.frame
//                        frame.scale(scale)
                        val scrap = recycler.getViewForPosition(it.position)
                        val v = scrap.tag as CollectionViewCell
                        v.contentView.frame.set(frame)
                        v.contentView.onFrameChanged()
                        v.applyAttribute(it)

                        var lp = v.contentView.getWidget().layoutParams as ViewGroup.MarginLayoutParams
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
                pageInfo.isLayout = true
            }

            override fun cachedLayoutAttributes(layoutAttribute: LayoutAttribute) {
                layout.cacheAttribute(layoutAttribute)
            }

            override fun getCachedLayoutAttributes(): LayoutAttribute {
                return layout.getCacheAttribute()
            }


            override fun onScrollChange(recycler: RecyclerView.Recycler, dx: Int, dy: Int) {
                visibleFrame.set(scrollX.toFloat(),scrollY.toFloat(),width.toFloat(),height.toFloat())
                calculate(recycler,dx.toFloat(),dy.toFloat())
                layout.onScroll(scrollX.toFloat(),scrollY.toFloat())
                super.onScrollChange(recycler, dx, dy)
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