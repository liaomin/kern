package liam.hitales.com.android

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hitales.ui.*
import com.hitales.ui.android.ViewStyle
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.Frame
//import com.hitales.ui.android.StateListColor

//import com.hitales.ui.View
//import com.hitales.utils.NotificationCenter
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.coroutineScope
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch


class MainActivity : Activity() {

    var mDelegate: ActivityDelegate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDelegate = Platform.init(this)
        mDelegate?.onCreate()

        return



        val s = com.hitales.ui.android.scrollview.ScrollView.createFromXLM()
//        for ( i in 0 .. 100){
//            val text = TextView(this)
//            text.layoutParams = FrameLayout.LayoutParams(200,20)
//            text.setText("$i")
//            s.addSubView(text)
//        }
//        val image = ImageView(this)
//        image.setImageBitmap(Image.named("1.jpg")?.bitmap)
//        s.addSubView(image)

        var f:com.hitales.ui.TextView? = null
        for ( i in 0 .. 50){
            val text = com.hitales.ui.TextView("$i", Frame(0f,20f*i,200f,18f))
            if( i == 0){
                f = text
            }
            text.setBackgroundColor(Color.RED)
            s.addSubView(text.getWidget())
        }



        val bu = com.hitales.ui.Button("dd",Frame(300f,320f,500f,100f))
        bu.setBackgroundColor(Color.RED)
        bu.setBackgroundColor(Color.BLUE,ViewState.PRESSED)
        bu.setShadow(Color.BLUE,20f)
        s.addSubView(bu.getWidget())

//        s.style = ViewStyle.IOS
//        s.style = ViewStyle.ANDROID

        setContentView(s)
        s.setPadding(50,50,50,50)
        s.postDelayed({
//            s.removeSubView(f!!.getWidget())
//            val imageView = com.hitales.ui.ImageView(Frame(0f,720f,200f,100f))
//            imageView.image = Image.named("1.jpg")
//            s.addSubView(imageView.getWidget())
//            s.postDelayed({
//                s.scrollBy(50,50)
//            },2000)
        },4000)

//        val v = View(this)
//        val lp = RecyclerView.LayoutParams(PixelUtil.toPixelFromDIP(500f).toInt(),PixelUtil.toPixelFromDIP(100f).toInt())
//        lp.topMargin = PixelUtil.toPixelFromDIP(130f).toInt()
//        lp.leftMargin = PixelUtil.toPixelFromDIP(300f).toInt()
//        v.layoutParams = lp
//
//        v.setBackgroundColor(Colors.RED)
//        s.addSubView(v)
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
