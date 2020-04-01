package com.hitales.test.view

import com.hitales.test.BasicViewController
import com.hitales.ui.*
import com.hitales.ui.layout.flex.*
import com.hitales.utils.EdgeInsets

class ViewBorderTestController : BasicViewController(){

    override fun createLayout(): Layout {
        val scrollView = ScrollView()
        scrollView.flexDirection = FlexDirection.ROW
        scrollView.justifyContent = JustifyContent.CENTER
        scrollView.alignItems = AlignItems.CENTER
        scrollView.flexWarp = FlexWarp.WARP
        return scrollView
    }

    fun getItemWidth():Float{
        return (Screen.getInstance().window.width - 60f)/ 2f
    }

    override fun onCreate() {
        super.onCreate()
        val bgColor = 0x99FF0000.toInt()
//        val bgColor = Colors.RED
        val lp = FlexLayoutParams()
        lp.width = getItemWidth()
        lp.height = getItemWidth()
        lp.margin = EdgeInsets.value(10f)

        var view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(10f)
        view.setBorderColor(Colors.BLUE,Colors.GREEN,Colors.YELLOW,Colors.ORANGE)
        view.setBorderColor(Colors.BLUE)
        view.setShadow(Colors.BLUE,9f,5f,5f)
        view.setBorderRadius(getItemWidth())

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(10f)
        view.setBorderColor(Colors.BLUE,Colors.GREEN,Colors.YELLOW,Colors.ORANGE)
        view.setBorderRadius(getItemWidth())


        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(10f)
        view.setBorderColor(Colors.BLUE,Colors.GREEN,Colors.YELLOW,Colors.ORANGE)
        view.setBorderColor(Colors.BLUE)
        view.setBorderStyle(BorderStyle.DASHED)


        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(10f)
        view.setBorderColor(Colors.BLUE,Colors.GREEN,Colors.YELLOW,Colors.ORANGE)
        view.setBorderColor(Colors.BLUE)
        view.setBorderStyle(BorderStyle.DOTTED)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(10f,20F,30F,40F)
        view.setBorderRadius(10f,20F,30F,40F)
        view.setBorderColor(Colors.BLUE)
        view.setBorderStyle(BorderStyle.DOTTED)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(10f)
        view.setBorderColor(Colors.BLUE,Colors.GREEN,Colors.YELLOW,Colors.ORANGE)
        view.setBorderColor(Colors.BLUE)
        view.setBorderRadius(10f)
        view.setBorderStyle(BorderStyle.DOTTED)


        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(10f)
        view.setBorderColor(Colors.BLUE,Colors.GREEN,Colors.YELLOW,Colors.ORANGE)
        view.setBorderColor(Colors.BLUE)
        view.setBorderRadius(40f)
        view.setBorderStyle(BorderStyle.DOTTED)



        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(100f)
        view.setBorderColor(Colors.BLUE,Colors.GREEN,Colors.YELLOW,Colors.ORANGE)
        view.setBorderRadius(getItemWidth())


//        val bgColor = Colors.RED
        view = View(lp)
        view.setBackgroundColor(bgColor)
        view.setBackgroundColor(Colors.RED)
        addView(view)
        view.setBorderWidth(60f,2f,20f,5f)
        view.setBorderColor(Colors.BLUE,Colors.GREEN,Colors.YELLOW,Colors.ORANGE)
        view.setBorderColor(0x330000ff)
        view.setBorderWidth(20f,5f,5f,5f)
        view.setBorderRadius(30f,40f,20f,40f)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(60f,2f,20f,5f)
        view.setBorderColor(0x330000ff)
        view.setBorderWidth(20f,5f,5f,5f)
//        view.setBorderRadius(30f,40f,20f,40f)

        view = View(lp)
//        val c = bgColor.reverseAlpha()
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(60f,2f,20f,5f)
        view.setBorderColor(0x330000ff)
        view.setBorderWidth(20f,5f,5f,5f)
        view.setBorderRadius(30f,40f,20f,40f)


        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(60f,2f,20f,5f)
        view.setBorderColor(0x330000ff,Colors.GREEN,Colors.YELLOW,Colors.ORANGE)
        view.setBorderWidth(20f,5f,5f,5f)
        view.setBorderRadius(30f,40f,20f,40f)


        view = View(lp)
        view.setBackgroundColor(Colors.BLUE)
        addView(view)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(60f,2f,20f,5f)
        view.setBorderColor(0x330000ff)
        view.setBorderWidth(20f,5f,5f,5f)
        view.setBorderRadius(30f,40f,20f,40f)



        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(60f,2f,20f,5f)
        view.setBorderColor(Colors.BLUE,Colors.GREEN,Colors.YELLOW,Colors.ORANGE)
        view.setBorderWidth(20f,5f,5f,5f)
        view.setBorderRadius(30f,40f,20f,40f)
        view.setBorderRadius((Platform.windowWidth / 2 - 20) /2)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(60f,2f,20f,5f)
        view.setBorderColor(0x330000ff)
        view.setBorderWidth(20f,5f,5f,5f)
        view.setBorderRadius(30f,40f,20f,40f)
        view.setBorderRadius((Platform.windowWidth / 2 - 20) /2)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(60f,2f,20f,5f)
        view.setBorderColor(0x330000ff,Colors.GREEN,Colors.YELLOW,Colors.ORANGE)
        view.setBorderWidth(20f,5f,5f,5f)
        view.setBorderRadius(30f,40f,20f,40f)
        view.setBorderRadius((Platform.windowWidth / 2 - 20) /2)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(60f,2f,20f,5f)
        view.setBorderColor(0x330000ff.toInt(),0x3300ff00.toInt(),0x33ffff00.toInt(),0x3300ff00)
        view.setBorderRadius(10f,20f,30f,40f)


        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(60f,1f,20f,10f)
        view.setBorderColor(Colors.BLUE)


        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(60f)
        view.setBorderColor(Colors.BLUE,Colors.RED,Colors.YELLOW,Colors.TRANSPARENT)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(60f,1f,20f,10f)
        view.setBorderColor(Colors.BLUE,Colors.RED,Colors.YELLOW,Colors.TRANSPARENT)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(60f,1f,20f,10f)
        view.setBorderColor(Colors.BLUE,Colors.RED,Colors.YELLOW,Colors.ORANGE)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(0f)
        view.setBorderColor(Colors.BLUE,Colors.RED,Colors.YELLOW,Colors.ORANGE)



        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(10f)
        view.setBorderColor(Colors.BLUE)


        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(10f)
        view.setBorderColor(Colors.GREEN)


        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(3f)
        view.setBorderRadius(20f)
        view.setBorderColor(Colors.BLUE)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(3f)
        view.setBorderRadius(20f)
        view.setBorderColor(Colors.GREEN)


        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(3f)
        view.setBorderRadius(10f,20f,30f,40f)
        view.setBorderColor(Colors.BLUE)


        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(3f)
        view.setBorderRadius(40f,10f,50f,0f)
        view.setBorderColor(Colors.BLUE)


        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(10f,0f,0f,0f)
        view.setBorderColor(Colors.BLUE)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(10f,0f,0f,0f)
        view.setBorderColor(Colors.GREEN,0,0,0)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(0f,0f,10f,0f)
        view.setBorderColor(Colors.BLUE)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(0f,0f,10f,0f)
        view.setBorderColor(0,0,Colors.GREEN,0)


        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(0f,10f,0f,0f)
        view.setBorderColor(Colors.BLUE)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(0f,10f,0f,0f)
        view.setBorderColor(0,Colors.GREEN,0,0)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(0f,0f,0f,10f)
        view.setBorderColor(Colors.BLUE)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(0f,0f,0f,10f)
        view.setBorderColor(0,0,0,Colors.GREEN)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(0f,10f,0f,10f)
        view.setBorderColor(Colors.BLUE)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(0f,10f,0f,10f)
        view.setBorderColor(0,Colors.GREEN,0,Colors.YELLOW)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(10f)
        view.setBorderColor(Colors.BLUE)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(10f)
        view.setBorderColor(Colors.RED,Colors.YELLOW,Colors.GREEN,Colors.BLACK)

        view = View(lp)
        view.setBackgroundColor(bgColor)
        addView(view)
        view.setBorderWidth(10f)
        view.setBorderRadius(getItemWidth() /2)
    }
}