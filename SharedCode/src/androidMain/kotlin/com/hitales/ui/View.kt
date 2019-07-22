package com.hitales.ui

import android.animation.Animator
import android.animation.AnimatorSet
import android.graphics.Canvas
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.animation.Transformation
import android.widget.FrameLayout
import com.hitales.ui.android.AndroidView
import com.hitales.ui.android.Background
import com.hitales.ui.animation.setAnimation
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.EdgeInsets
import com.hitales.utils.Frame
import com.hitales.utils.Size
import com.hitales.utils.WeakReference

actual open class View {

    protected val mWidget: android.view.View = createWidget()

    private var mBackgroundColor = Colors.TRANSPARENT

    var mBackground: Background? = null

    init {
        mWidget.setBackgroundColor(mBackgroundColor)
        mWidget.setOnClickListener{
            onPressListener?.invoke(this)
        }
        mWidget.setOnLongClickListener{
            onLongPressListener?.invoke(this)
            if(onLongPressListener != null){
                return@setOnLongClickListener true
            }
            return@setOnLongClickListener false
        }
    }

    var innerPadding : EdgeInsets? = null
        set(value) {
            field = value
            onPaddingSet()
        }

    actual var padding: EdgeInsets? = null
        set(value) {
            field = value
            onPaddingSet()
        }

    actual var margin: EdgeInsets? = null

    actual open var frame: Frame
        set(value) {
            field = value
            onFrameChanged()
        }

    actual open var hidden: Boolean = false
        set(value) {
            field = value
            if(value){
                mWidget.visibility = android.view.View.GONE
            }else{
                mWidget.visibility = android.view.View.VISIBLE
            }
        }


    actual var superView: ViewGroup? = null

    actual open var id: Int
        get() = mWidget.id
        set(value) {
            mWidget.id = value
        }

    actual open var tag: Any?
        get() = mWidget.tag
        set(value) {
            mWidget.tag = value
        }

    actual open var elevation:Float = 0f
        get() {
            if(Build.VERSION.SDK_INT >= 21){
               return mWidget.getElevation()
            }
            return field
        }
        set(value) {
            if(Build.VERSION.SDK_INT >= 21){
                mWidget.elevation = PixelUtil.toPixelFromDIP(value)
            }
            field = value
        }

    actual open var borderStyle:BorderStyle = BorderStyle.SOLID
        set(value) {
            field = value
            mBackground?.setBorderStyle(value)
        }

    /**
     * 透明度 0~1
     */
    actual open var opacity: Float
        get() = getWidget().alpha
        set(value) {
            getWidget().alpha = value
        }

    actual open var translateX: Float
        get() = getWidget().translationX
        set(value) {
            getWidget().translationX = value
        }

    actual open var translateY: Float
        get() = getWidget().translationY
        set(value) {
            getWidget().translationY = value
        }

    actual open var rotateX: Float
        get() = getWidget().rotationX
        set(value) {
            getWidget().rotationX = value
        }

    actual open var rotateY: Float
        get() = getWidget().rotationY
        set(value) {
            getWidget().rotationY = value
        }

    actual open var rotateZ: Float
        get() = getWidget().rotation
        set(value) {
            getWidget().rotation = value
        }

    actual open var scaleX: Float
        get() = getWidget().scaleX
        set(value) {
            getWidget().scaleX = value
        }

    actual open var scaleY: Float
        get() = getWidget().scaleY
        set(value) {
            getWidget().scaleY = value
        }

    private var onPressListener:((view: com.hitales.ui.View)->Unit)? = null

    private var onLongPressListener:((view: com.hitales.ui.View)->Unit)? = null


    actual constructor(frame: Frame) {
        this.frame = frame
    }

    open fun getWidget(): android.view.View {
        return mWidget
    }

    open fun createWidget(): android.view.View {
        return AndroidView(this)
    }

    actual open fun setBackgroundColor(color: Int) {
        if (mBackground != null) {
            mBackground?.setColor(color)
        } else {
            mWidget.setBackgroundColor(color)
        }
        mBackgroundColor = color
    }


    actual open fun removeFromSuperView() {
        superView?.removeView(this)
        superView = null
    }

    actual open fun onAttachedToWindow() {

    }

    actual open fun onDetachedFromWindow() {

    }

    actual open fun onAttachedToView(layoutView: ViewGroup) {

    }

    actual open fun onDetachedFromView(layoutView: ViewGroup) {

    }

    actual open fun setBorderColor(color: Int) {
        getOrCreateBackground().setBorderColor(color)
    }

    actual open fun setBorderColor(
        leftColor: Int, topColor: Int, rightColor: Int, bottomColor: Int
    ) {

        getOrCreateBackground().setBorderColor(leftColor, topColor, rightColor, bottomColor)
    }

    actual open fun setBorderWidth(borderWidth: Float) {
        getOrCreateBackground().setBorderWidth(borderWidth,borderStyle)
    }

    actual open fun setBorderWidth(leftWidth: Float, topWidth: Float, rightWidth: Float, bottomWidth: Float
    ) {
        val background = getOrCreateBackground()
        background.setBorderWidth(leftWidth, topWidth, rightWidth, bottomWidth,borderStyle)
        if (background.clipPath()) {
            setLayerType(android.view.View.LAYER_TYPE_SOFTWARE)
        } else {
            setLayerType(android.view.View.LAYER_TYPE_HARDWARE)
        }
    }

    actual open fun setBorderRadius(radius: Float) {
        setBorderRadius(radius, radius, radius, radius)
    }

    actual open fun setBorderRadius(topLeftRadius: Float, topRightRadius: Float, bottomRightRadius: Float, bottomLeftRadius: Float
    ) {
        val background = getOrCreateBackground()
        background.setBorderRadius(topLeftRadius, topRightRadius, bottomRightRadius, bottomLeftRadius)
        if (background.clipPath()) {
            setLayerType(android.view.View.LAYER_TYPE_SOFTWARE)
        } else {
            setLayerType(android.view.View.LAYER_TYPE_HARDWARE)
        }
    }

    open fun setLayerType(layerType: Int) {
        if (mWidget.layerType != layerType) {
            mWidget.setLayerType(layerType, null)
        }
    }

    actual fun getBorderLeftWidth(): Float {
        return mBackground?.borderLeftWidth ?: 0f
    }

    actual fun getBorderTopWidth(): Float {
        return mBackground?.borderTopWidth ?: 0f
    }

    actual fun getBorderRightWidth(): Float {
        return mBackground?.borderRightWidth ?: 0f
    }

    actual fun getBorderBottomWidth(): Float {
        return mBackground?.borderBottomWidth ?: 0f
    }

    protected fun getOrCreateBackground(): Background {
        if (mBackground == null) {
            mBackground = Background()
            mWidget.setBackgroundDrawable(mBackground)
            mBackground?.setColor(mBackgroundColor)
        }
        return mBackground!!
    }


    fun getBackground(): Background? {
        return mBackground
    }

    protected open fun onFrameChanged() {
        var params = getLayoutParams()
        mWidget.layoutParams = params
    }

    protected open fun onPaddingSet(){
        val padding = EdgeInsets.zero()
        getPadding(padding)
        mWidget.setPadding(padding.left.toInt(),padding.top.toInt(),padding.right.toInt(),padding.bottom.toInt())
    }

    protected open fun getPadding(padding:EdgeInsets){
        val tPadding = this.padding
        if(tPadding != null){
            padding.left += PixelUtil.toPixelFromDIP(tPadding.left)
            padding.right += PixelUtil.toPixelFromDIP(tPadding.right)
            padding.top += PixelUtil.toPixelFromDIP(tPadding.top)
            padding.bottom += PixelUtil.toPixelFromDIP(tPadding.bottom)
        }
        val innerPadding = this.innerPadding
        if(innerPadding != null){
            padding.left -= innerPadding.left
            padding.right += innerPadding.right
            padding.top -= innerPadding.top
            padding.bottom += innerPadding.bottom
        }
    }

    actual fun setOnPressListener(listener: (view: com.hitales.ui.View) -> Unit) {
        onPressListener = listener
    }

    actual fun setOnLongPressListener(listener: (iew: com.hitales.ui.View) -> Unit) {
        onLongPressListener = listener
    }

    protected open fun getLayoutParams(): FrameLayout.LayoutParams {
        var params = mWidget.layoutParams
        var top = PixelUtil.toPixelFromDIP(frame.y).toInt()
        var left = PixelUtil.toPixelFromDIP(frame.x).toInt()
        var width = PixelUtil.toPixelFromDIP(frame.width).toInt()
        var height = PixelUtil.toPixelFromDIP(frame.height).toInt()
        var right = left + width
        var bottom = top + height
        val background = mBackground
        if(background != null && background.haveShadow()){
            val radius =background.shadowRadius
            val dx= background.shadowDx
            val dy= background.shadowDy
            val l = (left + dx - radius).toInt()
            val t = (top + dy - radius).toInt()
            val r = (right + dx + radius).toInt()
            val b = (bottom + dy + radius).toInt()
            val ol = left.toFloat()
            val ot = top.toFloat()
            val ob = bottom.toFloat()
            val or = right.toFloat()
            left = Math.min(l,left)
            top = Math.min(t,top)
            right = Math.max(right,r)
            bottom = Math.max(bottom,b)
            background.offset = Frame(left - ol,top - ot,width.toFloat(),height.toFloat())
            innerPadding = EdgeInsets(top - ot,left - ol,bottom - ob,right - or)
        }else{
            background?.offset = null
            innerPadding = null
        }
        if (params == null || params !is FrameLayout.LayoutParams) {
            params = FrameLayout.LayoutParams(right - left, bottom - top)
        } else {
            params.width = right - left
            params.height = bottom - top
        }
        params.topMargin = top
        params.leftMargin = left

        return params
    }

    /**
     * @param maxWidth 最大宽度  如果小于等于0表示无限宽
     * @param maxHeight 最大高度  如果小于等于0表示无限高
     */
    actual open fun measureSize(maxWidth: Float, maxHeight: Float): Size {
        val size =  Size()
        measureSize(maxWidth,maxHeight,size)
        return size
    }

    actual open fun measureSize(maxWidth: Float, maxHeight: Float,size: Size){
        if(!frame.valid()){
            size.set(frame.width,frame.height)
            return
        }
        var width = PixelUtil.toPixelFromDIP(maxWidth).toInt()
        var height = PixelUtil.toPixelFromDIP(maxHeight).toInt()
        if( width <= 0 ){
            width = android.view.View.MeasureSpec.makeMeasureSpec(0, android.view.View.MeasureSpec.UNSPECIFIED)
        }else{
            width = android.view.View.MeasureSpec.makeMeasureSpec(width, android.view.View.MeasureSpec.AT_MOST)
        }
        if( height <= 0 ){
            height = android.view.View.MeasureSpec.makeMeasureSpec(0, android.view.View.MeasureSpec.UNSPECIFIED)
        }else{
            height = android.view.View.MeasureSpec.makeMeasureSpec(height, android.view.View.MeasureSpec.AT_MOST)
        }
        mWidget.measure(width,height)
        val measuredWidth = mWidget.measuredWidth
        val measuredHeight = mWidget.measuredHeight
        size.set(PixelUtil.toDIPFromPixel(measuredWidth.toFloat()), PixelUtil.toDIPFromPixel(measuredHeight.toFloat()))
    }

    protected open fun setBackgroundDrawable(drawable: Drawable,state: ViewState){
        getOrCreateBackground().setDrawable(drawable,state)
    }

    override fun toString(): String {
        return "${this::class.java.name}: frame :$frame"
    }

    actual open fun startAnimation(animation: Animation, completion: (() -> Unit)?) {
        val animatorSet = setAnimation(animation)
        val widget = WeakReference<android.view.View>(mWidget)
        animatorSet.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animator: Animator?) {
                val view = widget.get()
                if(!animation.fillAfter && view != null){
                    view.translationX = 0f
                    view.translationY = 0f
                    view.rotationX = 0f
                    view.rotationY = 0f
                    view.rotation = 0f
                    view.alpha = 1f
                    view.scaleX = 1f
                    view.scaleY = 1f
                }
                completion?.invoke()
                animation.delegate?.onAnimationStop(animation,this@View)
            }

            override fun onAnimationCancel(animator: Animator?) {

            }

            override fun onAnimationStart(animator: Animator?) {
                animation.delegate?.onAnimationStart(animation,this@View)
            }

        })
        animatorSet.start()
    }

    actual open fun releaseResource() {
        superView = null
    }

    /**
     * touches
     */
    actual open fun dispatchTouchEvent(touches: Touches): Boolean {
        return onInterceptTouchEvent(touches)
    }

    actual open fun onInterceptTouchEvent(touches: Touches): Boolean {
        return false
    }

    actual open fun touchesBegan(touches: Touches) {
        println("$this touchesBegan")
    }

    actual open fun touchesMoved(touches: Touches) {
        println("$this touchesMoved")
    }

    actual open fun touchesEnded(touches: Touches) {
        println("$this touchesEnded")
    }

    actual open fun touchesCancelled(touches: Touches) {
        println("$this touchesCancelled")
    }

    actual open fun setShadow(radius: Float, dx: Float, dy: Float, color: Int) {
        getOrCreateBackground().setShadow(radius,dx, dy, color)
        onFrameChanged()
    }
}