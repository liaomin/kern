package com.hitales.ui.android

import android.content.res.ColorStateList
import android.util.StateSet
import com.hitales.ui.Colors
import com.hitales.ui.ViewState


open class StateListColor(color:Int) : ColorStateList(arrayOf(IntArray(0)), intArrayOf(color)){

    class MStateColor(val state:ViewState,val color:Int){
        val stateSet:IntArray
        init {
            when(state){
                ViewState.FOCUSED->stateSet = intArrayOf(android.R.attr.state_focused)
                ViewState.PRESSED->stateSet = intArrayOf(android.R.attr.state_pressed)
                ViewState.SELECTED->stateSet = intArrayOf(android.R.attr.state_selected)
                ViewState.DISABLED->stateSet = intArrayOf(-android.R.attr.state_enabled)
                else -> {
                    stateSet = intArrayOf(android.R.attr.state_enabled)
                }
            }
        }
    }

    private val stateColors = ArrayList<MStateColor>()

    fun getColorsSize():Int{
        return stateColors.size
    }

    init {
        this.setColorForState(color)
    }

    private fun addStateColor(state:ViewState,color:Int){
        var i = 0
        while ( i < stateColors.size){
            val temp = stateColors[i]
            if(temp.state.equals(state)){
                stateColors.removeAt(i)
            }else{
                i++
            }
        }
        stateColors.add(MStateColor(state,color))
    }

    override fun getColorForState(stateSet: IntArray?, defaultColor: Int): Int {
        if(stateSet != null){
            if(stateColors.size > 1){
                for (i in 0 until stateSet.size) print("${stateSet[i]}  ")
                println()
            }
            var normalStateColor:MStateColor? = null
            for (i in stateColors.size - 1 downTo 0 ){
                val temp = stateColors[i]
                val isNormal = temp.state.equals(ViewState.NORMAL)
                if(normalStateColor == null && isNormal){
                    normalStateColor = temp
                }
                if(!isNormal && StateSet.stateSetMatches(temp.stateSet,stateSet)){
                    return temp.color
                }
            }
            if(normalStateColor != null){
                return  normalStateColor.color
            }
        }
        return getColorForState(ViewState.NORMAL)
    }

    override fun getDefaultColor(): Int {
        return getColorForState(ViewState.NORMAL)
    }

    fun setColorForState(color:Int,state: ViewState = ViewState.NORMAL): StateListColor {
        addStateColor(state, color)
        return this
    }

    open fun getColorForState(state: ViewState):Int{
        for (i in stateColors.size - 1 downTo 0 ){
            val temp = stateColors[i]
            if(temp.state.equals(state)){
                return temp.color
            }
        }
        return Colors.TRANSPARENT
    }

    override fun isStateful(): Boolean {
        return true
    }

}