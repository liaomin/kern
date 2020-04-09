package com.hitales.ui

import android.text.Editable
import android.text.TextWatcher
import com.hitales.ui.android.AndroidEditTextView
import com.hitales.utils.EdgeInsets


actual open class TextInput : com.hitales.ui.TextView,TextWatcher {


    private data class TextChangeInfo(val s:CharSequence,val start:Int,val length:Int,val source:CharSequence? = null)

    private var oldChangeInfo:TextChangeInfo? = null

    private var replaceChangeInfo:TextChangeInfo? = null

    private var skipChange = false

    actual var nextInput: TextInput? = null

    actual open var autoFocus:Boolean = false

    actual open var singleLine:Boolean = true
        set(value) {
            field = value
            if (value){
                numberOfLines = 1
            }else{
                numberOfLines = 0
            }
        }

    actual constructor(text: CharSequence?, layoutParams: LayoutParams?):super(text,layoutParams){
        padding = EdgeInsets(10f,15f,10f,15f)
        placeholderColor = 0xFF666666.toInt()
        numberOfLines = 1
    }

    override fun createWidget():  AndroidEditTextView {
        val widget = AndroidEditTextView(this)
        widget.addTextChangedListener(this)
        return widget
    }


    fun requestAutoFocus(){
        if(autoFocus){
            val w = getWidget()
            w.requestFocus()
            w.postDelayed({
                w.showSoftKeyboard()
            },0)
        }
    }

    override fun getWidget(): AndroidEditTextView {
        return super.getWidget() as AndroidEditTextView
    }

    actual open var placeholder: CharSequence?
        get() = getWidget().hint
        set(value) {
            getWidget().hint = value
        }

    actual open var placeholderColor: Int
        get() = getWidget().currentHintTextColor
        set(value) {
            getWidget().highlightColor = value
        }

    actual open fun setTextColor(color: Int, state: ViewState) {
        textColorList.setColorForState(color,state)
    }

    actual open fun cleanFocus() {
        mWidget.clearFocus()
    }

    actual open fun focus() {
        mWidget.requestFocus()
    }

    fun onFocusChanged(focused: Boolean) {
        val delegate = this.delegate
        if(delegate != null && delegate is TextInputDelegate){
            delegate.onFocusChanged(this,focused)
        }
    }

    fun canHideSoftKeyboard():Boolean{
        val next = nextInput
        if(next != null){
            next.focus()
            return false
        }
        return true
    }


    override fun afterTextChanged(s: Editable) {
        if(skipChange){
            return
        }
        val oInfo = oldChangeInfo
        val rInfo = replaceChangeInfo
        if(oInfo != null && rInfo != null){
            val delegate = this.delegate
            if(delegate != null && delegate is TextInputDelegate){
                try {
                    if(delegate.shouldChangeText(this,oInfo.source!!,oInfo.start,oInfo.length,rInfo.s)){
                        delegate.onTextChanged(this,s.toString())
                    }else{
                        //还原
                        skipChange = true
                        if(rInfo.length > 0){
                            s.delete(rInfo.start,rInfo.start+rInfo.length)
                        }
                        if(oInfo.length > 0){
                            s.append(oInfo.s,0,oInfo.length)
                        }
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }finally {
                    skipChange = false
                }

            }
        }
        oldChangeInfo = null
        replaceChangeInfo = null
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        if(skipChange){
            return
        }
        //这个方法被调用，说明在s字符串中，从start位置开始的count个字符即将被长度为after的新文本所取代
        oldChangeInfo = TextChangeInfo(s.substring(start,start+count),start,count,s.toString())
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if(skipChange){
            return
        }
        //这个方法被调用，说明在s字符串中，从start位置开始的count个字符刚刚取代了长度为before的旧文本
        replaceChangeInfo = TextChangeInfo(s.substring(start,start + count),start,count)

    }

    fun onSelectionChanged(selStart: Int, selEnd: Int) {
        val delegate = this.delegate
        if(delegate != null && delegate is TextInputDelegate){
            delegate.onSelectionChanged(this,selStart,selEnd)
        }
    }

}