package com.hitales.test
import com.hitales.ui.*

open class TestImageController :TestViewController(){

    override fun testView() {

        var image1 = Image.named("1.jpg")!!

        var index = 0

        var width = Platform.windowWidth - 20
        var view = ImageView()
        view.setBackgroundColor(Colors.BLUE)
        view.image = image1
//        var size = view.measureSize(width,0f)
//        addView(view, 10f,size.width, size.height)

        view = ImageView()
        view.setBackgroundColor(Colors.BLUE)
        view.image = image1
        view.setBorderRadius(5f)
        view.setShadow(Colors.RED,10f,3f,3f)
        view.resizeMode = ImageResizeMode.SCALE_FIT
        addView(view, "SCALE_FIT",index++)

        view = ImageView()
        view.setBackgroundColor(Colors.BLUE)
        view.image = image1
        view.setBorderRadius(5f)
        view.setShadow(Colors.RED,10f,0f,0f)
        view.resizeMode = ImageResizeMode.CENTER
        addView(view, "CENTER",index++)

        view = ImageView()
        view.setBackgroundColor(Colors.BLUE)
        view.image = image1
        view.setBorderRadius(5f)
        view.setShadow(Colors.RED,10f,0f,0f)
        view.resizeMode = ImageResizeMode.SCALE_FILL
        addView(view, "SCALE_FILL",index++)

        view = ImageView()
        view.setBackgroundColor(Colors.BLUE)
        view.image = image1
        view.setBorderRadius(5f)
        view.resizeMode = ImageResizeMode.SCALE_CENTER_CROP
        addView(view, "SCALE_CENTER_CROP",index++)



        image1 = Image.named("2.jpg")!!

        view = ImageView()
        view.setBackgroundColor(Colors.BLUE)
        view.image = image1
        view.setBorderRadius(5f)
        view.resizeMode = ImageResizeMode.SCALE_FIT
        addView(view, "SCALE_FIT",index++)

        view = ImageView()
        view.setBackgroundColor(Colors.BLUE)
        view.image = image1
        view.setBorderRadius(5f)
        view.resizeMode = ImageResizeMode.CENTER
        addView(view, "CENTER",index++)

        view = ImageView()
        view.setBackgroundColor(Colors.BLUE)
        view.image = image1
        view.setBorderRadius(5f)
        view.resizeMode = ImageResizeMode.SCALE_FILL
        addView(view, "SCALE_FILL",index++)

        view = ImageView()
        view.setBackgroundColor(Colors.BLUE)
        view.image = image1
        view.setBorderRadius(5f)
        view.resizeMode = ImageResizeMode.SCALE_CENTER_CROP
        addView(view, "SCALE_CENTER_CROP",index++)
    }
}

