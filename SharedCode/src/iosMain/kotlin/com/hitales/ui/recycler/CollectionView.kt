package com.hitales.ui.recycler

import com.hitales.ui.ScrollView
import com.hitales.utils.Frame
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UICollectionView
import platform.UIKit.UICollectionViewCell
import platform.UIKit.UICollectionViewDelegateFlowLayoutProtocol
import platform.UIKit.UIScrollView
import platform.darwin.NSObject

class IOSCell : UICollectionViewCell(CGRectMake(0.0,0.0,0.0,0.0)){

}

actual open class CollectionView : ScrollView {

    actual val layout: CollectionViewLayout

    actual var adapter: CollectionViewAdapter? = null
        set(value) {
            field = value
            if(value != null){

            }else{
                getWidget().delegate = null
            }
        }


    val uiCollectionViewDelegate:UICollectionViewDelegateFlowLayoutProtocol = object:NSObject(),UICollectionViewDelegateFlowLayoutProtocol{

    }

    actual constructor(frame: Frame, layout: CollectionViewLayout):super(frame){
        this.layout = layout
    }

    override fun createWidget(): UIScrollView {
        var widget = UICollectionView(CGRectMake(0.0,0.0,0.0,0.0),IOSCollectionViewLayout())
        widget.delegate = uiCollectionViewDelegate
        widget.registerClass(UICollectionViewCell.`class`(),"cell")
        return widget
    }

    override fun getWidget(): UICollectionView {
        return super.getWidget() as UICollectionView
    }

    actual fun reloadData() {
        getWidget().reloadData()
    }

}