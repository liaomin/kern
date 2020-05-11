package com.hitales.test

import com.hitales.ui.*
import com.hitales.ui.layout.flex.FlexLayoutParams
import com.hitales.utils.EdgeInsets

open class BasicViewController : ViewController() {


    override fun createLayout(): Layout {
//        return Layout()
        val scrollView = ScrollView()
//        scrollView.padding = EdgeInsets.value(150f)

//        scrollView.flexDirection = FlexDirection.ROW
//        scrollView.alignItems = AlignItems.CENTER
//        scrollView.showScrollBar = false
//        scrollView.setBackgroundColor(Colors.YELLOW)

        val lp = FlexLayoutParams()
        lp.margin = EdgeInsets.value(5f)
        val button = Button("返回🔙",lp)
        button.padding = EdgeInsets.value(10f)
        button.textSize = 25f
        button.setBackgroundColor(Colors.GREEN, ViewState.PRESSED)
        button.setOnPressListener {
            this.pop()
        }
        scrollView.addSubView(button)

        return scrollView
    }

    fun addView(view: View){
        this.view?.addSubView(view)
    }
}