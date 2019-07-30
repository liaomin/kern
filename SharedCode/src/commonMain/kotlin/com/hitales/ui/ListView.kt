package com.hitales.ui

import com.hitales.utils.Frame
import com.hitales.utils.Size

abstract class SectionAdapter {

    open fun getSectionsCount():Int{
        return 1
    }

    abstract fun getCount(sectionIndex:Int):Int

    open fun getType(sectionIndex:Int,index:Int):Int{
        return  1
    }

    open fun getHeaderView():View?{
        return null
    }

    open fun getFooterView():View?{
        return null
    }

    open fun getHeaderSize(size:Size){

    }

    open fun getFooterSize(size:Size){

    }

    open fun getSectionHeaderView(sectionIndex:Int):View?{
        return null
    }

    open fun getSectionFooterView(sectionIndex: Int):View?{
        return null
    }

    open fun getSectionHeaderSize(sectionIndex:Int,size:Size){

    }

    open fun getSectionFooterSize(sectionIndex:Int,size:Size){

    }

    open fun getSize(sectionIndex:Int,index:Int,type:Int,size:Size){

    }

    abstract  fun getView(sectionIndex:Int,index:Int,type:Int,cacheView:View?):View

    open fun getSeparatorView(sectionIndex:Int,index:Int,type:Int):View?{
        return null
    }

    open fun getSeparatorSize(sectionIndex:Int,index:Int,size:Size){

    }

}

open class SectionList : ScrollView {

    companion object {
        val HEADER_TYPE = -0xFFFFFFF
        val SECTIONS_HEADER_TYPE = HEADER_TYPE - 1
        val SECTIONS_FOOTER_TYPE = SECTIONS_HEADER_TYPE - 1
        val FOOTER_TYPE = SECTIONS_FOOTER_TYPE - 1
        val _TYPE = SECTIONS_FOOTER_TYPE - 1
    }

    data class ViewNode(val view: View,val type: Int,val sectionIndex:Int,val index:Int)

    var adapter:SectionAdapter? = null
        set(value) {
            field = value
            reloadData()
        }

    constructor(frame: Frame = Frame.zero())

    protected val visibleViews = ArrayList<ViewNode>()

    protected val cacheViews = HashMap<Int,ArrayList<View>>()

    private val tempFrame = Frame.zero()

    private val tempSize = Size()

    fun getCacheArray(type:Int):ArrayList<View>{
        if(cacheViews.containsKey(type)){
            return cacheViews.get(type)!!
        }
        val array = ArrayList<View>()
        cacheViews[type] = array
        return array
    }

    open fun reloadData(){
        cleanChildren()
        //setContextSize
        var contentWidth = frame.width
        var contentHeight = 0f
        adapter?.apply {
            tempSize.reset()
            this.getHeaderSize(tempSize)
            contentHeight += tempSize.height
            tempSize.reset()
            this.getFooterSize(tempSize)
            contentHeight += tempSize.height
            val sectionsCount = this.getSectionsCount()
            if(sectionsCount > 0){
                for ( i in 0 until sectionsCount ){
                    tempSize.reset()
                    this.getSectionHeaderSize(i,tempSize)
                    contentHeight += tempSize.height
                    tempSize.reset()
                    this.getSectionFooterSize(i,tempSize)
                    contentHeight += tempSize.height

                    for ( j in 0 until this.getCount(i)){
                        tempSize.reset()
                        getSize(i,j,getType(i,j),tempSize)
                        if(j != 0){

                        }
                        contentHeight += tempSize.height
                    }
                }
            }
        }
        contentSize = Size(contentWidth,contentHeight)
        layoutSubViews(scrollX,scrollY)
    }

    fun cleanChildren(){
        visibleViews.forEach {
            getCacheArray(it.type).add(it.view)
            it.view.frame.width = 0f
            it.view.frame.height = 0f
        }
        visibleViews.clear()
        children.forEach {
            it.removeFromSuperView()
        }
    }

    override fun addSubView(view: View, index: Int) {
        throw RuntimeException("ListView not support addView method, instead of set adapter ")
    }

    fun addView(view: View,size: Size,offset:Float,type: Int,sectionIndex: Int,index: Int){
        val node = ViewNode(view,type, sectionIndex, index)
        visibleViews.add(node)
        val frame = view.frame
        frame.set(0f,offset-size.height,frame.width,size.height)
        view.frame = frame
        super.addSubView(view,-1)
    }

    override fun layoutSubViews(offsetX: Float, offsetY: Float) {
        getVisibleFrame(tempFrame)
        val adapter = this.adapter
        if(adapter != null){
            if(visibleViews.isEmpty()){
                initVisibleViews(adapter,offsetX,offsetY)
            }else{
                updateVisibleViews(adapter,offsetX,offsetY)
            }
        }
    }

    private fun updateVisibleViews(adapter: SectionAdapter,offsetX: Float, offsetY: Float){
        val first = visibleViews.first()
        var last = visibleViews.last()

    }


    private fun initVisibleViews(adapter: SectionAdapter,offsetX: Float, offsetY: Float){
        var offset = 0f
        tempSize.reset()
        adapter.getHeaderSize(tempSize)
        offset += tempSize.height
        var started = false

        if(offset > offsetY){
            val view = adapter.getHeaderView()
            if(view == null){
                throw RuntimeException("headerView is null,but size.height not zero")
            }else{
                addView(view,tempSize,offset,0xFFFFFF,-1,-1)
            }
        }
        val sectionsCount = adapter.getSectionsCount()
        if(sectionsCount > 0){
            for ( i in 0 until sectionsCount ){
                tempSize.reset()
                adapter.getSectionHeaderSize(i,tempSize)
                offset += tempSize.height
                tempSize.reset()
                if(offset > offsetY){
                    val view = adapter.getSectionHeaderView(i)
                    if(view == null){
                        throw RuntimeException("headerView is null,but size.height not zero")
                    }else{
                        addView(view,tempSize,offset,0xFFFFFF,i,-1)
                    }
                }
            }
        }
    }

}

open class ListView : SectionList {

    constructor(frame: Frame = Frame.zero())

}