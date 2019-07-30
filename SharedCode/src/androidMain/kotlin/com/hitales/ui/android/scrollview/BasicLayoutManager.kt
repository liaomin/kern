package com.hitales.ui.android.scrollview

import android.animation.ValueAnimator
import android.view.ViewGroup
import androidx.core.view.animation.PathInterpolatorCompat
import androidx.recyclerview.widget.RecyclerView
import com.hitales.ui.android.ViewStyle
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.Size

open class BasicLayoutManager  : RecyclerView.LayoutManager {

    open class IOSDamping(val maxDistanceX:Int,val maxDistanceY:Int,val min:Int) {

        var offsetX:Int = 0

        var offsetY:Int = 0

        fun addOffsetX(dx:Int):Int{
            val off = calculateOffset(offsetX,dx,maxDistanceX)
            offsetX += off
            return off
        }

        fun addOffsetY(dy:Int):Int{
            var off = calculateOffset(offsetY,dy,maxDistanceY)
            val abs = Math.abs(dy)
            if(off == 0){
                if(abs >= min){
                    off = if(dy > 0) min else -min
                }else{
                    off = if(dy > 0) 1 else -1
                }
            }
            val nextOff = offsetY + off
            if(nextOff >= 0 && offsetY <=0){
                offsetY = 0
                off = nextOff - offsetY
            }else{
                offsetY += off
            }
            return off
        }

        open fun calculateOffset(offset: Int,d:Int,maxDistance:Int):Int{
            val abs = Math.abs(offset)
            var dis = maxDistance - abs
            if(abs >= maxDistance ){
                dis = min
            }
            return (d * ( dis / maxDistance.toFloat())).toInt()
        }

        open fun release(){

        }

    }

    var style = ViewStyle.ANDROID
        set(value){
            field = value
            if(value == ViewStyle.IOS){
                val max = PixelUtil.toPixelFromDIP(100f).toInt()
                val min = PixelUtil.toPixelFromDIP(1f).toInt()
                damping = IOSDamping(max / 2 , max,min)
            }else{
                damping = null
            }
        }

    val recyclerView: RecyclerView

    private var damping:IOSDamping?= null

    constructor(recyclerView: RecyclerView):super(){
        this.recyclerView = recyclerView
    }

    var contextSize = Size()

    var scrollX = 0

    var scrollY = 0

    var outRangeX = 0

    var outRangeY = 0

    var isAnimated = false

    val tempArray = arrayOf(0,0)

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)
        val pl = paddingLeft
        val pr = paddingRight
        val pt = paddingTop
        val pb = paddingBottom
        var maxWidth = width
        var maxHeight = height

        for (i in 0 until itemCount) {
            val scrap = recycler.getViewForPosition(i)
            addView(scrap)

            val lp = scrap.layoutParams as (RecyclerView.LayoutParams)
            val t = lp.topMargin + pt
            val l = lp.leftMargin + pl

            measureChildWithMargins(scrap, 0, 0)
            val itemWidth = getDecoratedMeasuredWidth(scrap)
            val itemHeight = getDecoratedMeasuredHeight(scrap)

            val r = l + itemWidth
            val b = t + itemHeight
            if(r + pr > maxWidth){
                maxWidth = r + pr
            }
            if(b + pb > maxHeight){
                maxHeight = b + pb
            }
            layoutDecorated(scrap, l , t , r, b)
        }
        contextSize.set(maxWidth ,maxHeight)
    }

    override fun canScrollVertically(): Boolean {
        return contextSize.height > height
    }

    override fun canScrollHorizontally(): Boolean {
        return contextSize.width > width
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        scrollBy(scrollX,dx,getMaxScrollX(),recycler,state)
        var used = tempArray[0]
        var unused = tempArray[1]
        scrollX += used
        if(used != 0){
            offsetChildrenHorizontal(-used )
        }
        return used
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
//        val damp = damping
//        if(damp != null && damp.offsetY != 0){
//            var off = damp.addOffsetY(dy)
//        }
        scrollBy(scrollY,dy,getMaxScrollY(),recycler,state)
        var used = tempArray[0]
        var unused = tempArray[1]
        scrollY += used
        if(used != 0){
            offsetChildrenVertical( -used )
        }
//        if(unused != 0 && damp != null){
//            val temp = unused
//            unused = damp.addOffsetY(temp)
//            val off = unused - temp
//            offsetChildrenVertical( -off )
//        }
        return used
    }

   open fun scrollBy(currentOffset:Int, offset: Int, maxOffset:Int, recycler: RecyclerView.Recycler, state: RecyclerView.State){
        var used = 0
        var unused = Math.abs(offset)
        var mOffset = currentOffset
        val nextOffset = currentOffset + offset
        if(offset > 0 ){
            //手指向上滑动，view向下，子view向上移动，减移动距离
            if( mOffset < maxOffset ){
                if(nextOffset >= maxOffset){
                    used = maxOffset - mOffset
                    unused -= used
                }else{
                    used = unused
                    unused= 0
                }
            }
        }else{
            //手指向下滑动，view向上，子view向下移动，加移动距离
            if( mOffset > 0 ){
                if(nextOffset < 0){
                    used = -mOffset
                    unused = -(unused - mOffset)
                }else{
                    used = -unused
                    unused= 0
                }
            }
        }
        tempArray[0] = used
        tempArray[1] = unused
    }

    override fun computeVerticalScrollOffset(state: RecyclerView.State): Int {
        return scrollY
    }

    override fun computeVerticalScrollRange(state: RecyclerView.State): Int {
        return contextSize.height.toInt()
    }

    override fun computeVerticalScrollExtent(state: RecyclerView.State): Int {
        return height
    }

    override fun computeHorizontalScrollOffset(state: RecyclerView.State): Int {
        return scrollX
    }

    override fun computeHorizontalScrollRange(state: RecyclerView.State): Int {
        return contextSize.width.toInt()
    }

    override fun computeHorizontalScrollExtent(state: RecyclerView.State): Int {
        return width
    }

    open fun getMaxScrollY():Int{
        return contextSize.height.toInt() - height
    }

    open fun getMaxScrollX():Int{
        return contextSize.width.toInt() - width
    }

    open fun startReleaseAnimation(){
        if( outRangeX != 0 || outRangeY != 0){
            val endAnimator =  ValueAnimator.ofInt(1,3,4)
            endAnimator.duration = 500
            endAnimator.interpolator = PathInterpolatorCompat.create(0.19f,1f,0.22f,1f)
            endAnimator.addUpdateListener {
                println("${it.animatedValue}")
            }
            endAnimator.start()
        }
    }
}