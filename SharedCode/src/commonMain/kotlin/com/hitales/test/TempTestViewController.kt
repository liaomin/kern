package com.hitales.test

import com.hitales.ui.*
import com.hitales.ui.layout.flex.FlexLayoutParams

open class TempTestViewController : BasicController() {

    val itemWidth = Platform.windowWidth / 3f


    override fun onCreate() {
        var lp = FlexLayoutParams()
        lp.width = itemWidth
        lp.height = itemWidth
        var view = TextInput("吊袜带无",lp)
//        var view = View(lp)
        view.setBackgroundColor(Colors.RED)
        view.setTextColor(Colors.BLUE)
        view.bold = true
        view.textSize = 30f
//        view.autoFocus = true
        view.setBorderRadius(itemWidth/2)
        view.setBorderWidth(10f)
        view.borderStyle = BorderStyle.DASHED
        view.setBorderColor(Colors.BLUE)
//        view.singleLine = false
        addView(view)

        val image = Image.named("1.jpg")
        val image2 = Image.named("12.jpg")

        val imageView = ImageView(lp)
        imageView.image = image


        addView(imageView)
    }

}