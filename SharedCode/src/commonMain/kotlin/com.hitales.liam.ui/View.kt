package com.hitales.liam.ui

expect open class View  {
    fun setId(id:Int)
    fun getId():Int
    fun setTag(tag:Any?)
    fun getTag():Any?
    /**
     * argb color
     */
    fun setBackgroundColor(color:Long)
}


expect open class TextView : View {
    constructor(text:CharSequence? = null)
    fun setText(text:CharSequence?)
    fun getText():CharSequence?

}

