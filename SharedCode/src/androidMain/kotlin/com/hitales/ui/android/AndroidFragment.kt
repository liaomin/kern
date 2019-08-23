package com.hitales.ui.android

import android.animation.Animator
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

abstract class AndroidFragment:Fragment(){

    companion object {
        const val ENTER_ANIMATOR_ID = -2
        const val EXIT_ANIMATOR_ID = -3
    }

    var index = -1

    open fun setupTransaction(transaction: FragmentTransaction){
//        transaction.setCustomAnimations(ENTER_ANIMATOR_ID,EXIT_ANIMATOR_ID)
    }

    override fun setSharedElementEnterTransition(transition: Any?) {
        super.setSharedElementEnterTransition(transition)
    }

    override fun setEnterTransition(transition: Any?) {
        super.setEnterTransition(transition)
    }

    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
        return null
    }

}