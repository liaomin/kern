package com.hitales.ui.android

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.TranslateAnimation
import androidx.core.widget.NestedScrollView
import com.hitales.ui.utils.PixelUtil


open class DampScrollView2 : NestedScrollView {

    // y方向上当前触摸点的前一次记录位置
    private var previousY = 0F
    // y方向上的触摸点的起始记录位置
    private var startY = 0F
    // y方向上的触摸点当前记录位置
    private var currentY = 0F
    // y方向上两次移动间移动的相对距离
    private var deltaY = 0F

    // 第一个子视图
    private var childView: View? = null

    // 用于记录childView的初始位置
    private val topRect = Rect()

    //水平移动搞定距离
    private var moveHeight: Float = 0.toFloat()

    constructor(context: Context) : super(context) {
        overScrollMode = View.OVER_SCROLL_NEVER
        isVerticalScrollBarEnabled  = true
        isFillViewport = true
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        println("this2 $this")
    }

    override fun dispatchNestedFling(velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return super.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val s = super.onInterceptTouchEvent(ev)
        return s
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        val h = PixelUtil.toPixelFromDIP(200f)
        val mh = measuredHeight
        val s = super.onTouchEvent(ev)
        return s
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val h = PixelUtil.toPixelFromDIP(200f)
        val p = Paint()
        p.style = Paint.Style.FILL
        p.color = Color.BLUE
        canvas.drawRect(0f,0f,canvas.width.toFloat(),h,p)
    }
//    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
//        if (getChildCount() > 0) {
//            childView = getChildAt(0)
//        }
//        if (null == childView) {
//            return super.dispatchTouchEvent(event)
//        }
//
//        when (event.getAction()) {
//            MotionEvent.ACTION_DOWN -> {
//                startY = event.getY()
//                previousY = startY
//
//                // 记录childView的初始位置
//                topRect.set(
//                    childView!!.getLeft(), childView!!.getTop(),
//                    childView!!.getRight(), childView!!.getBottom()
//                )
//                moveHeight = 0f
//            }
//            MotionEvent.ACTION_MOVE -> {
//                currentY = event.getY()
//                deltaY = currentY - previousY
//                previousY = currentY
//
//                //判定是否在顶部或者滑到了底部
//                if (!childView!!.canScrollVertically(-1) && currentY - startY > 0 || !childView!!.canScrollVertically(1) && currentY - startY < 0) {
//                    //计算阻尼
//                    var distance = (currentY - startY).toFloat()
//                    if (distance < 0) {
//                        distance *= -1f
//                    }
//
//                    var damping = 0.5f//阻尼值
//                    val height = getHeight().toFloat()
//                    if (height != 0f) {
//                        if (distance > height) {
//                            damping = 0f
//                        } else {
//                            damping = (height - distance) / height
//                        }
//                    }
//                    if (currentY - startY < 0) {
//                        damping = 1 - damping
//                    }
//
//                    //阻力值限制再0.3-0.5之间，平滑过度
//                    damping *= 0.25f
//                    damping += 0.25f
//
//                    moveHeight = moveHeight + deltaY * damping
//
//                    childView!!.layout(
//                        topRect.left, (topRect.top + moveHeight).toInt(), topRect.right,
//                        (topRect.bottom + moveHeight).toInt()
//                    )
//                }
//            }
//            MotionEvent.ACTION_UP -> {
//                if (!topRect.isEmpty()) {
//                    //开始回移动画
//                    upDownMoveAnimation()
//                    // 子控件回到初始位置
//                    childView!!.layout(
//                        topRect.left, topRect.top, topRect.right,
//                        topRect.bottom
//                    )
//                }
//                //重置一些参数
//                startY = 0f
//                currentY = 0f
//                topRect.setEmpty()
//            }
//        }
//
//        return super.dispatchTouchEvent(event)
//    }



    // 初始化上下回弹的动画效果
    private fun upDownMoveAnimation() {
        val animation = TranslateAnimation(
            0.0f, 0.0f,
            childView!!.getTop().toFloat(), topRect.top.toFloat()
        )
        animation.setDuration(600)
        animation.setFillAfter(true)
        //设置阻尼动画效果
        animation.setInterpolator(DampInterpolator())
        childView!!.setAnimation(animation)
    }

    inner class DampInterpolator : Interpolator {
        override fun getInterpolation(input: Float): Float {
            //先快后慢，为了更快更慢的效果，多乘了几次，现在这个效果比较满意
            return 1 - (1 - input) * (1 - input) * (1 - input) * (1 - input) * (1 - input)
        }

    }
}