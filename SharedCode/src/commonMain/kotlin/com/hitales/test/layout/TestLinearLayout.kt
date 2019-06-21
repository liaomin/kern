package com.hitales.test.layout

import com.hitales.test.TestViewController
import com.hitales.ui.*
import com.hitales.utils.EdgeInsets
import com.hitales.utils.Frame
import com.hitales.utils.Timer

class TestLinearLayout : TestViewController(){

    var timer:Timer? = null

    override fun testView() {
        val linearViewGroup = LinearViewGroup(Frame(0f,80f,Platform.windowWidth,Platform.windowHeight - 80f))

        var text = TextView("测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字")
        text.setBackgroundColor(Colors.BLUE)
        linearViewGroup.addView(text)

        text = TextView("margin{top:10} 测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字")
        text.margin = EdgeInsets(10f)
        text.setBackgroundColor(Colors.BLUE)
        linearViewGroup.addView(text)

        text = TextView("margin{top:10,left:20} 测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字")
        text.margin = EdgeInsets(10f,left = 20f)
        text.setBackgroundColor(Colors.BLUE)
        linearViewGroup.addView(text)

        text = TextView("margin{top:10,left:20,right:10} 测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字")
        text.margin = EdgeInsets(10f,left = 20f,right = 20f)
        text.setBackgroundColor(Colors.BLUE)
        linearViewGroup.addView(text)

        text = TextView("margin{top:10,left:20,right:10,bottom:20} 测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字")
        text.margin = EdgeInsets(10f,left = 20f,right = 20f,bottom = 20f)
        text.setBackgroundColor(Colors.BLUE)
        linearViewGroup.addView(text)

        val image1 =  Image.named("1.jpg")
        val image2 =  Image.named("2.jpg")
        var image = ImageView()
        image.image = image1
        linearViewGroup.addView(image)

        image = ImageView()
        image.margin = EdgeInsets(10f,left = 20f,right = 20f)
        image.image = image2
        linearViewGroup.addView(image)

        timer = Timer.schedule(1000,1){
            val frame = linearViewGroup.frame
            frame.width -= 1
            linearViewGroup.frame = frame
        }

        (view as ViewGroup).addView(linearViewGroup)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancle()
    }

}