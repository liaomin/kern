package com.hitales.test

import com.hitales.ui.BorderStyle
import com.hitales.ui.Colors
import com.hitales.ui.Platform
import com.hitales.ui.TextInput
import com.hitales.ui.layout.flex.FlexLayoutParams

open class TestViewViewController : BasicViewController() {

    val itemWidth = Platform.windowWidth / 3f


    override fun onCreate() {
        var lp = FlexLayoutParams()
        lp.width = itemWidth
        lp.height = itemWidth
        var view = TextInput("",lp)
//        var view = View(lp)
        view.setBackgroundColor(Colors.RED)
//        view.setTextColor(Colors.BLUE)
//        view.bold = true
//        view.textSize = 30f
//        view.autoFocus = true
        view.setBorderRadius(itemWidth/2)
        view.setBorderWidth(10f)
        view.borderStyle = BorderStyle.DOTTED
        view.setBorderColor(Colors.BLUE)
        view.singleLine = false
        addView(view)
    }

}