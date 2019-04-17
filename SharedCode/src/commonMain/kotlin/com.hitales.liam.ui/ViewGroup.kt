package com.hitales.liam.ui

expect abstract class ViewGroup : View {

    open fun addView(view: View, index:Int = -1, layoutParams: LayoutParams? = null)

}