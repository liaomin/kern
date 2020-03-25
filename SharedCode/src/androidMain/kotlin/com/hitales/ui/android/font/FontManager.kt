package com.hitales.ui.android.font

import android.content.res.AssetManager
import android.graphics.Typeface
import android.util.SparseArray
import java.util.*

class FontManager {
    private val EXTENSIONS = arrayOf(
        "",
        "_bold",
        "_italic",
        "_bold_italic"
    )
    private val FILE_EXTENSIONS = arrayOf(".ttf", ".otf")
    private val FONTS_ASSET_PATH = "fonts/"

    private var sFontManagerInstance: FontManager? = null

    private var mFontCache: MutableMap<String, FontFamily>

    private constructor() {
        mFontCache = HashMap()
    }

    fun getInstance():FontManager {
        if (sFontManagerInstance == null) {
            sFontManagerInstance = FontManager()
        }
        return sFontManagerInstance!!
    }

    fun getTypeface(
        fontFamilyName: String,
        style: Int,
        assetManager: AssetManager
    ): Typeface? {
        var fontFamily = mFontCache[fontFamilyName]
        if (fontFamily == null) {
            fontFamily = FontFamily()
            mFontCache[fontFamilyName] = fontFamily
        }
        var typeface: Typeface? = fontFamily.getTypeface(style)
        if (typeface == null) {
            typeface = createTypeface(fontFamilyName, style, assetManager)
            if (typeface != null) {
                fontFamily.setTypeface(style, typeface)
            }
        }
        return typeface
    }

    /**
     * Add additional font family, or replace the exist one in the font memory cache.
     * @param style
     * @see {@link Typeface#DEFAULT}
     * @see {@link Typeface#BOLD}
     * @see {@link Typeface#ITALIC}
     * @see {@link Typeface#BOLD_ITALIC}
     */
    fun setTypeface(fontFamilyName: String, style: Int, typeface: Typeface?) {
        if (typeface != null) {
            var fontFamily = mFontCache[fontFamilyName]
            if (fontFamily == null) {
                fontFamily = FontFamily()
                mFontCache[fontFamilyName] = fontFamily
            }
            fontFamily.setTypeface(style, typeface)
        }
    }

    private fun createTypeface(
        fontFamilyName: String,
        style: Int,
        assetManager: AssetManager
    ): Typeface? {
        val extension = EXTENSIONS[style]
        for (fileExtension in FILE_EXTENSIONS) {
            val fileName = StringBuilder()
                .append(FONTS_ASSET_PATH)
                .append(fontFamilyName)
                .append(extension)
                .append(fileExtension)
                .toString()
            try {
                return Typeface.createFromAsset(assetManager, fileName)
            } catch (e: RuntimeException) { // unfortunately Typeface.createFromAsset throws an exception instead of returning null
// if the typeface doesn't exist
            }
        }
        return Typeface.create(fontFamilyName, style)
    }

    private class FontFamily {
        private val mTypefaceSparseArray: SparseArray<Typeface>
        fun getTypeface(style: Int): Typeface {
            return mTypefaceSparseArray[style]
        }

        fun setTypeface(style: Int, typeface: Typeface) {
            mTypefaceSparseArray.put(style, typeface)
        }

        init {
            mTypefaceSparseArray = SparseArray(4)
        }
    }
}