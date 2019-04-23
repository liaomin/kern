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
import android.view.ViewGroup
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.hitales.liam.ui.Platform
import com.hitales.liam.ui.View
import com.hitales.liam.utils.NotificationCenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import java.util.*
import kotlinx.coroutines.launch

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
//        ScrollView(this).addView()
        Platform.init(this)
        val update = object :Runnable{
            override fun run() {
                NotificationCenter.getInstance().notify("update")
                window.decorView.postDelayed(this,10)
            }
        }
        update.run()
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
