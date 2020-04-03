package com.hitales.ui

import android.animation.Animator
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import com.hitales.ui.android.AndroidView
import com.hitales.ui.android.Background
import com.hitales.ui.animation.toAnimator
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.EdgeInsets
import com.hitales.utils.Frame
import com.hitales.utils.Size
import com.hitales.utils.WeakReference
import kotlin.math.max
import kotlin.math.min

actual open class View{

    companion object{
        fun getMeasureWidth(size:Float,sizeMode:MeasureMode):Int{
            val tempSize = PixelUtil.toPixelFromDIP(size).toInt()
            when (sizeMode){
                MeasureMode.AT_MOST -> return View.MeasureSpec.makeMeasureSpec(tempSize, View.MeasureSpec.AT_MOST)
                MeasureMode.UNSPECIFIED -> return View.MeasureSpec.makeMeasureSpec(tempSize, View.MeasureSpec.UNSPECIFIED)
                MeasureMode.EXACTLY -> return View.MeasureSpec.makeMeasureSpec(tempSize, View.MeasureSpec.EXACTLY)
            }
        }
    }

    actual var flags:Int = 0

    val mWidget: android.view.View

    private var mBackgroundColor = Colors.CLEAR

    var mBackground: Background? = null

    actual var delegate:WeakReference<ViewDelegate>? = null

    var innerPadding : EdgeInsets? = null
        set(value) {
            field = value
            calculatePadding()
        }

    actual var padding: EdgeInsets? = null
        set(value) {
            field = value
            calculatePadding()
        }

    actual open var layoutParams:LayoutParams?
        set(value) {
            field = value
            mWidget.requestLayout()
        }

    actual open val frame: Frame = Frame.identity()


    actual open var isHidden: Boolean = false
        set(value) {
            field = value
            if(value){
                mWidget.visibility = android.view.View.INVISIBLE
            }else{
                mWidget.visibility = android.view.View.VISIBLE
            }
        }


    actual var superView : WeakReference<Layout>? = null

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


    actual constructor(layoutParams:LayoutParams?) {
        mWidget = createWidget()
        mWidget.tag = this
        mWidget.setBackgroundColor(mBackgroundColor)
        this.layoutParams = layoutParams
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
        superView?.get()?.removeSubView(this)
        superView = null
    }

    actual open fun onAttachedToWindow() {

    }

    actual open fun onDetachedFromWindow() {

    }

    actual open fun onAttachedToView(layoutView: Layout) {

    }

    actual open fun onDetachedFromView(layoutView: Layout) {

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
        getOrCreateBackground().setBorderWidth(borderWidth)
    }

    actual open fun setBorderStyle(style: BorderStyle) {
        getOrCreateBackground().setBorderStyle(style)

    }

    actual open fun setBorderWidth(leftWidth: Float, topWidth: Float, rightWidth: Float, bottomWidth: Float
    ) {
        val background = getOrCreateBackground()
        background.setBorderWidth(leftWidth, topWidth, rightWidth, bottomWidth)
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

    actual fun setOnPressListener(listener: ((view: com.hitales.ui.View) -> Unit)?) {
        onPressListener = listener
        if(listener != null){

            mWidget.setOnClickListener{
                onPressListener?.invoke(this)
            }
        }else{
            mWidget.setOnClickListener(null)
        }
    }

    actual fun setOnLongPressListener(listener: ((view: com.hitales.ui.View) -> Unit)?) {
        onLongPressListener = listener
        if(listener != null){
            mWidget.setOnLongClickListener{
                onLongPressListener?.invoke(this)
                return@setOnLongClickListener true
            }
        }else{
            mWidget.setOnLongClickListener(null)
        }
    }

    actual open fun measure(widthSpace: Float,widthMode:MeasureMode, heightSpace: Float,heightMode:MeasureMode,outSize: Size){
        mWidget.measure(getMeasureWidth(widthSpace,widthMode), getMeasureWidth(heightSpace,heightMode))
        var measuredWidth = mWidget.measuredWidth.toFloat()
        var measuredHeight = mWidget.measuredHeight.toFloat()
        val inner = innerPadding
        if(inner != null && this.javaClass !== com.hitales.ui.View::class.java){
            measuredWidth = measuredWidth + inner.left - inner.right
            measuredHeight = measuredHeight + inner.top - inner.bottom
        }
        outSize.set(PixelUtil.toDIPFromPixel(measuredWidth), PixelUtil.toDIPFromPixel(measuredHeight))
    }

    actual open fun onFrameChanged(){
        delegate?.get()?.onFrameChanged(this)
    }

    actual open fun onLayout() {
        if(!isHidden){
            var l = PixelUtil.toPixelFromDIP(frame.x)
            var t = PixelUtil.toPixelFromDIP(frame.y)
            val width = PixelUtil.toPixelFromDIP(frame.width)
            val height = PixelUtil.toPixelFromDIP(frame.height)
            mBackground?.setOffsetSize(width,height)
            var r = l + width
            var b = t + height
            val inner = innerPadding
            if(inner != null){
                l += inner.left.toInt()
                t += inner.top.toInt()
                r += inner.right.toInt()
                b += inner.bottom.toInt()
            }
            mWidget.layout(l.toInt(), t.toInt(), r.toInt(), b.toInt())
            if(frame.isChanged()){
                onFrameChanged()
            }
        }
    }

    open fun calculatePadding(){
        val tPadding = this.padding
        var left = 0
        var top = 0
        var right = 0
        var bottom = 0
        if(tPadding != null){
            left += PixelUtil.toPixelFromDIP(tPadding.left).toInt()
            right += PixelUtil.toPixelFromDIP(tPadding.right).toInt()
            top += PixelUtil.toPixelFromDIP(tPadding.top).toInt()
            bottom += PixelUtil.toPixelFromDIP(tPadding.bottom).toInt()
        }
        val innerPadding = this.innerPadding
        if(innerPadding != null){
            left -= innerPadding.left.toInt()
            right += innerPadding.right.toInt()
            top -= innerPadding.top.toInt()
            bottom += innerPadding.bottom.toInt()
        }
        onCalculatePadding(left,top, right, bottom)
    }

    protected open fun onCalculatePadding(left:Int,top:Int,right:Int,bottom:Int){
        mWidget.setPadding(left,top, right, bottom)
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

    /**
     * touches
     */
    actual open fun dispatchTouchEvent(touches: Touches): Boolean {
        return onInterceptTouchEvent(touches)
    }

    actual open fun onInterceptTouchEvent(touches: Touches): Boolean {
        return false
    }

    actual open fun onTouchesBegan(touches: Touches) {
//        println("$this touchesBegan")
    }

    actual open fun onTouchesMoved(touches: Touches) {
//        println("$this touchesMoved")
    }

    actual open fun onTouchesEnded(touches: Touches) {
//        println("$this touchesEnded")
    }

    actual open fun onTouchesCancelled(touches: Touches) {
//        println("$this touchesCancelled")
    }

    actual open fun setShadow(color: Int,radius: Float, dx: Float, dy: Float) {
        val background = getOrCreateBackground()
        background.setShadow(radius,dx, dy, color)
        this.checkLayerType()
        if(background.haveShadow()){
            val radius = background.shadowRadius
            val dx= background.shadowDx
            val dy= background.shadowDy
            val l = min(dx - radius,0f)
            val t = min(dy - radius,0f)
            val r = max(0f,dx + radius)
            val b = max(0f,dy + radius)
            background.offset = Frame(l,t,0f,0f)
            innerPadding = EdgeInsets(l,t,r,b)
        }else{
            background.offset = null
            innerPadding = null
        }
    }

    actual open fun getBackgroundColor(): Int {
       return mBackgroundColor
    }

    actual fun getLeftBorderWidth(): Float {
        return mBackground?.borderLeftWidth ?: 0f
    }

    actual fun getTopBorderWidth(): Float {
        return mBackground?.borderTopWidth ?: 0f
    }

    actual fun getRightBorderWidth(): Float {
        return mBackground?.borderRightWidth ?: 0f
    }

    actual fun getBottomBorderWidth(): Float {
        return mBackground?.borderBottomWidth ?: 0f
    }

    actual fun getTopLeftBorderRadius():Float{
        return mBackground?.borderTopLeftRadius ?: 0f
    }

    actual fun getTopRightBorderRadius():Float{
        return mBackground?.borderTopRightRadius ?: 0f
    }

    actual fun getBottomLeftBorderRadius():Float{
        return mBackground?.borderBottomLeftRadius ?: 0f
    }

    actual fun getBottomRightBorderRadius():Float{
        return mBackground?.borderBottomRightRadius ?: 0f
    }

    actual fun getShadowColor(): Int {
        return mBackground?.shadowColor ?: 0
    }

    actual fun getShadowOffsetX(): Float {
        return mBackground?.shadowDx ?: 0f
    }

    actual fun getShadowOffsetY(): Float {
        return mBackground?.shadowDy ?: 0f
    }

    actual fun getShadowRadius(): Float {
        return mBackground?.shadowRadius ?: 0f
    }

    actual open fun needLayout(){
        mWidget.requestLayout()
    }

    actual open fun needDisplay(){
        mWidget.invalidate()
    }

}