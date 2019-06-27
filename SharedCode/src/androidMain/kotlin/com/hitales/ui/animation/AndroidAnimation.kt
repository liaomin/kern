package com.hitales.ui.animation

import android.graphics.Camera
import android.view.animation.AlphaAnimation
import android.view.animation.Transformation
import androidx.core.view.animation.PathInterpolatorCompat
import com.hitales.ui.Animation
import com.hitales.ui.utils.PixelUtil


open class AndroidAnimation : AlphaAnimation(1f,1f){

    companion object {
        fun fromAnimation(animation: Animation):AndroidAnimation{
            val androidAnimation = AndroidAnimation()
            androidAnimation.setFromRotate(animation.fromRotateX,animation.fromRotateY,animation.fromRotateZ)
            androidAnimation.setToRotate(animation.toRotateX,animation.toRotateY,animation.toRotateZ)
            androidAnimation.setFromTranslate(animation.fromTranslateX,animation.fromTranslateY,animation.fromTranslateZ)
            androidAnimation.setToTranslate(animation.toTranslateX,animation.toTranslateY,animation.toTranslateZ)
            androidAnimation.setFromScale(animation.fromScaleX,animation.fromScaleY)
            androidAnimation.setToScale(animation.toScaleX,animation.toScaleY)
            androidAnimation.fromOpacity = animation.fromOpacity
            androidAnimation.toOpacity = animation.toOpacity
            androidAnimation.fillAfter = animation.fillAfter
            androidAnimation.duration = animation.duration.toLong()
            androidAnimation.repeatCount = animation.repeatCount
            if(animation.autoreverses){
                androidAnimation.repeatMode = REVERSE
            }else{
                androidAnimation.repeatMode = RESTART
            }
            val i = animation.interpolator
            androidAnimation.interpolator = PathInterpolatorCompat.create(i.x1,i.y1,i.x2,i.y2)
            return androidAnimation
        }
    }

    val camera:Camera by lazy { Camera() }

    var centerX = 0f

    var centerY = 0f

    /**
     * 透明度，取值范围[0,1]
     */
    var fromOpacity:Float = 1f

    /**
     * 透明度，取值范围[0,1]
     */
    var toOpacity:Float = 1f

    /**
     * x方向的位移
     */
    var fromTranslateX = 0f
        set(value) {
            field = PixelUtil.toPixelFromDIP(value)
        }

    /**
     * x方向的位移
     */
    var toTranslateX = 0f
        set(value) {
            field = PixelUtil.toPixelFromDIP(value)
        }

    /**
     * y方向的位移
     */
    var fromTranslateY = 0f
        set(value) {
            field = PixelUtil.toPixelFromDIP(value)
        }

    /**
     * y方向的位移
     */
    var toTranslateY = 0f
        set(value) {
            field = PixelUtil.toPixelFromDIP(value)
        }

    /**
     * z方向的位移
     */
    var fromTranslateZ = 0f
        set(value) {
            field = PixelUtil.toPixelFromDIP(value)
        }

    /**
     * z方向的位移
     */
    var toTranslateZ = 0f
        set(value) {
            field = PixelUtil.toPixelFromDIP(value)
        }

    /**
     * x方向的旋转度数 0~360
     */
    var fromRotateX = 0f

    /**
     * x方向的旋转度数 0~360
     */
    var toRotateX = 0f

    /**
     * y方向的旋转度数 0~360
     */
    var fromRotateY = 0f

    /**
     * y方向的旋转度数 0~360
     */
    var toRotateY = 0f

    /**
     * z方向的旋转度数 0~360
     */
    var fromRotateZ = 0f

    /**
     * z方向的旋转度数 0~360
     */
    var toRotateZ = 0f

    /**
     * x方向的缩放
     */
    var fromScaleX = 1f

    /**
     * x方向的缩放
     */
    var toScaleX= 1f

    /**
     * y方向的缩放
     */
    var fromScaleY = 0f

    /**
     * y方向的缩放
     */
    var toScaleY = 0f


    fun setFromTranslate(x:Float,y:Float,z:Float){
        fromTranslateX = x
        fromTranslateY = y
        fromTranslateZ = z
    }

    fun setToTranslate(x:Float,y:Float,z:Float){
        toTranslateX = x
        toTranslateY = y
        toTranslateZ = z
    }

    fun setFromRotate(x:Float,y:Float,z:Float){
        fromRotateX = x
        fromRotateY = y
        fromRotateZ = z
    }

    fun setToRotate(x:Float,y:Float,z:Float){
        toRotateX = x
        toRotateY = y
        toRotateZ = z
    }

    fun setFromScale(x:Float = 1f,y:Float = 1f){
        fromScaleX = x
        fromScaleY = y
    }

    fun setToScale(x:Float = 1f,y:Float = 1f){
        toScaleX = x
        toScaleY = y
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        val matrix = t.matrix
        camera.save()
        camera.translate(fromTranslateX + (toTranslateX - fromTranslateX)*interpolatedTime,-(fromTranslateY + (toTranslateY - fromTranslateY))*interpolatedTime,-(fromTranslateZ + (toTranslateZ - fromTranslateZ))*interpolatedTime)
        camera.rotateX( fromRotateX + (toRotateX - fromRotateX)*interpolatedTime)
        camera.rotateY(fromRotateY + (toRotateY - fromRotateY)*interpolatedTime)
        camera.rotateZ(-(fromRotateZ + (toRotateZ - fromRotateZ)*interpolatedTime))
        camera.getMatrix(matrix)
        matrix.preTranslate(-centerX,-centerY)
        matrix.postScale(fromScaleX+(toScaleX-fromScaleX)*interpolatedTime,fromScaleY+(toScaleY-fromScaleY)*interpolatedTime)
        matrix.postTranslate(centerX,centerY)
        camera.restore()
        t.alpha = fromOpacity + (toOpacity - fromOpacity) * interpolatedTime
    }

    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
        centerX = width / 2f
        centerY = height / 2f
    }


    override fun willChangeTransformationMatrix(): Boolean {
        return true
    }

    override fun willChangeBounds(): Boolean {
        return true
    }
}