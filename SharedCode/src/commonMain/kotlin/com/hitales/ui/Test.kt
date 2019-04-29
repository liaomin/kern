package com.hitales.ui

import com.hitales.utils.Frame
import com.hitales.utils.NotificationCenter

class TestController : Controller() {

    var v:TextView?  = null

    override fun onCreate() {
        super.onCreate()
        val rootView = ScrollView(Frame(0f,0f,Platform.windowWidth -2,Platform.windowHeight-30))
        view = rootView
        rootView.setBackgroundColor(0xFF00FF00.toInt())
        val v  = Button("测试", Frame(0f,0f,100f,100f))
        rootView.addView(v)
        v.setBackgroundColor(0xff000000.toInt())
//        this.v = v
////        val v4  = TextView("测试2", Frame(60f,260f,200f,200f))
////        rootView.addView(v4)
////        v4.textColor = 0xFFFF0000.toInt()
//        v.setBackgroundColor(0xFFFF0000.toInt())
////        v.setTextColor(0xFF00FF00.toInt(),ViewState.PRESSED)
//        v.setTextColor(0xFF00FF00.toInt())
//
//
//        v.onLongPressListener = {
//            println(it)
//        }
//
//        val imageView  = ImageView(Frame(0f,110f,200f,200f))
//        rootView.addView(imageView)
//        imageView.setBackgroundColor(0xFFFF0000.toInt())
//        imageView.image = Image.named("yst_image_care.png")
//
//        val input  = Input("dwdw",Frame(60f,320f,200f,200f))
//        rootView.addView(input)
//        input.autoFocus = false
//        input.placeholderText = "占位符"
//        input.placeholderTextColor = 0xFF000000.toInt()
//        input.setBackgroundColor(0xFFFF0000.toInt())
//        input.textColor = 0xFF0000FF.toInt()
//        input.setTextColor(0xFF00FF00.toInt(),ViewState.FOCUSED)
//
//        val input2  = Input("dwdw",Frame(60f,540f,200f,200f))
//        rootView.addView(input2)
//        input2.autoFocus = true
//        input2.placeholderText = "占位符"
//        input2.placeholderTextColor = 0xFF000000.toInt()
//        input2.setBackgroundColor(0xFFFF0000.toInt())
//        input2.textColor = 0xFF0000FF.toInt()
//        input2.setTextColor(0xFF00FF00.toInt(),ViewState.FOCUSED)
//
//
//        v.onPressListener = {
//            input.cleanFocus()
//        }
//
//        for (i in 0 .. 1000){
//            val text = TextView("${i}", Frame(0f,750+110f*i,Platform.windowWidth,100f))
//            text.setBackgroundColor( 0xff00ff00.toInt())
//            rootView.addView(text)
//        }
//
//        NotificationCenter.getInstance().addObserver("update",this::ob)
    }
//
//    fun ob(key:Any,value: Any?){
//        val frame = v!!.frame
//        frame.x += 1
////        v?.frame = frame
//    }

}