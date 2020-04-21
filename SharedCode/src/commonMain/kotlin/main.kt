

import com.hitales.test.BasicViewController
import com.hitales.test.TempTestViewViewController
import com.hitales.test.back.TestCollectionViewController
import com.hitales.test.view.TextBorderTestController
import com.hitales.test.view.ViewBorderTestController
import com.hitales.ui.*
import com.hitales.ui.animation.EaseInOutInterpolator
import com.hitales.ui.layout.flex.*
import com.hitales.utils.EdgeInsets


class NavigateController : BasicViewController(){
    override fun createLayout(): Layout {
        return FlexLayout()
    }

    override fun onCreate() {
        super.onCreate()
        val map = HashMap<String,ViewController>()
        map["测试view"] = TempTestViewViewController()
        map["test1测试"] = TempTestViewViewController()
        map["test2"] = TempTestViewViewController()
        map["测试Collection"] = TestCollectionViewController()
        map["TextViewBorderTestController"] = TextBorderTestController()
        map["ViewBorderTestController"] = ViewBorderTestController()
        for (i in 0 until  1){
            map.forEach {
                val entry = it
                val lp = FlexLayoutParams()
                lp.margin = EdgeInsets.value(5f)
                val button = Button(it.key,lp)
                button.padding = EdgeInsets.value(10f)
                button.textSize = 25f
                button.setBackgroundColor(Colors.GREEN,ViewState.PRESSED)
//                button.setShadow(Colors.RED,10f,0f,0f)
                button.setOnPressListener {
                    this.push(entry.value)
                }
//                addView(button)
            }
        }

//        val lp = FlexLayoutParams(300f)
//        lp.margin = EdgeInsets.value(5f)
//        val textInp = TextInput("",lp)
//        textInp.setBackgroundColor(Colors.RED)
//        addView(textInp)

        view?.padding = EdgeInsets.value(20f)
        val sc = ScrollView(LayoutParams(300f,300f))
//        val sc = FlexLayout()
//        sc.orientation = Orientation.HORIZONTAL
        sc.padding = EdgeInsets.value(10f)
//        sc.clipsToBounds = true
        sc.setBackgroundColor(0x5f0000ff)
//        sc.isPageEnable = true
        sc.justifyContent = JustifyContent.SPACE_AROUND
        sc.alignItems = AlignItems.FLEX_END
        sc.flexDirection = FlexDirection.ROW
        sc.flexWarp = FlexWarp.WARP_REVERSE
        for ( i in 0 until 10){
            val lp = FlexLayoutParams()
            lp.margin = EdgeInsets.value(5f)
            var text = TextInput("司马彦行书${i+1}",lp)
            text.setBackgroundColor(Colors.RED)
            text.setFontStyle("JingDianXingShuJian",FontStyle.BOLD_ITALIC)
            sc.addSubView(text)

            val b = Button("测试 ${i+1}",lp)
            sc.addSubView(b)
        }

//        view?.padding = EdgeInsets.value(20f)
//        val sc = ScrollView()
//        sc.setShadow(Colors.RED,4f,10f,0f)
//        sc.setBorderRadius(8f)
//        sc.padding = EdgeInsets.value(10f)
//        sc.setBackgroundColor(Colors.BLUE)
//        sc.justifyContent = JustifyContent.CENTER
//        sc.alignItems = AlignItems.STRETCH
//        sc.flexDirection = FlexDirection.ROW
//        for ( i in 0 until 2){
//            val lp = FlexLayoutParams()
//            lp.margin = EdgeInsets.value(5f)
//            var text = TextInput("司马彦行书",lp)
//            text.setBackgroundColor(Colors.RED)
//            text.setFontStyle("JingDianXingShuJian",FontStyle.BOLD_ITALIC)
//            sc.addSubView(text)
//        }

//        val lp = FlexLayoutParams()
//        lp.flex = 1f
//        lp.minWidth = 50f
//        lp.height = 100f
//        val v = View(lp)
//        v.setBackgroundColor(Colors.YELLOW)
//        sc.addSubView(v)
//
        addView(sc)

//        text = TextInput("司马彦行书",lp)
//        text.setBackgroundColor(Colors.RED)
//        text.setFontStyle("JingDianXingShuJian",FontStyle.BOLD)
//        addView(text)
//
//        text = TextInput("司马彦行书",lp)
//        text.setBackgroundColor(Colors.RED)
//        text.setFontStyle("JingDianXingShuJian",FontStyle.ITALIC)
//        addView(text)
//
//        text = TextInput("司马彦行书")
//        text.padding = EdgeInsets.identity()
//        text.setBackgroundColor(Colors.RED)
//        text.setFontStyle("JingDianXingShuJian")
//        addView(text)

    }

}

fun main() {
    val window = Screen.getInstance().window
    var animation = Animation()
    animation.setFromTranslate(window.width,0f)
    animation.duration = 300f
    animation.fillAfter = true
    animation.interpolator = EaseInOutInterpolator()
    window.defaultEnterAnimation = animation
    window.rootViewController = NavigateController()


}