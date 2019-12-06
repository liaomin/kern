package com.hitales.ui.android

import android.content.res.ColorStateList
import android.util.StateSet
import com.hitales.ui.Colors
import com.hitales.ui.ViewState


inline fun ViewState.toStateSet():IntArray{
    val stateSet:IntArray
    when(this){
        ViewState.FOCUSED->stateSet = intArrayOf(android.R.attr.state_focused)
        ViewState.PRESSED->stateSet = intArrayOf(android.R.attr.state_pressed)
        ViewState.SELECTED->stateSet = intArrayOf(android.R.attr.state_selected)
        ViewState.DISABLED->stateSet = intArrayOf(-android.R.attr.state_enabled)
        else -> {
            stateSet = intArrayOf(android.R.attr.state_enabled)
        }
    }
    return stateSet
}

open class StateListColor(color:Int) : ColorStateList(arrayOf(IntArray(0)), intArrayOf(color)),Comparator<StateListColor.MStateColor>{

    class MStateColor(val state:ViewState,val color:Int){
        val stateSet:IntArray = state.toStateSet()
    }

    override fun compare(o1: MStateColor, o2: MStateColor): Int {
        if(o1.state.equals(ViewState.NORMAL)){
            return -1
        }else if(o2.state.equals(ViewState.NORMAL)){
            return 1
        }
        return 1
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
        stateColors.sortWith(this)
    }

    override fun getColorForState(stateSet: IntArray?, defaultColor: Int): Int {
        if(stateSet != null){
            for (i in stateColors.size - 1 downTo 0 ){
                val temp = stateColors[i]
                if(StateSet.stateSetMatches(temp.stateSet,stateSet)){
                    return temp.color
                }
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