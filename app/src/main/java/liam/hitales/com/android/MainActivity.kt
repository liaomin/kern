package liam.hitales.com.android

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.drawable.StateListDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Xml
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.hitales.ui.*
//import com.hitales.ui.StateListColor

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
//        val intent = Intent(
//            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//            Uri.parse("package:$packageName")
//        )
//        startActivityForResult(intent, 1234)

//        setContentView(R.layout.test)

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
