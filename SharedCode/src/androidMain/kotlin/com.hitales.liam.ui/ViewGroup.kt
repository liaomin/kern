package com.hitales.liam.ui


actual abstract class ViewGroup : View() {

    actual open fun addView(
        view: View,
        index: Int
    ) {

    }

}