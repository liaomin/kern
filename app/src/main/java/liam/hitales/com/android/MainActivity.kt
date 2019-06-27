package liam.hitales.com.android

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.StateListDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Xml
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.view.animation.Transformation
import android.widget.*
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.hitales.ui.*
import com.hitales.ui.animation.AndroidAnimation
import com.hitales.ui.utils.PixelUtil
//import com.hitales.ui.android.StateListColor

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

    var mDelegate: ActivityDelegate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDelegate = Platform.init(this)
        mDelegate?.onCreate()
        LinearLayout(this).orientation
//        val intent = Intent(
//            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//            Uri.parse("package:$packageName")
//        )
//        startActivityForResult(intent, 1234)

//        setContentView(R.layout.test)
//
//        val rootView = findViewById<FrameLayout>(R.id.root)
//        rootView.postDelayed({
//            val a = com.hitales.ui.android.AndroidEditTextView(this)
//            a.setBackgroundColor(Color.RED)
//            val p = FrameLayout.LayoutParams(400,100)
//            p.topMargin = 150
//            rootView.addView(a,p)
//        },5000)

//        setContentView(fr)


//        println("~~$m，${c.locationX}, ${c.locationZ}")


//        RecyclerView(this).startAnimation(ScaleAnimation)
    }

    override fun onResume() {
        super.onResume()
        mDelegate?.onResume()

        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivityForResult(intent, 1234)
            }else{
                OverlayView.showOverlay(this)
            }
        } else {
            OverlayView.showOverlay(this)
        }
    }

    override fun onPause() {
        super.onPause()
        windowManager.removeView(OverlayView.instance)
        mDelegate?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mDelegate?.onDestory()
    }

    override fun onBackPressed() {
        if(mDelegate != null && mDelegate!!.onBackPressed()){
            return
        }
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mDelegate?.onActivityResult(requestCode,resultCode,data)
        if (requestCode === 1234) {
            OverlayView.showOverlay(this)
        }
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
