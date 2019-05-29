package com.hitales.test

import com.hitales.ui.*
import com.hitales.utils.Frame

class TestTextController :ViewController(){

    override fun addView(view: View, title: String, index: Int) {
        val itemWidth = Platform.windowWidth / 2 - 20
        val itemHeight = itemWidth + 20
        val x =  (if(index % 2 == 0) 0f else Platform.windowWidth / 2)
        val y = (index/2) * (itemHeight+10) + 60

        val group = FrameViewGroup(Frame(x,y, Platform.windowWidth / 2,itemHeight+2))
        view.frame = Frame(10f,22f,itemWidth,itemWidth)
        group.addView(view)

        val titleFrame = Frame(10f,0f,itemWidth,20f)
        group.addView(getTilteWidget(title,titleFrame))

        (this.view as ScrollView).addView(group)
    }

    override fun testView() {
        addTitleView("测试textSize")
        var viewHeight = 45f
        for (i in 0..18){
            val textSize = 9+i*2
            var view = TextView("测试文字textSize:$textSize")
            view.textSize = textSize.toFloat()
            view.setBackgroundColor(Colors.RED)
            view.alignment = TextAlignment.RIGHT
            addView(view,0f,viewHeight)
        }
        var view = TextView("测试文字textSize:49.2")
        view.textSize = 49.2f
        view.alignment = TextAlignment.CENTER
        addView(view,0f,viewHeight)
    }
}