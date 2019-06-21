package com.hitales.test.layout

import com.hitales.test.TestViewController
import com.hitales.ui.*
import com.hitales.ui.layout.LayoutManager
import com.hitales.ui.layout.LinearLayoutManager
import com.hitales.utils.EdgeInsets
import com.hitales.utils.Frame
import com.hitales.utils.Timer

class TestLinearLayout : TestViewController(){

    var timer:Timer? = null

    override fun onCreate() {
        view = FrameViewGroup( Frame(0f,0f,Platform.windowWidth,Platform.windowHeight - 30))
        view?.setBackgroundColor(Colors.WHITE)
        testView()
    }

    override fun testView() {

        val linearViewGroup = ScrollView(Frame(0f,80f,Platform.windowWidth,Platform.windowHeight - 80f))
        val linearLayoutManager= LinearLayoutManager()
        linearViewGroup.layoutManager = linearLayoutManager

        var button = Button("测试H", Frame(100f,20f, 60f,60f))
        button.setBackgroundColor(Colors.BLUE)
        button.setBorderRadius(5f)
        button.setOnPressListener {
            linearLayoutManager.orientation = Orientation.HORIZONTAL
            linearViewGroup.layout()
        }
        linearViewGroup.addView(button)

        button = Button("测试V", Frame(100f,20f, 60f,60f))
        button.setBackgroundColor(Colors.BLUE)
        button.setBorderRadius(5f)
        button.setOnPressListener {
            linearLayoutManager.orientation = Orientation.VERTICAL
            linearViewGroup.layout()
        }
        linearViewGroup.addView(button)

        button = Button("返回", Frame(100f,20f, 60f,60f))
        button.setBackgroundColor(Colors.BLUE)
        button.setBorderRadius(5f)
        button.setOnPressListener {
           pop()
        }
        linearViewGroup.addView(button)

        var text = TextView("测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字")
        text.setBackgroundColor(Colors.BLUE)
        text.margin = EdgeInsets(10f,left = 20f,right = 20f,bottom = 20f)
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

        text = TextView("margin{top:10,left:20,right:10,bottom:20} 测试文字测试文字")
        text.lineHeight = 50f
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

        image = ImageView()
        image.image = image1
        linearViewGroup.addView(image)

        timer = Timer.schedule(1000,1){
//            val frame = linearViewGroup.frame
//            frame.width -= 1
//            linearViewGroup.frame = frame
        }

        (view as ViewGroup).addView(linearViewGroup)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancle()
    }

}