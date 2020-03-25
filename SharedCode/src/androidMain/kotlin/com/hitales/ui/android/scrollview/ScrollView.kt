package com.hitales.ui.android.scrollview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hitales.android.R
import com.hitales.ui.Colors
import com.hitales.ui.Platform
import com.hitales.ui.android.AndroidLayout

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
        }

    var scrollEnabled = true

    protected var layouted = false

    protected var flexLayout: AndroidLayout? = null

//    open class ViewHolder(val view: View,val v:com.hitales.ui.recycler.CollectionViewCell? = null) : RecyclerView.ViewHolder(view) {
//
//    }

    open class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

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
        fun createFromXLM(): ScrollView {
            val scrollView = LayoutInflater.from(Platform.getApplication()).inflate(R.layout.recycler_scroll_view, null) as ScrollView
            return scrollView
        }
    }

    protected open fun setup(view: com.hitales.ui.ScrollView){
        layoutManager = BasicLayoutManager(this)
        flexLayout = object : AndroidLayout(view){
            override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
                val maxWidth = MeasureSpec.getSize(widthMeasureSpec)
                val maxHeight = MeasureSpec.getSize(heightMeasureSpec)
                var widthMode = MeasureSpec.getMode(widthMeasureSpec)
                var heightMode = MeasureSpec.getMode(heightMeasureSpec)
                if(orientation == com.hitales.ui.Orientation.VERTICAL){
                    heightMode = MeasureSpec.UNSPECIFIED
                }else{
                    heightMode = MeasureSpec.UNSPECIFIED
                }
                super.onMeasure(MeasureSpec.makeMeasureSpec(maxWidth,widthMode), MeasureSpec.makeMeasureSpec(maxHeight,heightMode))
            }
        }
        flexLayout?.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)
        flexLayout?.setBackgroundColor(Colors.CLEAR)
        adapter = object : Adapter<ViewHolder>(){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                return ViewHolder(flexLayout!!)
            }

            override fun getItemCount(): Int {
               return 1
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            }

        }
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

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        layouted = true
        super.onLayout(changed, l, t, r, b)
    }

    fun addSubView(view: View,index:Int = -1){
        if(index < 0){
            flexLayout?.addView(view)
        }else{
            flexLayout?.addView(view,index)
        }
        if(layouted){
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

}