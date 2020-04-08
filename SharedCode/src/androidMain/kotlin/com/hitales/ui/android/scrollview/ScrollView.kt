package com.hitales.ui.android.scrollview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hitales.ui.Colors
import com.hitales.ui.Platform
import com.hitales.ui.android.AndroidLayout
import com.hitales.utils.Size

open class ScrollView : RecyclerView {

    open var orientation: com.hitales.ui.Orientation = com.hitales.ui.Orientation.VERTICAL
        set(value) {
            val layout = flexLayout!!
            if(field != value){
                if(value == com.hitales.ui.Orientation.VERTICAL){
                    layout.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)
                }else{
                    layout.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT)
                }
            }
            field = value
        }

    var scrollEnabled = true

    protected var flag = 0

    protected var flexLayout: AndroidLayout? = null

    open class ViewHolder(val view: View,val v:com.hitales.ui.recycler.CollectionViewCell? = null) : RecyclerView.ViewHolder(view) {

    }

    constructor() : super(Platform.getApplication()) {
        scrollBarStyle = SCROLLBARS_OUTSIDE_OVERLAY
        clipToPadding = false
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        scrollBarStyle = SCROLLBARS_OUTSIDE_OVERLAY
        clipToPadding = false
    }

    companion object {
        internal const val FLAG_LAYOUT_MASK = 1 shl 3
        internal const val FLAG_BEGIN_SCROLL_MASK = 1 shl 4
        internal const val FLAG_BEGIN_DRAGG_MASK = 1 shl 5
        internal const val FLAG_BEGIN_DECELERATE_MASK = 1 shl 6
    }

    protected open fun setup(view: com.hitales.ui.ScrollView){
        layoutManager = BasicLayoutManager(this)
        var flexLayout = object : AndroidLayout(view){
            override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
                val maxWidth = MeasureSpec.getSize(widthMeasureSpec)
                val maxHeight = MeasureSpec.getSize(heightMeasureSpec)
                var widthMode = MeasureSpec.getMode(widthMeasureSpec)
                var heightMode = MeasureSpec.getMode(heightMeasureSpec)
                if(orientation == com.hitales.ui.Orientation.VERTICAL){
                    heightMode = MeasureSpec.UNSPECIFIED
                }else{
                    widthMode = MeasureSpec.UNSPECIFIED
                }
                super.onMeasure(MeasureSpec.makeMeasureSpec(maxWidth,widthMode), MeasureSpec.makeMeasureSpec(maxHeight,heightMode))
            }
        }
        flexLayout.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)
        flexLayout.setBackgroundColor(Colors.CLEAR)
        adapter = object : Adapter<ViewHolder>(){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                return ViewHolder(flexLayout)
            }

            override fun getItemCount(): Int {
               return 1
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            }
        }
        this.flexLayout = flexLayout
    }

    fun getScrolledX():Int{
        val layout = layoutManager
        if(layout != null && layout is BasicLayoutManager){
            return layout.scrollX
        }
        return scrollX
    }

    fun getScrolledY():Int{
        val layout = layoutManager
        if(layout != null && layout is BasicLayoutManager){
            return layout.scrollY
        }
        return scrollY
    }

    fun getContextSize():Size{
        val layout = layoutManager
        if(layout != null && layout is BasicLayoutManager){
            return layout.contextSize.clone()
        }
        return Size()
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        flag = flag or FLAG_LAYOUT_MASK
        super.onLayout(changed, l, t, r, b)
    }

    fun addSubView(view: View,index:Int = -1){
        if(index < 0){
            flexLayout?.addView(view)
        }else{
            flexLayout?.addView(view,index)
        }
        if(flag and FLAG_LAYOUT_MASK == FLAG_LAYOUT_MASK){
            onDataSetChanged()
        }
    }

    fun removeSubView(view: View){
        flexLayout?.removeView(view)
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        flexLayout?.setPadding(left, top, right, bottom)
    }

    open fun onDataSetChanged(){

    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (!scrollEnabled) false else super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        val action = e.action
        val layoutManager = this.layoutManager
        if( layoutManager != null && layoutManager is BasicLayoutManager && (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL)){
            layoutManager.startReleaseAnimation()
        }
        if(action == MotionEvent.ACTION_DOWN){
            return if (scrollEnabled) super.onTouchEvent(e) else scrollEnabled
        }
        return super.onTouchEvent(e)
    }


    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        when(state){
            SCROLL_STATE_IDLE -> {
                if(flag and FLAG_BEGIN_DECELERATE_MASK == FLAG_BEGIN_DECELERATE_MASK){
                    onEndDecelerating()
                    flag = flag and FLAG_BEGIN_DECELERATE_MASK.inv()
                }
                if(flag and FLAG_BEGIN_SCROLL_MASK == FLAG_BEGIN_SCROLL_MASK){
                    onEndScrolling()
                    flag = flag and FLAG_BEGIN_SCROLL_MASK.inv()
                }
            }
            SCROLL_STATE_DRAGGING -> {
                if(flag and FLAG_BEGIN_SCROLL_MASK != FLAG_BEGIN_SCROLL_MASK){
                    onBeginScrolling()
                    flag = flag or FLAG_BEGIN_SCROLL_MASK
                }
                if(flag and FLAG_BEGIN_DRAGG_MASK != FLAG_BEGIN_DRAGG_MASK){
                    onBeginDragging()
                    flag = flag or FLAG_BEGIN_DRAGG_MASK
                }
            }
            SCROLL_STATE_SETTLING -> {
                if(flag and FLAG_BEGIN_SCROLL_MASK != FLAG_BEGIN_SCROLL_MASK){
                    onBeginScrolling()
                    flag = flag or FLAG_BEGIN_SCROLL_MASK
                }
                if(flag and FLAG_BEGIN_DRAGG_MASK == FLAG_BEGIN_DRAGG_MASK){
                    onEndDragging()
                    flag = flag and FLAG_BEGIN_DRAGG_MASK.inv()
                }
                if(flag and FLAG_BEGIN_DECELERATE_MASK != FLAG_BEGIN_DECELERATE_MASK){
                    onBeginDecelerating()
                    flag = flag or FLAG_BEGIN_DECELERATE_MASK
                }
            }
        }
    }


    open fun onBeginScrolling(){

    }

    open fun onEndScrolling(){

    }

    open fun onBeginDragging(){

    }

    open fun onEndDragging(){

    }

    open fun onBeginDecelerating(){

    }
    open fun onEndDecelerating(){

    }
}