package com.hitales.ui

import android.animation.Animator
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.FrameLayout
import com.hitales.ui.android.AndroidView
import com.hitales.ui.android.Background
import com.hitales.ui.animation.toAnimator
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.EdgeInsets
import com.hitales.utils.Frame
import com.hitales.utils.Size
import com.hitales.utils.WeakReference

actual open class View{

    protected val mWidget: android.view.View = createWidget()

    private var mBackgroundColor = Colors.WHITE

    var mBackground: Background? = null

    actual var delegate:WeakReference<ViewDelegate>? = null
        set(value) {
            field = value
            if(value != null){
                mWidget.setOnClickListener{
                    onPressListener?.invoke(this)
                    delegate?.get()?.onPress(this)
                }
                mWidget.setOnLongClickListener{
                    onLongPressListener?.invoke(this)
                    delegate?.get()?.onLongPress(this)
                    return@setOnLongClickListener true
                }
            }else {
                if(onPressListener == null){
                    mWidget.setOnClickListener(null)
                }
                if(onLongPressListener == null){
                    mWidget.setOnLongClickListener(null)
                }
            }
        }

    init {
        mWidget.setBackgroundColor(mBackgroundColor)
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

    actual open var isHidden: Boolean = false
        set(value) {
            field = value
            if(value){
                mWidget.visibility = android.view.View.INVISIBLE
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

    actual open var tag: Any? = null

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

    actual open var clipsToBounds: Boolean = false
        set(value) {
            field = value
            if (mWidget is android.view.ViewGroup){
                mWidget.clipChildren = value
            }
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
        superView?.removeSubView(this)
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
        this.checkLayerType()
    }

    actual open fun setBorderRadius(radius: Float) {
        setBorderRadius(radius, radius, radius, radius)
    }

    actual open fun setBorderRadius(topLeftRadius: Float, topRightRadius: Float, bottomRightRadius: Float, bottomLeftRadius: Float
    ) {
        val background = getOrCreateBackground()
        background.setBorderRadius(topLeftRadius, topRightRadius, bottomRightRadius, bottomLeftRadius)
        this.checkLayerType()
    }

    fun checkLayerType(){
        val background = getOrCreateBackground()
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

    fun getOrCreateBackground(): Background {
        if (mBackground == null) {
            mBackground = Background()
            mWidget.setBackgroundDrawable(mBackground)
            mBackground!!.setColor(mBackgroundColor)
        }
        return mBackground!!
    }

    fun getBackground(): Background? {
        return mBackground
    }

    actual open fun onFrameChanged() {
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
        if(listener != null){
            mWidget.setOnClickListener{
                onPressListener?.invoke(this)
                delegate?.get()?.onPress(this)
            }
        }else{
            mWidget.setOnClickListener(null)
        }
    }

    actual fun setOnLongPressListener(listener: (iew: com.hitales.ui.View) -> Unit) {
        onLongPressListener = listener
        if(listener != null){
            mWidget.setOnLongClickListener{
                onLongPressListener?.invoke(this)
                delegate?.get()?.onLongPress(this)
                return@setOnLongClickListener true
            }
        }else{
            mWidget.setOnLongClickListener(null)
        }
    }

    protected open fun getLayoutParams(): android.view.ViewGroup.LayoutParams {
        var params = mWidget.layoutParams
        var top = PixelUtil.toPixelFromDIP(frame.y).toInt()
        var left = PixelUtil.toPixelFromDIP(frame.x).toInt()
        var width = PixelUtil.toPixelFromDIP(frame.width).toInt()
        var height = PixelUtil.toPixelFromDIP(frame.height).toInt()
        var right = left + width
        var bottom = top + height
        val background = mBackground
        if(background != null && background.haveShadow()){
            val radius = background.shadowRadius
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
        if (params == null || params !is android.view.ViewGroup.MarginLayoutParams) {
            params = FrameLayout.LayoutParams(right - left, bottom - top)
        } else {
            params.width = right - left
            params.height = bottom - top
        }
        if(params is android.view.ViewGroup.MarginLayoutParams){
            params.topMargin = top
            params.leftMargin = left
        }
        return params
    }

    /**
     * @param widthSpace 最大宽度  如果小于等于0表示无限宽
     * @param heightSpace 最大高度  如果小于等于0表示无限高
     */
    actual open fun measureSize(widthSpace: Float,heightSpace: Float): Size {
        val size =  Size()
        measureSize(widthSpace,heightSpace,size)
        return size
    }

    actual open fun measureSize(widthSpace: Float,heightSpace: Float,size: Size){
        if(!frame.valid()){
            size.set(frame.width,frame.height)
            return
        }
        var maxWidth = widthSpace
        var maxHeight = heightSpace
        var width = PixelUtil.toPixelFromDIP(maxWidth).toInt()
        var height = PixelUtil.toPixelFromDIP(maxHeight).toInt()
        if( width <= 0 ){
            width = android.view.View.MeasureSpec.makeMeasureSpec(0, android.view.View.MeasureSpec.UNSPECIFIED)
        }else{
            width = android.view.View.MeasureSpec.makeMeasureSpec(width, android.view.View.MeasureSpec.AT_MOST)
        }
        if ( height <= 0 ){
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
        val animatorSet = animation.toAnimator(getWidget())
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

    actual open fun setShadow(color: Int,radius: Float, dx: Float, dy: Float) {
        getOrCreateBackground().setShadow(radius,dx, dy, color)
        onFrameChanged()
        this.checkLayerType()
    }


}