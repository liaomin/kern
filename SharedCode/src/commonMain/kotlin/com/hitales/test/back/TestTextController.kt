package com.hitales.test.back

//package com.hitales.test
//import com.hitales.ui.*
//
//open class TestTextControllerTest :TestViewController(){
//
//    override fun testView() {
//
//        addButton("测试Bold和textColor"){
//            push(TestBorderAndTextColor())
//        }
//
//        addButton("测试FontSize"){
//            push(TestFontSize())
//        }
//
//        addButton("测试Alignment"){
//            push(TestAlignment())
//        }
//
//        addButton("测试LineHeight"){
//            push(TestLineHeight())
//        }
//
//        addButton("测试decorationLine"){
//            push(TestDecorationLine())
//        }
//
//        addButton("测试letterSpacing"){
//            push(TestLetterSpacing())
//        }
//
//        addButton("测试ellipsizeMode和numberOfLines"){
//            push(TestEllipsizeMode())
//        }
//    }
//}
//
//
//class TestEllipsizeMode : TestTextControllerTest(){
//    override fun testView() {
//        var width = Platform.windowWidth
//        var view = TextView("TAIL测试文字测试文字测试文字测试文测试文字测测试文字测试文字测试文字测试文测试文字测试测试文字测试文字测试文字测试文测试文字测试试")
//        view.textSize = 16f
//        view.numberOfLines = 1
//        view.ellipsizeMode = TextEllipsizeMode.TAIL
//        addView(view, 0f, 20f)
//
//        view = TextView("MIDDLE测试文字测试文字测试文字测试文测试文字测测试文字测试文字测试文字测试文测试文字测试测试文字测试文字测试文字测试文测试文字测试试")
//        view.textSize = 16f
//        view.numberOfLines = 1
//        view.ellipsizeMode = TextEllipsizeMode.MIDDLE
//        addView(view, 0f, 20f)
//
//        view = TextView("HEAD测试文字测试文字测试文字测试文测试文字测测试文字测试文字测试文字测试文测试文字测试测试文字测试文字测试文字测试文测试文字测试试")
//        view.textSize = 16f
//        view.numberOfLines = 1
//        view.ellipsizeMode = TextEllipsizeMode.HEAD
//        addView(view, 0f, 20f)
//
//        view = TextView("TAIL测试文字测试文字测试文字测试文测试文字测测试文字测试文字测试文字测试文测试文字测试测试文字测试文字测试文字测试文测试文字测试试TAIL测试文字测试文字测试文字测试文测试文字测测试文字测试文字测试文字测试文测试文字测试测试文字测试文字测试文字测试文测试文字测试试")
//        view.textSize = 16f
//        view.numberOfLines = 2
//        view.ellipsizeMode = TextEllipsizeMode.TAIL
//        addView(view, 0f, 40f)
//    }
//}
//
//
//class TestLetterSpacing : TestTextControllerTest(){
//    override fun testView() {
//        var width = Platform.windowWidth
//        var view = TextView("测试文字测试文字测试文字测试文测试文字测试")
//        view.textSize = 16f
//        var size = view.measureSize(width, 0f)
//        addView(view, 0f, size.height)
//
//        var letterSpacing = 1f
//        view = TextView("letterSpacing:$letterSpacing 测试文字测试文字测试文字测试文测试文字测试")
//        view.textSize = 16f
//        view.letterSpacing = letterSpacing
//        size = view.measureSize(width, 0f)
//        addView(view, 0f, size.height)
//
//        letterSpacing = 2f
//        view = TextView("letterSpacing:$letterSpacing 测试文字测试文字测试文字测试文测试文字测试")
//        view.textSize = 16f
//        view.letterSpacing = letterSpacing
//        size = view.measureSize(width, 0f)
//        addView(view, 0f, size.height)
//
//        letterSpacing = 3f
//        view = TextView("letterSpacing:$letterSpacing letterSpacing")
//        view.textSize = 16f
//        view.letterSpacing = letterSpacing
//        size = view.measureSize(width, 0f)
//        addView(view, 0f, size.height)
//
//        letterSpacing = 4f
//        view = TextView("letterSpacing:$letterSpacing letterSpacing")
//        view.textSize = 16f
//        view.letterSpacing = letterSpacing
//        size = view.measureSize(width, 0f)
//        addView(view, 0f, size.height)
//    }
//}
//
//class TestDecorationLine : TestTextControllerTest(){
//
//    override fun testView() {
//        var width = Platform.windowWidth
//        var view = TextView("NONE 测试文字测试文字测试文字测试文测试文字测试")
//        view.textSize = 16f
//        view.decorationLine = TextDecorationLine.NONE
//        view.lineHeight = 50f
//        var size = view.measureSize(width, 0f)
//        addView(view, 0f, size.height)
//
//        view = TextView("UNDERLINE_LINE_THROUGH 测试文字测试文字测试文字测试文测试文字测试")
//        view.textSize = 16f
//        view.decorationLine = TextDecorationLine.UNDERLINE_LINE_THROUGH
//        view.lineHeight = 50f
//        size = view.measureSize(width, 0f)
//        addView(view, 0f, size.height)
//
//        view = TextView("测试文字测试文xww")
//        view.textSize = 16f
//        view.decorationLine = TextDecorationLine.UNDERLINE
//        view.lineHeight = 50f
//        size = view.measureSize(width, 0f)
//        addView(view, 0f, size.height)
//
//        view = TextView("LINE_THROUGH 测试文字测试文字测试文字测试文测试文字测试")
//        view.textSize = 16f
//        view.decorationLine = TextDecorationLine.LINE_THROUGH
//        view.lineHeight = 50f
//        size = view.measureSize(width, 0f)
//        addView(view, 0f, size.height)
//    }
//
//}
//
//
//class TestBorderAndTextColor : TestTextControllerTest(){
//
//    override fun testView() {
//        var width = Platform.windowWidth
//        var view = TextView("normal 测试文字测试文字测试文字测试文测试文字测试")
//        val colors = arrayListOf(Colors.RED,Colors.GREEN,Colors.BLUE,0x54000000)
//        for (i in 0 until  colors.size){
//            val color = colors[i]
//            view = TextView("color:$colors 测试文字测试文字测试文字测试文测试文字测试")
//            view.textSize = 16f
//            val size = view.measureSize(width,0f)
//            addView(view,0f,size.height)
//            view.textColor = color
//        }
//        for (i in 0 until  colors.size){
//            val color = colors[i]
//            view = TextView("color:$colors bold:true 测试文字测试文字测试文字测试文测试文字测试")
//            view.textSize = 16f
//            val size = view.measureSize(width,0f)
//            addView(view,0f,size.height)
//            view.textColor = color
//            view.bold = true
//        }
//    }
//}
//
//class TestLineHeight : TestTextControllerTest(){
//
//    override fun testView() {
//        val text = "测试文字测试文字测试文字测试文测试文字测试测试文字测试文字测试文字测试文测试文字测试测试文字测试文字测试文字测试文测试文字测试测试文字测试文字测试文字测试文测试文字测试"
//        var width = Platform.windowWidth
//        var view = TextView("normal $text")
//        view.textSize = 16f
//        view.setBackgroundColor(Colors.RED)
//        val size = view.measureSize(width,0f)
//        addView(view,0f,size.height)
//        for (i in 0 .. 40){
//            val lineHeight = i.toFloat()+16
//            view = TextView("lineHeight:$lineHeight $text")
//            view.textSize = 16f
//            view.lineHeight = lineHeight
//            view.setBackgroundColor(Colors.RED)
//            val size = view.measureSize(width,0f)
//            addView(view,0f,size.height)
//            if( i == 40){
//                var frame = view.frame.clone()
//                frame.height = 56f
//                var temp = View(frame)
//                temp.setBackgroundColor(0xaa00FF00.toInt())
//                (this.view as ScrollView).addSubView(temp)
//                 frame = frame.clone()
//                frame.y += 56f
//                temp = View(frame)
//                temp.setBackgroundColor(0xaa0000FF.toInt())
//                (this.view as ScrollView).addSubView(temp)
//                frame = frame.clone()
//                frame.y += 56f
//                temp = View(frame)
//                temp.setBackgroundColor(0xaaffff00.toInt())
//                (this.view as ScrollView).addSubView(temp)
//                frame = frame.clone()
//                frame.y += 56f
//                temp = View(frame)
//                temp.setBackgroundColor(0xaaff7f00.toInt())
//                (this.view as ScrollView).addSubView(temp)
//            }
//        }
//    }
//}
//
//class TestFontSize : TestTextControllerTest(){
//
//    override fun testView() {
//        var width = Platform.windowWidth
//        for (i in 0..50){
//            val textSize = 1+i*2
//            var view = TextView("测试文字textSize:$textSize")
//            view.textSize = textSize.toFloat()
//            view.setBackgroundColor(Colors.RED)
//            val size = view.measureSize(width,0f)
//            addView(view,0f,size.height)
//        }
//    }
//}
//
//class TestAlignment: TestTextControllerTest(){
//
//    override fun testView() {
//        var view = TextView("LEFT测试文字")
//        view.textSize = 20f
//        view.alignment = TextAlignment.LEFT
//        view.setBackgroundColor(Colors.RED)
//        addView(view,0f,50f)
//
//        view = TextView("RIGHT测试文字")
//        view.textSize = 20f
//        view.alignment = TextAlignment.RIGHT
//        view.setBackgroundColor(Colors.RED)
//        addView(view,0f,50f)
//
//        view = TextView("CENTER测试文字")
//        view.textSize = 20f
//        view.alignment = TextAlignment.CENTER
//        view.setBackgroundColor(Colors.RED)
//        addView(view,0f,50f)
//
//        view = TextView("LEFT测试文字测试文字测试文字测试文字测试文字")
//        view.textSize = 20f
//        view.alignment = TextAlignment.LEFT
//        view.setBackgroundColor(Colors.RED)
//        addView(view,0f,100f)
//
//        view = TextView("RIGHT测试文字测试文字测试文字测试文字测试文字")
//        view.textSize = 20f
//        view.alignment = TextAlignment.RIGHT
//        view.setBackgroundColor(Colors.RED)
//        addView(view,0f,100f)
//
//        view = TextView("CENTER测试文字测试文字测试文字测试文字测试文字")
//        view.textSize = 20f
//        view.alignment = TextAlignment.CENTER
//        view.setBackgroundColor(Colors.RED)
//        addView(view,0f,100f)
//    }
//}