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
        var view = TextView("测试")
        view.textColor = Colors.RED
        addView(view,"测试颜色",getIndex())


        view = TextView("测试")
        view.textColor = Colors.RED
        view.textSize = 50f
        addView(view,"测试颜色",getIndex())


        view = TextView("测试")
        view.textColor = Colors.RED
        view.textSize = 50f
        view.bold = true
        addView(view,"测试颜色",getIndex())


        view = TextView("测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试")
        view.textColor = Colors.RED
        view.textSize = 50f
        view.bold = true
        addView(view,"测试颜色",getIndex())

        view = TextView("测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试")
        view.textColor = Colors.RED
        view.textSize = 20f
        view.bold = true
        addView(view,"测试颜色",getIndex())

        view = TextView("测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试")
        view.textColor = Colors.RED
        view.textSize = 20f
        view.bold = true
        view.lineHeight = 20f
        addView(view,"测试颜色",getIndex())
    }
}