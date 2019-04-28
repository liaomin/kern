package com.hitales.ui

import android.content.res.ColorStateList


open class StateListColor(color:Int) : ColorStateList(arrayOf(IntArray(0)), intArrayOf(color)){

    private val colors:IntArray = IntArray(ViewState.SELECTED.value +1 )

    init {
        this.setColor(color)
    }

    override fun getColorForState(stateSet: IntArray?, defaultColor: Int): Int {
        if(stateSet != null){
            var enable = false
            for ( i in 0 until stateSet.size){
                var state = stateSet[i]
                if(state == android.R.attr.state_enabled){
                    enable = true
                    continue
                }
                when (state){
                    android.R.attr.state_focused -> return getColorForState(ViewState.FOCUSED)
                    android.R.attr.state_pressed -> return getColorForState(ViewState.PRESSED)
                    android.R.attr.state_selected -> return getColorForState(ViewState.SELECTED)
                }
            }
            if(!enable) return getColorForState(ViewState.DISABLED)
        }
        return getColorForState(ViewState.NORMAL)
    }

    override fun getDefaultColor(): Int {
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

    open fun getColorForState(state: ViewState):Int{
        return colors[state.value]
    }

    override fun isStateful(): Boolean {
        return true
    }

}