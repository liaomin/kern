package com.hitales.ui.animation

import com.hitales.ui.Animation
import com.hitales.utils.WeakReference
import platform.Foundation.NSNumber
import platform.Foundation.numberWithDouble
import platform.Foundation.numberWithFloat
import platform.QuartzCore.CAAnimationGroup
import platform.QuartzCore.CABasicAnimation
import platform.QuartzCore.CAMediaTimingFunction
import platform.QuartzCore.kCAFillModeForwards
import platform.posix.M_PI
import platform.posix.M_PI_2

fun transAnimation(animation:Animation):CAAnimationGroup{
    val animationGroup = CAAnimationGroup()
    val tx = CABasicAnimation.animationWithKeyPath("transform.translation.x")
    val ty = CABasicAnimation.animationWithKeyPath("transform.translation.y")
    val rx = CABasicAnimation.animationWithKeyPath("transform.rotation.x")
    val ry = CABasicAnimation.animationWithKeyPath("transform.rotation.y")
    val rz = CABasicAnimation.animationWithKeyPath("transform.rotation.z")
    val op = CABasicAnimation.animationWithKeyPath("opacity")
    val sx = CABasicAnimation.animationWithKeyPath("transform.scale.x")
    val sy = CABasicAnimation.animationWithKeyPath("transform.scale.y")

    sx.fromValue = NSNumber.numberWithFloat(animation.fromScaleX)
    sx.toValue = NSNumber.numberWithFloat(animation.toScaleX)

    sy.fromValue = NSNumber.numberWithFloat(animation.fromScaleY)
    sy.toValue = NSNumber.numberWithFloat(animation.toScaleY)


    op.fromValue = NSNumber.numberWithFloat(animation.fromOpacity)
    op.toValue = NSNumber.numberWithFloat(animation.toOpacity)

    tx.fromValue = NSNumber.numberWithFloat(animation.fromTranslateX)
    tx.toValue = NSNumber.numberWithFloat(animation.toTranslateX)

    ty.fromValue = NSNumber.numberWithFloat(animation.fromTranslateY)
    ty.toValue = NSNumber.numberWithFloat(animation.toTranslateY)

    rx.fromValue =  NSNumber.numberWithDouble(animation.fromRotateX/180f* M_PI)
    rx.toValue =  NSNumber.numberWithDouble(animation.toRotateX/180f* M_PI)

    ry.fromValue =  NSNumber.numberWithDouble(animation.fromRotateY/180f* M_PI)
    ry.toValue =  NSNumber.numberWithDouble(animation.toRotateY/180f* M_PI)

    rz.fromValue =  NSNumber.numberWithDouble(animation.fromRotateZ/180f* M_PI)
    rz.toValue =  NSNumber.numberWithDouble(animation.toRotateZ/180f* M_PI)

    animationGroup.setAnimations(arrayListOf(tx,ty,rx,ry,rz,op,sx,sy))
    animationGroup.setRemovedOnCompletion(false)
    if(animation.fillAfter){
        animationGroup.setFillMode(kCAFillModeForwards)
    }
    animationGroup.setAutoreverses(animation.autoreverses)
    animationGroup.setRepeatCount(animation.repeatCount.toFloat())
    animationGroup.setDuration(animation.duration/1000.0)
    val i = animation.interpolator
    animationGroup.setTimingFunction(CAMediaTimingFunction.functionWithControlPoints(i.x1,i.y1,i.x2,i.y2))
    return animationGroup
}