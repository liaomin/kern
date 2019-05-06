package liam.hitales.com.android

import android.content.res.Resources
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.drawable.StateListDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ScrollView
import android.widget.TextView
//import com.hitales.ui.StateListColor
import com.hitales.ui.ViewState

import com.hitales.ui.Platform
import com.hitales.ui.StateListColor
import com.hitales.utils.Timer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.*

//import com.hitales.ui.View
//import com.hitales.utils.NotificationCenter
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.coroutineScope
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(R.layout.activity_main)
//        findViewById<TextView>(R.id.text_test).text = Platform.windowHeight.toString()

//        val v  = com.hitales.liam.ui.TextView()
//        v.setBackgroundColor(Color.RED)
//        v.text = "dwdw"
//        setContentView(v)


        Platform.init(this)
//        val update = object :Runnable{
//            override fun run() {
//                NotificationCenter.getInstance().notify("update")
//                window.decorView.postDelayed(this,10)
//            }
//        }
//        update.run()

//        val scrollView = ScrollView(this)
//        val frameLayout = FrameLayout(this)
////        scrollView.addView(frameLayout,FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT))
//        for(i in 0 until 1000){
//            val textView = Button(this)
//            if(i == 0){
//                textView.setPadding(100,0,100,0)
//            }
////            textView.setBackgroundColor(0)
//            textView.text = i.toString()
//            val textColor = StateListColor(Color.RED)
//            textView.setBackgroundColor(Color.RED)
//            var layout = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,150)
//            layout.topMargin = 155 * i
//            frameLayout.addView(textView,layout)
//            textColor.setColorForState(Color.BLACK,ViewState.PRESSED)
//            textView.setTextColor(textColor)
//            frameLayout.postDelayed({
//                textColor.setColorForState(Color.GREEN,ViewState.DISABLED)
////                textView.isEnabled = false
//            },5000)
//        }
//
//        setContentView(frameLayout)
        StateListDrawable()
//        LinearGradient(this)

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
