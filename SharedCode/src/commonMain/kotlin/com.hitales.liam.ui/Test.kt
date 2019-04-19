package com.hitales.liam.ui

import com.hitales.liam.utils.Frame
import com.hitales.liam.utils.NotificationCenter

class TestController : Controller() {

    var v:TextView?  = null

    override fun onCreate() {
        super.onCreate()
        val rootView = FrameViewGroup()
//        rootView.setBackgroundColor(0xFFFF0000)
        val v  = Button("测试", Frame(60f,60f,100+60f,111+60f))
        rootView.addView(v)
        this.v = v
        val v2  = TextView("测试", Frame(60f,200f,100+60f,200+60f))
        rootView.addView(v2)
        v.setBackgroundColor(0xFFFFFF00)
        view = rootView
        v.onPressListener = {
            print(it)
        }
        v.onLongPressListener = {
            print(it)
        }
        NotificationCenter.getInstance().addObserver("update",this::ob)
//        NotificationCenter.getInstance().addObserver("test",this::ob)
//        NotificationCenter.getInstance().notify("test","hahhha")
//        NotificationCenter.getInstance().removeObserver("test",this::ob)
//        NotificationCenter.getInstance().notify("test","hahhha")
    }

    fun ob(key:Any,value: Any?){
        val frame = v!!.frame
        frame.x += 1
//        v?.frame = frame
    }

}