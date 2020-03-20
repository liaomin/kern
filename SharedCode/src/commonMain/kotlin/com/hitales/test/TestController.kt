package com.hitales.test

import com.hitales.ui.*
import com.hitales.ui.layout.flex.AlignItems
import com.hitales.ui.layout.flex.FlexDirection
import com.hitales.ui.layout.flex.FlexLayout
import com.hitales.ui.layout.flex.FlexLayoutParams
import com.hitales.utils.EdgeInsets
import com.hitales.utils.random


open class TestController : Controller() {

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

        v.flexDirection = FlexDirection.ROW_REVERSE
//        v.flexWarp = FlexWarp.WARP
//        v.justifyContent = JustifyContent.FLEX_END
        v.alignItems = AlignItems.CENTER


//        val lp2 = FlexLayoutParams()
//        val v2 = FlexLayout(lp2)
//        v2.padding = EdgeInsets.value(60f)
//        lp.maxWidth = 100f
//        lp.maxHeight = 100f
//        v2.addSubView(v)

        return v
    }

}