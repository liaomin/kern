package com.hitales.liam.ui

import com.hitales.liam.utils.NotificationCenter

class TestController : Controller() {

    override fun onCreate() {
        super.onCreate()
        val v  = TextView("测试")
//        v.setBackgroundColor(0xFFFF0000)
        view = v
        NotificationCenter.getInstance().addObserver("test",this::ob)
        NotificationCenter.getInstance().notify("test","hahhha")
        NotificationCenter.getInstance().removeObserver("test",this::ob)
        NotificationCenter.getInstance().notify("test","hahhha")


    }

    fun ob(key:Any,value: Any?){
        print("value:${value}")
    }

}