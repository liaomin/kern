package com.hitales.test

import com.hitales.ui.*
import com.hitales.utils.Frame

open class ViewController : BasicController() {

    override fun onCreate() {
        super.onCreate()
        testView()
    }

    open fun testView(){
        var view = View()
        view.setBackgroundColor(Colors.RED)
        addView(view,"测试颜色",getIndex(),Colors.RED)
        view.setBorderColor(Colors.BLUE)










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
        titleWidget.text = "$title"
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

class BorderWidthTestController : ViewController(){

    override fun testView() {

        val bgColor = 0x33FF0000

        var view = View()
        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderWidth",getIndex(),Colors.BLACK)
        view.setBorderWidth(60f,2f,20f,5f)
        view.setBorderColor(Colors.BLUE,Colors.GREEN,Colors.YELLOW,Colors.ORANGE)
        view.setBorderRadius(10f)
//        view.setBorderRadius((Platform.windowWidth / 2 - 20) /2)
//        view.setBorderRadius(10f,80f,20f,0f)

        view = View()
        view.setBackgroundColor(bgColor)
        addView(view,"测试borderWidth",getIndex(),Colors.BLACK)
        view.setBorderWidth(60f,2f,20f,5f)
        view.setBorderColor(0x330000ff.toInt(),0x3300ff00.toInt(),0x33ffff00.toInt(),0x3300ff00)
        view.setBorderRadius(10f,20f,30f,40f)
        view.setBorderWidth(20f,5f,20f,5f)
        view.setBorderRadius(30f,40f,30f,40f)

//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderWidth",getIndex(),Colors.BLACK)
//        view.setBorderWidth(60f,1f,20f,10f)
//        view.setBorderColor(Colors.BLUE)
//
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderWidth",getIndex(),Colors.BLACK)
//        view.setBorderWidth(60f)
//        view.setBorderColor(Colors.BLUE,Colors.RED,Colors.YELLOW,Colors.TRANSPARENT)
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderWidth",getIndex(),Colors.BLACK)
//        view.setBorderWidth(60f,1f,20f,10f)
//        view.setBorderColor(Colors.BLUE,Colors.RED,Colors.YELLOW,Colors.TRANSPARENT)
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderWidth",getIndex(),Colors.BLACK)
//        view.setBorderWidth(60f,1f,20f,10f)
//        view.setBorderColor(Colors.BLUE,Colors.RED,Colors.YELLOW,Colors.ORANGE)
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderWidth",getIndex(),Colors.BLACK)
//        view.setBorderWidth(0f)
//        view.setBorderColor(Colors.BLUE,Colors.RED,Colors.YELLOW,Colors.ORANGE)
//
//
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderWidth",getIndex(),Colors.BLACK)
//        view.setBorderWidth(10f)
//        view.setBorderColor(Colors.BLUE)
//
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderWidth color",getIndex(),Colors.BLACK)
//        view.setBorderWidth(10f)
//        view.setBorderColor(Colors.GREEN)
//
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderWidth",getIndex(),Colors.BLACK)
//        view.setBorderWidth(3f)
//        view.setBorderRadius(20f)
//        view.setBorderColor(Colors.BLUE)
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderWidth color",getIndex(),Colors.BLACK)
//        view.setBorderWidth(3f)
//        view.setBorderRadius(20f)
//        view.setBorderColor(Colors.GREEN)
//
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderWidth",getIndex(),Colors.BLACK)
//        view.setBorderWidth(3f)
//        view.setBorderRadius(10f,20f,30f,40f)
//        view.setBorderColor(Colors.BLUE)
//
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderWidth",getIndex(),Colors.BLACK)
//        view.setBorderWidth(3f)
//        view.setBorderRadius(40f,10f,50f,0f)
//        view.setBorderColor(Colors.BLUE)
//
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderLeftWidth",getIndex(),Colors.BLACK)
//        view.setBorderWidth(10f,0f,0f,0f)
//        view.setBorderColor(Colors.BLUE)
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderLeftWidth color",getIndex(),Colors.BLACK)
//        view.setBorderWidth(10f,0f,0f,0f)
//        view.setBorderColor(Colors.GREEN,0,0,0)
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderRightWidth",getIndex(),Colors.BLACK)
//        view.setBorderWidth(0f,0f,10f,0f)
//        view.setBorderColor(Colors.BLUE)
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderRightWidth",getIndex(),Colors.BLACK)
//        view.setBorderWidth(0f,0f,10f,0f)
//        view.setBorderColor(0,0,Colors.GREEN,0)
//
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderTopWidth",getIndex(),Colors.BLACK)
//        view.setBorderWidth(0f,10f,0f,0f)
//        view.setBorderColor(Colors.BLUE)
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderTopWidth",getIndex(),Colors.BLACK)
//        view.setBorderWidth(0f,10f,0f,0f)
//        view.setBorderColor(0,Colors.GREEN,0,0)
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderBottomWidth",getIndex(),Colors.BLACK)
//        view.setBorderWidth(0f,0f,0f,10f)
//        view.setBorderColor(Colors.BLUE)
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderBottomWidth",getIndex(),Colors.BLACK)
//        view.setBorderWidth(0f,0f,0f,10f)
//        view.setBorderColor(0,0,0,Colors.GREEN)
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderTopBottomWidth",getIndex(),Colors.BLACK)
//        view.setBorderWidth(0f,10f,0f,10f)
//        view.setBorderColor(Colors.BLUE)
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderTopBottomWidth",getIndex(),Colors.BLACK)
//        view.setBorderWidth(0f,10f,0f,10f)
//        view.setBorderColor(0,Colors.GREEN,0,Colors.YELLOW)
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderWidth",getIndex(),Colors.BLACK)
//        view.setBorderWidth(10f)
//        view.setBorderColor(Colors.BLUE)
//
//        view = View()
//        view.setBackgroundColor(bgColor)
//        addView(view,"测试borderWidth color",getIndex(),Colors.BLACK)
//        view.setBorderWidth(10f)
//        view.setBorderColor(Colors.RED,Colors.YELLOW,Colors.GREEN,Colors.BLACK)
    }

}