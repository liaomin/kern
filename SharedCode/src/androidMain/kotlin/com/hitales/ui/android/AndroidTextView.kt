package com.hitales.ui.android

import android.os.Build
import android.text.TextUtils
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.hitales.ui.Button
import com.hitales.ui.Platform
import com.hitales.ui.TextView

open class AndroidTextView(protected val mView: TextView) : AppCompatTextView(Platform.getApplication()){

    init {
        gravity = Gravity.CENTER_VERTICAL
        includeFontPadding = false
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        }
        ellipsize = TextUtils.TruncateAt.END
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mView.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mView.onDetachedFromWindow()
    }
}