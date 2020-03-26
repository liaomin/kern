package com.hitales.test.back

import com.hitales.ui.*
import com.hitales.ui.layout.flex.*
import com.hitales.utils.EdgeInsets
import com.hitales.utils.random


open class TestViewController : ViewController() {

    override fun createLayout():Layout {

        val lp = FlexLayoutParams()
        val v = FlexLayout(lp)

        val view = View()
        view.layoutParams = LayoutParams()
        view.layoutParams.width = 100f
        view.layoutParams.height = 100f
        view.setBackgroundColor(Colors.BLUE)
        view.setShadow(Colors.GREEN,20f,-5f,-5f)
        view.setBorderRadius(50f)
        v.addSubView(view)
        view.setOnPressListener {
            println("1")
        }

        val vL = FlexLayoutParams()
        vL.left = 00f
        vL.top = 00f
        vL.height = 140f
//        vL.width = 200f
        vL.flex =1f
        vL.maxWidth =20f
        vL.margin = EdgeInsets.value(10f)
//        vL.position = LayoutPosition.ABSOLUTE
        val text = TextView("这是文本",vL)
        text.setBackgroundColor(Colors.RED)
//        text.padding = EdgeInsets(10f,20f,30f,40f)
        //TODO 测试shadow
//        text.setShadow(Colors.GREEN,20f,30f,30f)
        v.addSubView(text)
//        text.setTextShadow(Colors.BLUE,4f,4f,3f)
        text.setOnPressListener {
            println("1")
        }

        for (i in 0 until 2){
            val l = FlexLayoutParams()
//            l.margin = EdgeInsets.value(10f)
            val t = TextView("文本  ${i}")
            t.lineHeight = 50f
            t.layoutParams = l
            t.padding = EdgeInsets.value(10f)

            t.setBackgroundColor(0xff000000.toInt().or((0xffffff * random()).toInt()))
            v.addSubView(t)
            t.setOnPressListener {
                println("i:$i")
            }
        }

//        v.padding = EdgeInsets.value(30f)

        v.flexDirection = FlexDirection.ROW
//        v.flexWarp = FlexWarp.WARP
//        v.justifyContent = JustifyContent.CENTER
        v.alignItems = AlignItems.CENTER


//        v.padding = EdgeInsets.value(10f)
//        return v


        val lex = FlexLayoutParams()
//        lex.height = Platform.windowHeight / 2f

        val flexLayout = ScrollView(lex)
        flexLayout.flexDirection = FlexDirection.ROW
        flexLayout.flexWarp = FlexWarp.WARP
        flexLayout.justifyContent = JustifyContent.CENTER
//        flexLayout.alignItems = AlignItems.STRETCH
        var tp = FlexLayoutParams()
        val width = 20f
        tp.width = width
        tp.height = width
        val text1 = TextView("参数：int autoSizeMinTextSize,",tp)
        text1.setBackgroundColor(Colors.BLUE)
        flexLayout.addSubView(text1)
//        text1.numberOfLines = 1
        text1.enableAutoFontSizeToFit(1,100)
//        text1.disableAutoFontSizeToFit()

        val tp2 = FlexLayoutParams()
        tp2.width = 200f
        val slider = Slider(tp2)
        slider.max = 100
        slider.progress = 0
        flexLayout.addSubView(slider)
        slider.onValueChangeListener = {slider:Slider,value:Int,max:Int ->
            val l = width * (value / 100f * 10 + 1)
            tp.width = l
            tp.height = l
            text1.needLayout()
        }



        for (i in 0 until 200){
            val l = FlexLayoutParams()
//            l.margin = EdgeInsets.value(10f)
            val t = TextView("文本  ${i}")
            t.lineHeight = 50f
            t.layoutParams = l
            t.padding = EdgeInsets.value(10f)
            l.margin = EdgeInsets.value(5f)
            t.setBackgroundColor(0xff000000.toInt().or((0xffffff * random()).toInt()))
            flexLayout.addSubView(t)
            t.setOnPressListener {
                println("i:$i")
            }
        }

        flexLayout.padding = EdgeInsets.value(20f)
//v.removeAllSubViews()
//        v.addSubView(flexLayout)
        return flexLayout

    }

}