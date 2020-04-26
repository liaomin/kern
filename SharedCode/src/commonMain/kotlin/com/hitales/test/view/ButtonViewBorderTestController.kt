package com.hitales.test.view

import com.hitales.ui.Button
import com.hitales.ui.LayoutParams

class ButtonViewBorderTestController : BorderTestController<Button>() {

    override fun getView(layoutParams: LayoutParams): Button {
       val view = Button("文本",layoutParams)
        return view
    }


}