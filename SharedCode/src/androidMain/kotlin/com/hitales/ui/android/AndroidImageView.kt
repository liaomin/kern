package com.hitales.ui.android

import android.widget.ImageView
import com.hitales.ui.Platform

open class AndroidImageView : ImageView {

    constructor():super(Platform.getApplication()){
        scaleType = ScaleType.CENTER_CROP
    }

}