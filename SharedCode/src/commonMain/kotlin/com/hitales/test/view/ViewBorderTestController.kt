package com.hitales.test.view

import com.hitales.ui.LayoutParams
import com.hitales.ui.View


class ViewBorderTestController : BorderTestController<View>(){

    override fun getView(layoutParams: LayoutParams): View {
        return View(layoutParams)
    }

}