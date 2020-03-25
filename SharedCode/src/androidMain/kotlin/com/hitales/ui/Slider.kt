package com.hitales.ui

import android.widget.SeekBar
import com.hitales.ui.android.AndroidSlider

actual open class Slider : View {

    actual constructor(layoutParams: LayoutParams):super(layoutParams)

    override fun createWidget(): android.view.View {
        return AndroidSlider(this)
    }

    override fun getWidget(): AndroidSlider {
        return super.getWidget() as AndroidSlider
    }

    actual var max: Int
        get() = getWidget().max
        set(value) {
            getWidget().max = value
        }
    actual var progress: Int
        get() = getWidget().progress
        set(value) {
            getWidget().progress = value
        }

    actual var onValueChangeListener: ((slider: Slider, value: Int, max: Int) -> Unit)? = null
        set(value) {
            field = value
            if(value != null){
                getWidget().setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        value.invoke(this@Slider,progress,max)
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    }

                })
            }else{
                getWidget().setOnSeekBarChangeListener(null)
            }
        }
}