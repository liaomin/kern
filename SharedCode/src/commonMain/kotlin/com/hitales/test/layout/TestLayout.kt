package com.hitales.test.layout

import com.hitales.test.TestViewController

class TestLayout : TestViewController(){

    override fun testView() {
        addButton("测试LinearLayout"){
            push(TestLinearLayout())
        }
    }

}