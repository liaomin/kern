package com.hitales.ui.android

import android.graphics.Paint.FontMetricsInt
import android.text.style.LineHeightSpan

/**
 * set android text lineHeight
 * copy from react native source code
 */
class CustomLineHeightSpan internal constructor(height: Float) : LineHeightSpan {
    private val mHeight: Int
    override fun chooseHeight(text: CharSequence, start: Int, end: Int, spanstartv: Int, v: Int, fm: FontMetricsInt) { // This is more complicated that I wanted it to be. You can find a good explanation of what the
        // FontMetrics mean here: http://stackoverflow.com/questions/27631736.
        // The general solution is that if there's not enough height to show the full line height, we
        // will prioritize in this order: descent, ascent, bottom, top
        if (fm.descent > mHeight) { // Show as much descent as possible
            fm.descent = Math.min(mHeight, fm.descent)
            fm.bottom = fm.descent
            fm.ascent = 0
            fm.top = fm.ascent
        } else if (-fm.ascent + fm.descent > mHeight) {
            // Show all descent, and as much ascent as possible
            fm.bottom = fm.descent
            fm.ascent = -mHeight + fm.descent
            fm.top = fm.ascent
        } else if (-fm.ascent + fm.bottom > mHeight) {
            // Show all ascent, descent, as much bottom as possible
            fm.top = fm.ascent
            fm.bottom = fm.ascent + mHeight
        } else if (-fm.top + fm.bottom > mHeight) {
            // Show all ascent, descent, bottom, as much top as possible
            fm.top = fm.bottom - mHeight
        } else {
            // Show proportionally additional ascent / top & descent / bottom
            val additional = mHeight - (-fm.top + fm.bottom)
            // Round up for the negative values and down for the positive values  (arbritary choice)
            // So that bottom - top equals additional even if it's an odd number.
            fm.top -= Math.ceil(additional / 2.0f.toDouble()).toInt()
            fm.bottom += Math.floor(additional / 2.0f.toDouble()).toInt()
            fm.ascent = fm.top
            fm.descent = fm.bottom
        }
    }

    init {
        mHeight = Math.ceil(height.toDouble()).toInt()
    }
}
