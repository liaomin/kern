
import com.hitales.test.BasicViewController
import com.hitales.test.TempTestViewViewController
import com.hitales.ui.*
import com.hitales.ui.animation.EaseInOutInterpolator
import com.hitales.ui.layout.flex.FlexLayoutParams
import com.hitales.utils.EdgeInsets

class NavigateController : BasicViewController(){

    override fun onCreate() {
        super.onCreate()
        val map = HashMap<String,ViewController>()
        map["测试view"] = TempTestViewViewController()
        map["test1"] = TempTestViewViewController()
        map["test2"] = TempTestViewViewController()
        map["test3"] = TempTestViewViewController()
        map["test4"] = TempTestViewViewController()
        map["test5"] = TempTestViewViewController()
        for (i in 0 until  1){
            map.forEach {
                val entry = it
                val lp = FlexLayoutParams()
                lp.margin = EdgeInsets.value(5f)
                val button = Button(it.key,lp)
                button.padding = EdgeInsets.value(10f)
                button.textSize = 25f
                button.setShadow(Colors.RED,10f,0f,0f)
                button.setOnPressListener {
                    this.push(entry.value)
                }
                addView(button)
            }
        }

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
    window.defaultExitAnimation = animation.reverse()
    window.rootViewController = NavigateController()
}