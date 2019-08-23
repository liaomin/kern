package liam.hitales.com.android

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.InputType
import android.view.Gravity
import android.widget.EditText
import android.widget.FrameLayout
import com.hitales.ui.Colors
import com.hitales.ui.android.AndroidActivity


//import com.hitales.ui.android.StateListColor

//import com.hitales.ui.View
//import com.hitales.utils.NotificationCenter
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.coroutineScope
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch


class MainActivity : AndroidActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mDelegate = Platform.init(this)
//        mDelegate?.onCreate()



//        var f1 = F1()
//        addFragment(f1)
//
//        val delay = 3000L
//
//        Timer.schedule(delay){
//            addFragment(F2())
//            Timer.schedule(delay){
//                addFragment(F3())
//                Timer.schedule (delay){
//                    popTo(f1.index)
//                }
//            }
//        }

//        supportFragmentManager.beginTransaction().add(R.id.root,F1(),"").addToBackStack("dw").commit()

        return


        val f = FrameLayout(this)
        val editText = EditText(this)
        editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
//文本显示的位置在EditText的最上方
        editText.setGravity(Gravity.TOP);
        editText.setText("1233333333333333333333333333333333333333333333333333333333333333333333333333");
//改变默认的单行模式
        editText.setSingleLine(false);
//水平滚动设置为False
        editText.setHorizontallyScrolling(false);
        var edi = editText
        val lp =  FrameLayout.LayoutParams(300,250)
        lp.topMargin = 400
        edi.setBackgroundColor(Colors.BLUE)
        f.addView(edi,lp)
        setContentView(f)

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

    override fun onDestroy() {
        super.onDestroy()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 1234) {
            OverlayView.showOverlay(this)
        }
    }
}
