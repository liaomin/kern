package com.hitales.test
import com.hitales.ui.*
import com.hitales.utils.Timer

open class TestInputController : TestViewController(){

    override fun testView() {

        var width = Platform.windowWidth -20
        var view = TextInput("TAIL测试文字测试文字测试文字测试文测试文字测测试文字测试文字测试文字测试文测试文字测试测试文字测试文字测试文字测试文测试文字测试试")
        view.setBackgroundColor(Colors.BLUE)
        view.textSize = 16f
        view.autoFocus = true
//        view.ellipsizeMode = TextEllipsizeMode.TAIL
        var size = view.measureSize(width,0f)
        addView(view, 10f,Platform.windowWidth - 20, size.height)
        Timer.schedule(1000){
            view.focus()
        }

    }
}

