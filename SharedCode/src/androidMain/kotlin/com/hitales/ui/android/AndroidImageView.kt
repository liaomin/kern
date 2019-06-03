package com.hitales.ui.android

import android.graphics.Canvas
import android.widget.ImageView
import com.hitales.ui.Image
import com.hitales.ui.Platform

open class AndroidImageView : ImageView {

    private val mView:com.hitales.ui.ImageView

    constructor(view:com.hitales.ui.ImageView):super(Platform.getApplication()){
        scaleType = ScaleType.FIT_CENTER
        mView = view
    }

    override fun draw(canvas: Canvas) {
        val bg = mView.mBackground
        if(bg != null && bg.clipPath()){
            val saveCount = canvas.save()
            canvas.clipPath(bg.getOuterPath(canvas.width.toFloat(),canvas.height.toFloat()))
            super.draw(canvas)
            canvas.restoreToCount(saveCount)
        }else{
            super.draw(canvas)
        }
    }


}