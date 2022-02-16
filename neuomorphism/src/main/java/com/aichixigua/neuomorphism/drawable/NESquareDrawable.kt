package com.aichixigua.neuomorphism.drawable

import android.graphics.*
import com.aichixigua.neuomorphism.drawable.base.NEDrawable
import com.aichixigua.neuomorphism.model.NEAttribute
import com.aichixigua.neuomorphism.shape.NESquare

/**
 * @author : JYM
 * @description : 四边形Drawable
 * @email : aichixiguaj@qq.com
 * @date : 2021/6/18 16:24
 */
class NESquareDrawable(
    cornerRadius: Float,
    attr: NEAttribute
) : NEDrawable(attr) {

    private var square: NESquare = NESquare(cornerRadius, attr)

    override fun draw(canvas: Canvas) {
        square.drawShape(canvas)
    }

    override fun onBoundsChange(bounds: Rect?) {
        super.onBoundsChange(bounds)
        bounds?.let {
            square.init(it.width().toFloat(), it.height().toFloat())
        }
    }

}