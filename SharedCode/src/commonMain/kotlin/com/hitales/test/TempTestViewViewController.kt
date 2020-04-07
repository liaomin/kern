package com.hitales.test

import com.hitales.ui.*
import com.hitales.ui.layout.flex.FlexLayoutParams
import com.hitales.utils.EdgeInsets
import com.hitales.utils.WeakReference

open class TempTestViewViewController : BasicViewController(),TextInputDelegate {

    val itemWidth = Platform.windowWidth / 3f

    override fun createLayout(): Layout {
        val s =  super.createLayout() as ScrollView
        s.isPageEnable = true
        return s
    }

    override fun onCreate() {
        super.onCreate()
        var lp = FlexLayoutParams()
        lp.width = itemWidth
        lp.height = itemWidth
        var view = TextInput("单位",lp)
        view.delegate = WeakReference(this)
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


        var view2 = TextInput("",lp)
        view2.autoFocus = true
        view.nextInput = WeakReference(view2)
        view2.setBackgroundColor(Colors.RED)
        addView(view2)

        var view3 = TextInput("",lp)
        view3.setBackgroundColor(Colors.RED)
        view2.nextInput = WeakReference(view3)
        addView(view3)

        var view4= TextInput("",lp)
        view4.setBackgroundColor(Colors.RED)
        view3.nextInput = WeakReference(view4)
        addView(view4)


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

    override fun onTextChanged(view: TextInput, s: CharSequence) {
        println(s)
    }

    override fun onFocusChanged(view: TextInput, focused: Boolean) {

    }

    override fun onSelectionChanged(view: TextInput, selStart: Int, selEnd: Int) {

    }

    override fun shouldChangeText(view: TextInput, beforeText: CharSequence, start: Int, length: Int, replaceText: CharSequence): Boolean {
        return true
    }

    override fun onFrameChanged(view: View) {

    }

}