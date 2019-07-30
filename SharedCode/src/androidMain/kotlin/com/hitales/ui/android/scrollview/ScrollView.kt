package com.hitales.ui.android.scrollview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.hitales.android.R
import com.hitales.ui.Platform
import com.hitales.ui.android.ViewStyle


open class ScrollView : RecyclerView {

    var style = ViewStyle.ANDROID
        set(value) {
            field = value
            val lm = layoutManager
            if(lm is BasicLayoutManager){
                lm.style = value
            }
            if(value == ViewStyle.IOS){
                overScrollMode = View.OVER_SCROLL_NEVER
            }else{
                overScrollMode = View.OVER_SCROLL_IF_CONTENT_SCROLLS
            }
        }

    var scrollEnabled = true

    protected var layouted = false

    protected var frameLayout: FrameLayout? = null

    open class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }

    constructor() : super(Platform.getApplication()) {
        scrollBarStyle = SCROLLBARS_OUTSIDE_OVERLAY
        clipToPadding = false
        setup()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        scrollBarStyle = SCROLLBARS_OUTSIDE_OVERLAY
        clipToPadding = false
        setup()
    }

    companion object {
        fun createFromXLM(): ScrollView {
            val scrollView = LayoutInflater.from(Platform.getApplication()).inflate(R.layout.recycler_scroll_view, null) as ScrollView
            return scrollView
        }
    }

    protected open fun setup(){
        layoutManager = BasicLayoutManager(this)
        val frameLayout = object : FrameLayout(Platform.getApplication()){
            override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
                var maxWidth = measuredWidth
                var maxHeight = measuredHeight
                for (i in 0 until childCount){
                    val child = getChildAt(i)
                    if(child.visibility != View.GONE){
                        var mt = 0
                        var ml = 0
                        var mr = 0
                        var mb = 0
                        val lp = child.layoutParams
                        if(lp is MarginLayoutParams){
                            mt = lp.topMargin
                            ml = lp.leftMargin
                            mr = lp.rightMargin
                            mb = lp.bottomMargin
                        }
                        val childWidth = child.measuredWidth + ml + mr
                        val childHeight = child.measuredHeight+ mt + mb
                        if(maxWidth < childWidth) maxWidth = childWidth
                        if(maxHeight < childHeight) maxHeight = childHeight
                    }
                }
                maxWidth += paddingLeft + paddingRight
                maxHeight += paddingTop + paddingBottom
                setMeasuredDimension(maxWidth,maxHeight)
            }
        }
        val lp = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
        frameLayout.layoutParams = lp
        this.frameLayout = frameLayout
        adapter = object : Adapter<ViewHolder>(){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                return ViewHolder(frameLayout)
            }

            override fun getItemCount(): Int {
                return 1
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            }
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        layouted = true
        super.onLayout(changed, l, t, r, b)
    }

    fun addSubView(view: View,index:Int = -1){
        if(index < 0){
            frameLayout?.addView(view)
        }else{
            frameLayout?.addView(view,index)
        }
        if(layouted){
            onDataSetChanged()
        }
    }

    fun removeSubView(view: View){
        frameLayout?.removeView(view)
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