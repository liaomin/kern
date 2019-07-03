package com.hitales.test
import com.hitales.ui.*
import com.hitales.ui.animation.*
import com.hitales.utils.Frame

open class TestAnimationInterpolatorController :TestViewController(), Animation.AnimationDelegate {

    override fun onAnimationStart(animation: Animation, view: View?) {
        println("onAnimationStart")
    }

    override fun onAnimationStop(animation: Animation, view: View?) {
        println("onAnimationFinish")
    }

    override fun testView() {

        var view = Button("EaseInOut")
        view.textSize = 8f
        view.setBackgroundColor(Colors.BLUE)
        view.frame = Frame(0f,50f,50f,50f)
        view.setBorderRadius(25f)
        (this.view as ScrollView).addView(view)
        offsetY = view.frame.y + view.frame.height

        var linear = Button("linear")
        linear.textSize = 8f
        linear.setBackgroundColor(Colors.BLUE)
        linear.frame = Frame(0f,offsetY,50f,50f)
        linear.setBorderRadius(25f)
        (this.view as ScrollView).addView(linear)
        offsetY += linear.frame.height

        var EaseIn = Button("EaseIn")
        EaseIn.textSize = 8f
        EaseIn.setBackgroundColor(Colors.BLUE)
        EaseIn.frame = Frame(0f,offsetY,50f,50f)
        EaseIn.setBorderRadius(25f)
        (this.view as ScrollView).addView(EaseIn)
        offsetY += EaseIn.frame.height

        var EaseOut = Button("EaseOut")
        EaseOut.textSize = 8f
        EaseOut.setBackgroundColor(Colors.BLUE)
        EaseOut.frame = Frame(0f,offsetY,50f,50f)
        EaseOut.setBorderRadius(25f)
        (this.view as ScrollView).addView(EaseOut)
        offsetY += EaseOut.frame.height

        var custom = Button("custom")
        custom.textSize = 8f
        custom.setBackgroundColor(Colors.BLUE)
        custom.frame = Frame(0f,offsetY,50f,50f)
        custom.setBorderRadius(25f)
        (this.view as ScrollView).addView(custom)
        offsetY += custom.frame.height


        offsetY += 30
        addButton("开始"){
            var animation = Animation()
            animation.setToTranslate(Platform.windowWidth - 50f,0f)
            animation.duration = 2000f
            animation.delegate = this
            animation.interpolator = EaseInOutInterpolator()
            view.startAnimation(animation)

            animation = Animation()
            animation.setToTranslate(Platform.windowWidth - 50f,0f)
            animation.duration = 2000f
            animation.delegate = this
            animation.interpolator = LinearInterpolator()
            linear.startAnimation(animation)

            animation = Animation()
            animation.setToTranslate(Platform.windowWidth - 50f,0f)
            animation.duration = 2000f
            animation.delegate = this
            animation.interpolator = EaseInInterpolator()
            EaseIn.startAnimation(animation)

            animation = Animation()
            animation.setToTranslate(Platform.windowWidth - 50f,0f)
            animation.duration = 2000f
            animation.delegate = this
            animation.interpolator = EaseOutInterpolator()
            EaseOut.startAnimation(animation)

            animation = Animation()
            animation.setToTranslate(Platform.windowWidth - 50f,0f)
            animation.duration = 2000f
            animation.delegate = this
            animation.interpolator = BezierInterpolator(0.785f, 0.135f, 0.15f, 0.86f)
            custom.startAnimation(animation)
        }


    }
}

