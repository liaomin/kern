package liam.hitales.com.android

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.hitales.ui.Colors
import com.hitales.ui.Platform
import com.hitales.ui.utils.PixelUtil
import androidx.core.content.ContextCompat.getSystemService
import android.os.Debug.getMemoryInfo





class OverlayView(context: Context) : View(context),Runnable{


    companion object {

        var instance:OverlayView? = null

        fun showOverlay(activity: Activity){
            val overlayView = OverlayView(activity)
            overlayView.activityManager = activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            instance = overlayView
            var layout_parms = WindowManager.LayoutParams.TYPE_PHONE


            if( Build.VERSION.SDK_INT >= 23){
                if(Settings.canDrawOverlays(activity)){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        layout_parms = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                    } else {
                        layout_parms = WindowManager.LayoutParams.TYPE_PHONE
                    }
                }else{
                    return
                }
            }

            val layoutParams= WindowManager.LayoutParams(
                PixelUtil.toPixelFromDIP(150f).toInt(),
                PixelUtil.toPixelFromDIP(60f).toInt(),
                layout_parms,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT)
            layoutParams.gravity = Gravity.LEFT or Gravity.TOP
            layoutParams.x = PixelUtil.toPixelFromDIP(Platform.windowWidth - 170).toInt()
            layoutParams.y = PixelUtil.toPixelFromDIP(30f).toInt()
//            val windowManager = activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager

            activity.windowManager.addView(overlayView,layoutParams)

        }
    }

    val mPaint = Paint()

    var lastTime = 0L

    var fps = 0

    var fpsText = ""

    var totalMem = ""

    var availMem = ""

    var attachedToWindow = false

    var activityManager:ActivityManager? = null

    init {
        setBackgroundColor(0x54000000)
        mPaint.color = Colors.WHITE
        mPaint.textSize = PixelUtil.toPixelFromSP(14f)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        attachedToWindow = false
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        attachedToWindow = true
        lastTime = System.currentTimeMillis()
        post(this)
    }

    override fun run() {
        if(attachedToWindow){
            invalidate()
            post(this)
            fps++
            val currentTime = System.currentTimeMillis()
            if(currentTime - lastTime >= 1000){
                fpsText = "$fps"
                lastTime = currentTime
                fps = 0
                val memInfo = ActivityManager.MemoryInfo()
                activityManager!!.getMemoryInfo(memInfo)
                availMem = "${memInfo.availMem/1024/1024} M"
                if(Build.VERSION.SDK_INT >= 16){
                    totalMem = "${memInfo.totalMem/1024/1024} M"
                }
                System.gc()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val textSize = mPaint.textSize
        val marginY = 5f
        var offsetY = textSize + marginY
        val offsetX =  20f
        canvas.drawText("fps:$fpsText",offsetX,offsetY,mPaint)
        offsetY += textSize + marginY
        canvas.drawText("totalMem:$totalMem",offsetX,offsetY,mPaint)

        offsetY += textSize + marginY
        canvas.drawText("availMem:$availMem",offsetX,offsetY,mPaint)


    }
}