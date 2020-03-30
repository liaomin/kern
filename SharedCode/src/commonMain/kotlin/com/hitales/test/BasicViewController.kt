package com.hitales.test

import com.hitales.ui.Layout
import com.hitales.ui.ScrollView
import com.hitales.ui.View
import com.hitales.ui.ViewController

open class BasicViewController : ViewController() {

    override fun createLayout(): Layout {
        val scrollView = ScrollView()
//        scrollView.padding = EdgeInsets.value(10f)
//        scrollView.flexDirection = FlexDirection.COLUMN
//        scrollView.flexWarp = FlexWarp.WARP
//        scrollView.justifyContent = JustifyContent.CENTER
//        scrollView.alignItems = AlignItems.CENTER
//        scrollView.setBackgroundColor(Colors.YELLOW)
        return scrollView
    }

    fun addView(view: View){
        this.view?.addSubView(view)
    }
}