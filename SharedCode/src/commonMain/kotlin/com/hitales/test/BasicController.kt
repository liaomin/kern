package com.hitales.test

import com.hitales.ui.Controller
import com.hitales.ui.Layout
import com.hitales.ui.ScrollView
import com.hitales.ui.View
import com.hitales.ui.layout.flex.AlignItems
import com.hitales.ui.layout.flex.FlexDirection
import com.hitales.ui.layout.flex.JustifyContent
import com.hitales.utils.EdgeInsets

open class BasicController : Controller() {

    override fun createLayout(): Layout {
        val scrollView = ScrollView()
        scrollView.padding = EdgeInsets.value(10f)
        scrollView.flexDirection = FlexDirection.COLUMN
//        scrollView.flexWarp = FlexWarp.WARP
        scrollView.justifyContent = JustifyContent.CENTER
        scrollView.alignItems = AlignItems.CENTER
//        scrollView.setBackgroundColor(Colors.YELLOW)
        return scrollView
    }

    fun addView(view: View){
        this.view.addSubView(view)
    }
}