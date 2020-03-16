package com.hitales.test

import com.hitales.ui.*
import com.hitales.ui.layout.flex.FlexDirection
import com.hitales.ui.layout.flex.FlexLayout
import com.hitales.ui.layout.flex.FlexLayoutParams
import com.hitales.utils.EdgeInsets
import com.hitales.utils.random


open class TestController : Controller() {

    override fun createLayout():Layout {
        val v = FlexLayout()
        v.padding = EdgeInsets.value(10f)

//        val view = View()
//        view.layoutParams = LayoutParams()
//        view.layoutParams.width = 100f
//        view.layoutParams.height = 100f
//        view.setBackgroundColor(Colors.BLUE)
//        view.setShadow(Colors.GREEN,20f,-5f,-5f)
//        view.setBorderRadius(50f)
//        v.addSubView(view)
//        view.setOnPressListener {
//            println("1")
//        }
//
//        val text = TextView("这是文本")
//        text.setBackgroundColor(Colors.RED)
//        text.padding = EdgeInsets(10f,20f,30f,40f)
//        text.setShadow(Colors.GREEN,20f,30f,30f)
//        v.addSubView(text)
//        text.setTextShadow(Colors.BLUE,4f,4f,3f)
//        text.setOnPressListener {
//            println("1")
//        }
//
//        for (i in 0 until 100){
//            val l = FlexLayoutParams()
//            l.margin = EdgeInsets.value(10f)
//            val t = TextView("文本  ${i}")
//            t.layoutParams = l
//            t.padding = EdgeInsets.value(10f)
//
//            t.setBackgroundColor(0xff000000.toInt().or((0xffffff * random()).toInt()))
//            v.addSubView(t)
//            t.setOnPressListener {
//                println("i:$i")
//            }
//        }
//
//        v.padding = EdgeInsets.value(30f)

        v.flexDirection = FlexDirection.COLUMN

        val l = FlexLayoutParams()
        l.flex = 2f
//        l.height = 100f
        l.margin = EdgeInsets.value(10f)
        for ( i in 0 until 10){
            var view = View(l)
            view.setBackgroundColor(0xff000000.toInt().or((0xffffff * random()).toInt()))
            v.addSubView(view)
        }

        val text = TextView("文本")
        val lt = FlexLayoutParams()
        lt.minHeight = 50f
        lt.minWidth = 100f
        lt.margin = EdgeInsets.value(20f)
        text.layoutParams = lt
        text.setBackgroundColor(Colors.BLUE)
        v.addSubView(text)


//        v.flexWarp = FlexWarp.WARP
//        v.justifyContent = JustifyContent.SPACE_AROUND

        return v
    }

}