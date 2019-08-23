package com.hitales.test
import com.hitales.ui.Colors
import com.hitales.ui.Platform
import com.hitales.ui.TextInput
import com.hitales.utils.Timer

open class TestInputController : TestViewController(){

    override fun testView() {

        var width = Platform.windowWidth -20
        var view = TextInput("TAIL测试文字测试文字测试文字测试文测试文字测测试文字测试文字测试文字测试文测试文字测试测试文字测试文字测试文字测试文测试文字测试试")
        view.setBackgroundColor(0x660000ff.toInt())
        view.textSize = 16f
        view.autoFocus = true
        view.setBorderWidth(1f)
        view.setBorderColor(Colors.RED)
        view.setBorderRadius(10f)
//        view.ellipsizeMode = TextEllipsizeMode.TAIL
        var size = view.measureSize(width,0f)
        addView(view, 10f,Platform.windowWidth - 20, size.height)
        Timer.schedule(1000){
            view.focus()
        }


        var view2 = TextInput()
        view2.setBackgroundColor(0x660000ff.toInt())
        view2.placeholder = "输入吊袜带无多无"
        view2.textSize = 16f
        view2.autoFocus = true
        view2.setBorderWidth(1f)
        view2.setBorderColor(Colors.RED)
        view2.setBorderRadius(10f)
        addView(view2, 10f,Platform.windowWidth - 20, size.height)


        view = TextInput("TAIL测试文字测试文字测试文字测试文测试文字测测试文字测试文字测试文字测试文测试文字测试测试文字测试文字测试文字测试文测试文字测试试")
        view.setBackgroundColor(0x660000ff.toInt())
        view.textSize = 16f
        view.autoFocus = true
        view.setBorderWidth(1f)
        view.setBorderColor(Colors.RED)
        view.setBorderRadius(10f)
//        view.ellipsizeMode = TextEllipsizeMode.TAIL
        view.singleLine = false
        view.measureSize(width,0f,size)
        addView(view, 10f,Platform.windowWidth - 20, size.height)
        Timer.schedule(1000){
            view.focus()
        }
    }
}

