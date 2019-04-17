package com.hitales.liam.ui

class TestController : Controller() {

    override fun onCreate() {
        super.onCreate()
        val v  = TextView("测试")
        v.setBackgroundColor(0xFFFF0000)
        view = v
    }
}