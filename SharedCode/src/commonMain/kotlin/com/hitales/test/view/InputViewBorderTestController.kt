package com.hitales.test.view

import com.hitales.ui.LayoutParams
import com.hitales.ui.TextInput

class InputViewBorderTestController : BorderTestController<TextInput>() {

    override fun getView(layoutParams: LayoutParams): TextInput {
       val view = TextInput("文本",layoutParams)
        return view
    }


}