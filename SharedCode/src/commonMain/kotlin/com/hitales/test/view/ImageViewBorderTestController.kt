package com.hitales.test.view

import com.hitales.ui.Image
import com.hitales.ui.ImageView
import com.hitales.ui.LayoutParams

class ImageViewBorderTestController : BorderTestController<ImageView>() {

    override fun getView(layoutParams: LayoutParams): ImageView {
        val view = ImageView(layoutParams)
        view.image = Image.named("1.jpg")
        return view
    }


}