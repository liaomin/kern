package com.hitales.test

import com.hitales.ui.*
import com.hitales.utils.Frame

open class BasicController : Controller() {

    override fun onCreate() {
        view = ScrollView( Frame(0f,0f,Platform.windowWidth,Platform.windowHeight))
        view?.setBackgroundColor(Colors.WHITE)
        val backButton = Button("返回", Frame(0f,0f,80f,48f))
        backButton.setBackgroundColor(0xFFFF0000.toInt())
        backButton.setOnPressListener {
            this.pop()
        }
        addView(backButton)
    }


    fun addView(view:View){
        (this.view as ScrollView).addView(view)
    }


    override fun onPause() {
        super.onPause()
        println("${this::class} onPause")
    }

    override fun onResume() {
        super.onResume()
        println("${this::class} onResume")
    }


}