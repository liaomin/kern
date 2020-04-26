package com.hitales.test.view

import com.hitales.ui.LayoutParams
import com.hitales.ui.TextView

class TextViewBorderTestController : BorderTestController<TextView>() {

    override fun getView(layoutParams: LayoutParams): TextView {
       val view:TextView = TextView("文本",layoutParams)
        return view
    }


}