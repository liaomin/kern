package com.hitales.test.view

import com.hitales.test.BasicViewController
import com.hitales.ui.Button
import com.hitales.ui.Colors
import com.hitales.ui.ViewController
import com.hitales.ui.ViewState
import com.hitales.ui.layout.flex.FlexLayoutParams
import com.hitales.utils.EdgeInsets

class TestViewBorderController : BasicViewController(){

    override fun onCreate() {
        super.onCreate()
        val map = HashMap<String, ViewController>()
        map["view"] = ViewBorderTestController()
        map["text"] = TextBorderTestController()
        map["button"] = ButtonViewBorderTestController()
        map["input"] = InputViewBorderTestController()
        map["image"] = ImageViewBorderTestController()
        for (i in 0 until  1){
            map.forEach {
                val entry = it
                val lp = FlexLayoutParams()
                lp.margin = EdgeInsets.value(5f)
                val button = Button(it.key,lp)
                button.padding = EdgeInsets.value(10f)
                button.textSize = 25f
                button.setBackgroundColor(Colors.GREEN, ViewState.PRESSED)
                button.setOnPressListener {
                    this.push(entry.value)
                }
                addView(button)
            }
        }
    }

}