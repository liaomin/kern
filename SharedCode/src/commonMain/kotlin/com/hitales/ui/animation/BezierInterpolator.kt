package com.hitales.ui.animation

/**
 * 贝塞尔插值器
 * [(0,0),(x1,y1),(x2,y2),(1,1)]
 * 自定义可以参照 http://aashishdhawan.github.io/technical/media-timing-function/
 */
open class BezierInterpolator(val x1:Float,val y1:Float,val x2: Float,val y2:Float){
}

/**
 * 匀速
 */
class LinearInterpolator:BezierInterpolator(0f,0f,1f,1f)

/**
 * 先慢后快
 */
class EaseInInterpolator:BezierInterpolator(0.42f,0f,1f,1f)

/**
 * 先快后慢
 */
class EaseOutInterpolator:BezierInterpolator(0f,0f,0.58f,1f)

/**
 * 中间快
 */
class EaseInOutInterpolator:BezierInterpolator(0.42f,0f,0.58f,1f)
