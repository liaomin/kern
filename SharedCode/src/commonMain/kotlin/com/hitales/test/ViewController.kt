package com.hitales.test

import com.hitales.ui.*
import com.hitales.utils.Frame

class ViewController : BasicController() {

    override fun onCreate() {
        super.onCreate()
        testView()
    }

    fun testView(){
        var view = View()
        view.setBackgroundColor(Colors.RED)
        addView(view,"测试颜色",getIndex(),Colors.RED)

        view = View()
        view.setBackgroundColor(Colors.BLUE)
        addView(view,"测试颜色",getIndex(),Colors.BLUE)

        view = View()
        view.setBackgroundColor(0xFF999999.toInt())
        addView(view,"测试颜色",getIndex(),0xFF999999.toInt())

        view = View()
        view.setBackgroundColor(0x33999999)
        addView(view,"测试颜色",getIndex(),0x33999999)
    }


    fun addView(view: View, title:String, index:Int){
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

    fun addView(view: View, title:String, index:Int,color:Int){
        val itemWidth = Platform.windowWidth / 2 - 20
        val itemHeight = itemWidth + 20
        val x =  (if(index % 2 == 0) 0f else Platform.windowWidth / 2)
        val y = (index/2) * (itemHeight+10) + 60

        val group = FrameViewGroup(Frame(x,y, Platform.windowWidth / 2,itemHeight+2))
        view.frame = Frame(10f,22f,itemWidth,itemWidth)
        group.addView(view)

        val titleWidget = getTilteWidget("$title",Frame(10f,0f,itemWidth,20f))
        titleWidget.textColor = color
        titleWidget.text = "$title ${color == titleWidget.textColor}"
        group.addView(titleWidget)

        (this.view as ScrollView).addView(group)
    }

    fun getTilteWidget(title:String,frame: Frame): TextView {
        val text = TextView(title,frame)
        return text
    }

    private var index:Int = 0

    fun getIndex():Int{
        return index++
    }
}