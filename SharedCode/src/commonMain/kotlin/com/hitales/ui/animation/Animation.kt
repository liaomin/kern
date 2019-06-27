package com.hitales.ui

import com.hitales.ui.animation.BezierInterpolator
import com.hitales.ui.animation.LinearInterpolator


open class Animation {

    interface AnimationDelegate{

        fun onAnimationStart(animation: Animation)

        fun onAnimationFinish(animation: Animation)

    }

    /**
     * 插值器，默认LinearInterpolator
     */
    var interpolator:BezierInterpolator = LinearInterpolator()

    /**
     * 回调
     */
    var delegate:AnimationDelegate? = null

    /**
     * 报错结束后的位置
     */
    var fillAfter:Boolean = true

    /**
     * 重复次数
     */
    var repeatCount:Int = 0

    /**
     * 重复时是否逆向动画
     */
    var autoreverses:Boolean = false

    /**
     * 动画时间
     */
    var duration:Float = 0f

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

    /**
     * x方向的位移
     */
    var toTranslateX = 0f

    /**
     * y方向的位移
     */
    var fromTranslateY = 0f

    /**
     * y方向的位移
     */
    var toTranslateY = 0f

    /**
     * z方向的位移
     */
    var fromTranslateZ = 0f

    /**
     * z方向的位移
     */
    var toTranslateZ = 0f

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
     * ios上CATransform3D
     */
    var m34 = -1/500.0


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
    var fromScaleY = 1f

    /**
     * y方向的缩放
     */
    var toScaleY = 1f

    fun setFromTranslate(x:Float,y:Float,z:Float = 0f){
        fromTranslateX = x
        fromTranslateY = y
        fromTranslateZ = z
    }

    fun setToTranslate(x:Float,y:Float,z:Float = 0f){
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


}