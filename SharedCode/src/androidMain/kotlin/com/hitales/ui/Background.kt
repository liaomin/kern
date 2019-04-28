package com.hitales.ui

import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.drawable.StateListDrawable




open class Background : StateListDrawable() {

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        current
        var d :ColorStateList? = null
    }

}