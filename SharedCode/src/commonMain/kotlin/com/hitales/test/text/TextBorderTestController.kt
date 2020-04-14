package com.hitales.test.view

import com.hitales.ui.LayoutParams
import com.hitales.ui.TextAlignment
import com.hitales.ui.TextView

class TextBorderTestController : BorderTestController<TextView>(){

    override fun getView(layoutParams: LayoutParams): TextView {
        val textView = TextView("测试文字",layoutParams)
        textView.alignment = TextAlignment.CENTER
        return textView
    }

}