package com.hitales.ui.ios

import com.hitales.ui.ViewState
import platform.UIKit.*


open class StateListColor(color:Int) {

    private val colors:IntArray = IntArray(ViewState.SELECTED.value + 1 )

    init {
        this.setColor(color)
    }

    fun getColorForState(state:UIControlState): Int {
        if(state != null){
            when(state){
                UIControlStateNormal -> return getColorForState(ViewState.NORMAL)
                UIControlStateHighlighted -> return getColorForState(ViewState.PRESSED)
                UIControlStateDisabled -> return getColorForState(ViewState.DISABLED)
                UIControlStateSelected -> return getColorForState(ViewState.SELECTED)
                UIControlStateFocused -> return getColorForState(ViewState.FOCUSED)
            }
        }
        return getColorForState(ViewState.NORMAL)
    }

    fun setColorForState(color:Int,state: ViewState): StateListColor {
        colors[state.value] = color
        return this
    }

    fun setColor(color:Int): StateListColor {
        for (i in 0 .. ViewState.SELECTED.value){
            colors[i] = color
        }
        return this
    }

    override fun toString(): String {
        return "{NORMAL:${colors[0]},PRESSED:${colors[1]},FOCUSED:${colors[2]},DISABLED:${colors[3]},SELECTED:${colors[4]}}"
    }

    open fun getColorForState(state: ViewState):Int{
        return colors[state.value]
    }
}