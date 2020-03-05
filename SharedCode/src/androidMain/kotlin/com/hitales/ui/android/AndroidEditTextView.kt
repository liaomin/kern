//package com.hitales.ui.android
//
//import android.graphics.Canvas
//import android.text.InputType
//import android.view.Gravity
//import android.view.MotionEvent
//import androidx.appcompat.widget.AppCompatEditText
//import com.hitales.ui.Platform
//import com.hitales.ui.TextInput
//
//
//class AndroidEditTextView constructor(protected val mView: TextInput) : AppCompatEditText(Platform.getApplication(),null, android.R.attr.editTextStyle) {
////    open class AndroidEditTextView(private val view: TextInput) : AppCompatEditText(Platform.getApplication()){
//
//    init {
//        val editText = this
//        editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE)
////文本显示的位置在EditText的最上方
//        editText.setGravity(Gravity.TOP)
//        editText.setText("1233333333333333333333333333333333333333333333333333333333333333333333333333");
////改变默认的单行模式
//        editText.setSingleLine(false)
////水平滚动设置为False
//        editText.setHorizontallyScrolling(false)
////        this.setSingleLine(false)
//    }
//
//    protected val mViewHelper = ViewHelper(this,mView)
//
//    override fun onAttachedToWindow() {
//        super.onAttachedToWindow()
//        mViewHelper.onAttachedToWindow()
//    }
//
//    override fun onDetachedFromWindow() {
//        super.onDetachedFromWindow()
//        mViewHelper.onDetachedFromWindow()
//    }
//
//    override fun dispatchDraw(canvas: Canvas) {
//        mViewHelper.dispatchDraw(canvas)
//        super.dispatchDraw(canvas)
//    }
//
//    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
//        if(mViewHelper.interceptTouchEvent(event)){
//            return false
//        }
//        return mViewHelper.dispatchTouchEvent(event) || super.dispatchTouchEvent(event)
//    }
//
//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        mViewHelper.onTouchEvent(event)
//        return super.onTouchEvent(event)
//    }
//}