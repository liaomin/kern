package liam.hitales.com.android


//import com.hitales.ui.android.StateListColor

//import com.hitales.ui.View
//import com.hitales.utils.NotificationCenter
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.coroutineScope
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.hitales.ui.ActivityDelegate
import com.hitales.ui.Platform
import com.hitales.ui.android.AndroidActivity
import main

class MainActivity : AndroidActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main()

        val w = View(this)

//        val image = Image.named("2.jpg")
//
//        val l = FrameLayout(this)
//        var offsetX = 100
//        var offsetY = 0
//        for (i in 0 until 100){
//            val imageView2 = com.hitales.ui.ImageView()
//            val view = imageView2.getWidget()
//            imageView2.setBorderRadius(30f)
//            imageView2.setShadow(Colors.RED,0f,0f,0f)
//            imageView2.setBackgroundColor(Colors.BLUE)
//            view.setImageBitmap(image?.bitmap)
//            val lp = FrameLayout.LayoutParams(800,800)
//            lp.leftMargin = offsetX
//            lp.topMargin = offsetY
//            offsetY += 800
//            l.addView(view,lp)
//        }
//
//        val s = NestedScrollView(this)
//        s.clipChildren = false
//        l.clipChildren = false
//        s.addView(l)
//        setContentView(s)


    }

    override fun onInit(): ActivityDelegate {
       return Platform.init(this,true)
    }

    override fun onResume() {
        super.onResume()
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 1234) {
            OverlayView.showOverlay(this)
        }
    }
}
