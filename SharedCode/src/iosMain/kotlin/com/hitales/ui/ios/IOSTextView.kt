package com.hitales.ui.ios

import com.hitales.ui.TextView
import com.hitales.utils.WeakReference
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UILabel

class IOSTextView(protected val mView: WeakReference<TextView>) : UILabel(CGRectMake(0.0,0.0,0.0,0.0)) {

}