package com.hitales.test

import com.hitales.ui.Layout
import com.hitales.ui.ScrollView
import com.hitales.ui.View
import com.hitales.ui.ViewController
import com.hitales.ui.layout.flex.FlexDirection

open class BasicViewController : ViewController() {

    override fun createLayout(): Layout {
//        return Layout()
        val scrollView = ScrollView()
//        scrollView.padding = EdgeInsets.value(150f)

        scrollView.flexDirection = FlexDirection.ROW
//        scrollView.alignItems = AlignItems.CENTER
//        scrollView.showScrollBar = false
//        scrollView.setBackgroundColor(Colors.YELLOW)
        return scrollView
    }

    fun addView(view: View){
        this.view?.addSubView(view)
    }
}