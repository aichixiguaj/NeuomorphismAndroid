package com.aichixigua.neuomorphism.drawable.base

import android.graphics.*
import android.graphics.drawable.Drawable
import com.aichixigua.neuomorphism.model.NEAttribute

/**
 * @author : JYM
 * @description : 基础Drawable
 * @email : aichixiguaj@qq.com
 * @date : 2021/7/1 16:14
 */
abstract class NEDrawable(private val attr: NEAttribute) : Drawable() {

    protected var shapeWidth: Float = 0f
    protected var shapeHeight: Float = 0f

    override fun onBoundsChange(bounds: Rect?) {
        super.onBoundsChange(bounds)
        bounds?.let {
            initSize(it)
        }
    }

    private fun initSize(rect: Rect) {
        shapeWidth = rect.width().toFloat()
        shapeHeight = rect.height().toFloat()
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setAlpha(alpha: Int) {

    }

    override fun setColorFilter(colorFilter: ColorFilter?) {

    }

}