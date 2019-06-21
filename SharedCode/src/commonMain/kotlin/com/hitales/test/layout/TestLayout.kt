package com.hitales.test.layout

import com.hitales.test.BasicController
import com.hitales.test.TestBorderAndTextColor
import com.hitales.test.TestViewController
import com.hitales.ui.Colors
import com.hitales.ui.LinearViewGroup
import com.hitales.ui.Platform
import com.hitales.ui.ViewGroup
import com.hitales.ui.layout.LinearLayoutManager
import com.hitales.utils.Frame

class TestLayout : TestViewController(){

    override fun testView() {
        addButton("测试LinearLayout"){
            push(TestLinearLayout())
        }
    }

}