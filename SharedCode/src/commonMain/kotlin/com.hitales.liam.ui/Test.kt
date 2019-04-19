package com.hitales.liam.ui

import com.hitales.liam.utils.Frame
import com.hitales.liam.utils.NotificationCenter

class TestController : Controller() {

    override fun onCreate() {
        super.onCreate()
        val rootView = FrameViewGroup()
        rootView.setBackgroundColor(0xFFFF0000)
        val v  = TextView("测试", Frame(60f,60f,100+60f,111+60f))
        rootView.addView(v)
        v.setBackgroundColor(0xFFFFFF00)
        view = rootView
        NotificationCenter.getInstance().addObserver("test",this::ob)
        NotificationCenter.getInstance().notify("test","hahhha")
        NotificationCenter.getInstance().removeObserver("test",this::ob)
        NotificationCenter.getInstance().notify("test","hahhha")
    }

    fun ob(key:Any,value: Any?){
        print("value:${value}")
    }

}