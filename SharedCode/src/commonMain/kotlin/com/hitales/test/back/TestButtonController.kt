package com.hitales.test.back

//package com.hitales.test
//import com.hitales.ui.*
//
//open class TestButtonController :TestViewController(){
//
//    override fun testView() {
//
//        var width = Platform.windowWidth - 20
//        var view = Button("TAIL测试文字测试文字测试文字测试文测试文字测测试文字测试文字测试文字测试文测试文字测试测试文字测试文字测试文字测试文测试文字测试试")
//        view.setBackgroundColor(Colors.BLUE)
//        view.setOnPressListener {  }
//        view.textSize = 16f
//        view.numberOfLines = 1
//        view.ellipsizeMode = TextEllipsizeMode.TAIL
//        var size = view.measureSize(width,0f)
//        addView(view, 10f,size.width, size.height)
//
//        view = Button("border")
//        view.setOnPressListener {  }
//        view.setBackgroundColor(Colors.BLUE)
//        view.textSize = 16f
//        view.setBorderRadius(5f)
//        view.numberOfLines = 1
//        view.ellipsizeMode = TextEllipsizeMode.TAIL
//        size = view.measureSize(width,0f)
//        addView(view, 10f,size.width, size.height)
//
//        view = Button("bold")
//        view.setOnPressListener {  }
//        view.setBackgroundColor(Colors.BLUE)
//        view.textSize = 16f
//        view.bold = true
//        view.setBorderRadius(5f)
//        view.numberOfLines = 1
//        view.ellipsizeMode = TextEllipsizeMode.TAIL
//        size = view.measureSize(width,0f)
//        addView(view, 10f,size.width, size.height)
//
//        view = Button("textColor")
//        view.setOnPressListener {  }
//        view.setBackgroundColor(Colors.BLUE)
//        view.textSize = 16f
//        view.textColor = Colors.RED
//        view.setBorderRadius(5f)
//        view.numberOfLines = 1
//        view.ellipsizeMode = TextEllipsizeMode.TAIL
//        size = view.measureSize(width,0f)
//        addView(view, 10f,size.width, size.height)
//
//        view = Button("textColor state")
//        view.setOnPressListener {  }
//        view.setBackgroundColor(Colors.BLUE)
//        view.textSize = 16f
//        view.setTextColor(Colors.RED)
//        view.setTextColor(Colors.YELLOW,ViewState.PRESSED)
//        view.setBorderRadius(5f)
//        view.numberOfLines = 1
//        view.ellipsizeMode = TextEllipsizeMode.TAIL
//        size = view.measureSize(width,0f)
//        addView(view, 10f,size.width, size.height)
//
//        view = Button("textColor state bgColor state")
//        view.setOnPressListener {  }
//        view.setBackgroundColor(Colors.RED,ViewState.PRESSED)
//        view.setBackgroundColor(Colors.BLUE)
//
//
//        view.textSize = 16f
//        view.setTextColor(Colors.RED)
//        view.setTextColor(Colors.BLUE,ViewState.PRESSED)
//        view.setBorderRadius(5f)
//        view.numberOfLines = 1
//        view.ellipsizeMode = TextEllipsizeMode.TAIL
//        size = view.measureSize(width,0f)
//        addView(view, 10f,size.width, size.height)
//
//        val image1 = Image.named("1.jpg")!!
//        val image2 = Image.named("2.jpg")!!
//
//        view = Button("bgImage")
//        view.setOnPressListener {  }
//        view.setBackgroundColor(Colors.BLUE)
//        view.setBackgroundColor(Colors.RED,ViewState.PRESSED)
//        view.textSize = 16f
//        view.setTextColor(Colors.RED)
//        view.setTextColor(Colors.BLUE,ViewState.PRESSED)
//        view.setBorderRadius(5f)
//        view.numberOfLines = 1
//        view.ellipsizeMode = TextEllipsizeMode.TAIL
//        view.setBackgroundImage(image1)
//        size = view.measureSize(width,0f)
//        addView(view, 10f,size.width, size.height)
//
//        view = Button("bgImage state")
//        view.setOnPressListener {  }
//        view.setBackgroundColor(Colors.BLUE)
//        view.setBackgroundColor(Colors.RED,ViewState.PRESSED)
//        view.textSize = 16f
//        view.setTextColor(Colors.RED)
//        view.setTextColor(Colors.BLUE,ViewState.PRESSED)
//        view.setBorderRadius(5f)
//        view.numberOfLines = 1
//        view.ellipsizeMode = TextEllipsizeMode.TAIL
//        view.setBackgroundImage(image1)
//        view.setBackgroundImage(image2,ViewState.PRESSED)
//        size = view.measureSize(width,0f)
//        addView(view, 10f,size.width, size.height)
//
//
//    }
//}
//
