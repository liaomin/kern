package com.hitales.test

import com.hitales.ui.*
import com.hitales.ui.layout.flex.*
import com.hitales.utils.EdgeInsets

open class TempTestViewViewController : BasicViewController() {



    val itemWidth = Platform.windowWidth / 3f

    override fun onCreate() {
        super.onCreate()
        var lp = FlexLayoutParams()
        lp.width = itemWidth
        lp.height = itemWidth
        var view = TextInput("单位",lp)
//        var view = View(lp)
        view.setBackgroundColor(Colors.RED)
        view.setTextColor(Colors.BLUE)
        view.bold = true
        view.textSize = 30f
//        view.autoFocus = true
        view.setBorderRadius(itemWidth/2)
        view.setBorderWidth(10f)
        view.setBorderStyle(BorderStyle.DASHED)
        view.setBorderColor(Colors.BLUE)
//        view.singleLine = false
        addView(view)

        val image = Image.named("1.jpg")
        val image2 = Image.named("2.jpg")

        val imageView = ImageView(lp)
        imageView.image = image

        addView(imageView)

        for ( i in 0 until  10){
            var l = FlexLayoutParams()
            val imageView2 = ImageView(l)
            l.margin = EdgeInsets.value(10f)
            imageView2.image = image2
            imageView2.setBorderRadius(30f)
            imageView2.setBackgroundColor(Colors.BLUE)
            imageView2.setShadow(Colors.RED,20f,0f,0f)
            addView(imageView2)
        }
    }

}