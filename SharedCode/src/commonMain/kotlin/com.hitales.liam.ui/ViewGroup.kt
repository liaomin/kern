package com.hitales.liam.ui

expect abstract class ViewGroup : View {

    val children:ArrayList<View>

    open fun addView(view: View, index:Int = -1)

}