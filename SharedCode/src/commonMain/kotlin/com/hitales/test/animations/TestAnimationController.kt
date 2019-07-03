package com.hitales.test
import com.hitales.ui.*
import com.hitales.ui.animation.EaseOutInterpolator
import com.hitales.utils.Frame

open class TestAnimationController :TestViewController(), Animation.AnimationDelegate {

    override fun onAnimationStart(animation: Animation,view: View?) {
        println("onAnimationStart")
    }

    override fun onAnimationStop(animation: Animation,view: View?) {
        println("onAnimationFinish")
    }

    override fun testView() {

        val button = Button("测试插值器", Frame(70f,00f,60f,48f))
        button.setBackgroundColor(Colors.RED)
        button.setOnPressListener {
            val animation = Animation()
            animation.setFromTranslate(Platform.windowWidth,0f)
            animation.setToTranslate(0f,0f)
            animation.duration = 500f
            animation.interpolator = EaseOutInterpolator()
            this.push(TestAnimationInterpolatorController(),animation)
        }
        (this.view as ScrollView).addView(button)

        var image1 = Image.named("1.jpg")!!

        var index = 0


        var view = ImageView()
        view.setBackgroundColor(Colors.BLUE)
        view.image = image1
        val itemWidth = Platform.windowWidth / 2 - 20
        val itemHeight = itemWidth + 20
        view.frame = Frame(100f,50f,itemWidth,itemHeight)
        view.setBorderRadius(5f)
        view.resizeMode = ImageResizeMode.SCALE_FIT
        (this.view as ScrollView).addView(view)
        offsetY += view.frame.height + 30

//        offsetY = 250f

        addButton("tY"){
            val animation = Animation()
            animation.setToTranslate(0f,100f)
            animation.duration = 3000f
            animation.delegate = this
            view.startAnimation(animation)
        }

        addButton("translate"){
            val animation = Animation()
            animation.setToTranslate(100f,100f)
            animation.duration = 3000f
            animation.delegate = this
            view.startAnimation(animation)
        }


        addButton("x"){
            val animation = Animation()
            animation.setToRotate(360f,0f,0f)
            animation.duration = 3000f
            animation.delegate = this
            view.startAnimation(animation)
        }

        addButton("y"){
            val animation = Animation()
            animation.setToRotate(0f,360f,0f)
            animation.duration = 3000f
            animation.delegate = this
            view.startAnimation(animation)
        }

        addButton("z"){
            val animation = Animation()
            animation.setToRotate(0f,0f,360f)
            animation.duration = 3000f
            animation.delegate = this
            view.startAnimation(animation)
        }

        addButton("缩放"){
            val animation = Animation()
            animation.setToScale(2f,2f)
            animation.duration = 3000f
            animation.delegate = this
            view.startAnimation(animation)
        }

        addButton("透明度"){
            val animation = Animation()
            animation.fromOpacity = 1f
            animation.toOpacity = 0F
            animation.duration = 3000f
            animation.delegate = this
            view.startAnimation(animation)
        }

        addButton("组合测试1"){
            val animation = Animation()
            animation.setToRotate(360f,360f,360f)
            animation.fromOpacity = 1f
            animation.toOpacity = 0f
            animation.duration = 3000f
            animation.delegate = this
            view.startAnimation(animation)
        }

        addButton("组合测试2"){
            val animation = Animation()
            animation.setToTranslate(100f,100f)
            animation.fromOpacity = 1f
            animation.toOpacity = 0f
            animation.duration = 3000f
            animation.delegate = this
            view.startAnimation(animation)
        }

        addButton("组合测试2"){
            val animation = Animation()
            animation.setToRotate(360f,360f,360f)
            animation.setToTranslate(100f,100f)
            animation.setToScale(2f,2f)
            animation.fromOpacity = 1f
            animation.toOpacity = 0f
            animation.duration = 3000f
            animation.delegate = this
            view.startAnimation(animation)
        }

    }
}

