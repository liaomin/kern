package com.hitales.ui.recycler

import com.hitales.ios.ui.HAttributes
import com.hitales.ios.ui.HLayout
import com.hitales.utils.Frame
import com.hitales.utils.Size
import com.hitales.utils.WeakReference
import kotlinx.cinterop.CValue
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRect
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSize
import platform.CoreGraphics.CGSizeMake
import platform.Foundation.NSIndexPath
import platform.UIKit.indexPathForRow

inline fun Frame.toCGRect():CValue<CGRect>{
    return CGRectMake(x.toDouble(),y.toDouble(),width.toDouble(),height.toDouble())
}

class IOSCollectionViewLayout(val collectionViewRef: WeakReference<CollectionView>) : HLayout(){

    val tempFrame = Frame()

    override fun prepareLayout() {
        super.prepareLayout()
        val collectionView = collectionViewRef.get()
        if(collectionView != null){
            collectionView.layout?.let {
                it.prepareLayout()
            }
        }
    }

    override fun layoutAttributesForElementsInRect(rect: CValue<CGRect>): List<*>? {
        val collectionView = collectionViewRef.get()
        if(collectionView != null){
            collectionView.layout?.let {
                rect.useContents {
                    tempFrame.set(origin.x.toFloat(),origin.y.toFloat(),size.width.toFloat(),size.height.toFloat())
                    val attrs = it.getLayoutAttributesInFrame(tempFrame)
                    return attrs.map {
                        val att = HAttributes.layoutAttributesForCellWithIndexPath(NSIndexPath.indexPathForRow(it.row.toLong(),it.section.toLong()))
                        att.frame = it.frame.toCGRect()
                        att.tag2 = it
                        return@map att
                    }
                }

            }
        }
        return super.layoutAttributesForElementsInRect(rect)
    }


    override fun collectionViewContentSize(): CValue<CGSize> {
        val collectionView = collectionViewRef.get()
        if(collectionView != null){
            collectionView.layout?.let {
                val size = Size()
                it.getContentSize(size)
                return CGSizeMake(size.width.toDouble(),size.height.toDouble())
            }
        }
        return super.collectionViewContentSize()
    }


}
