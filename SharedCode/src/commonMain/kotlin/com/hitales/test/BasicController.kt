package com.hitales.test

import com.hitales.ui.*
import com.hitales.utils.Frame
import com.hitales.utils.WeakReference

open class BasicController : Controller() {

    var offsetX = 0f

    var offsetY = 60f

    val buttonWidth = Platform.windowWidth - 20

    val buttonHeight = 50f

    override fun onCreate() {
        view = ScrollView( Frame(0f,0f,Platform.windowWidth,Platform.windowHeight - 30))
        view?.setBackgroundColor(Colors.WHITE)
        val backButton = Button("返回", Frame(0f,0f,80f,48f))
        backButton.setBackgroundColor(0xFFFF0000.toInt())
        var l = WeakReference(this)
        backButton.setOnPressListener {
            l.get()?.pop()
        }
        addView(backButton)
    }


    fun addView(view:View){
        (this.view as ScrollView).addView(view)
    }


    open fun addView(view: View, with: Float = 0f ,height:Float){
        var w = with
        if(with <= 0){
            w = Platform.windowWidth
        }
        view.frame = Frame(offsetX,offsetY,w,height)

        offsetY += height + 10

        (this.view as ScrollView).addView(view)
    }


    open fun addTitleView(title:String ){
        val title = TextView(title, Frame(10f,offsetY,Platform.windowWidth-20,20f))
        title.textSize = 18f
        title.bold = true
        (this.view as ScrollView).addView(title)
    }

    open fun addButton(title:String,onPress: (view: com.hitales.ui.View) -> Unit){
        val button = Button(title, Frame(10f,offsetY, buttonWidth , buttonHeight))
        offsetY += buttonHeight + 10
        button.setOnPressListener(onPress)
        button.setBackgroundColor(Colors.BLUE)
        (this.view as ScrollView).addView(button)
    }


    override fun onPause() {
        super.onPause()
        println("${this::class} onPause")
    }

    override fun onResume() {
        super.onResume()
        println("${this::class} onResume")
    }


}