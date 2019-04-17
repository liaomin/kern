package liam.hitales.com.android

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionValues
import android.view.animation.ScaleAnimation
import android.widget.LinearLayout
import android.widget.TextView
import com.hitales.liam.ui.Platform
import com.hitales.liam.ui.TestController
import com.hitales.liam.ui.View
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(R.layout.activity_main)
//        findViewById<TextView>(R.id.text_test).text = Platform.windowHeight.toString()

//        val v  = com.hitales.liam.ui.TextView()
//        v.setBackgroundColor(Color.RED)
//        v.text = "dwdw"
//        setContentView(v)

//        val t = TestController.j

//        val i = Intent(this,TestController::class.java)
//        startActivity(i)

        Platform.init(this)

    }

    override fun getResources(): Resources {
        val res = super.getResources()
        val configuration = res.configuration
        if (configuration.fontScale != 1.0f) {//fontScale要缩放的比例
            configuration.fontScale = 1.0f
            res.updateConfiguration(configuration, res.displayMetrics)
        }
        return res
    }
}
