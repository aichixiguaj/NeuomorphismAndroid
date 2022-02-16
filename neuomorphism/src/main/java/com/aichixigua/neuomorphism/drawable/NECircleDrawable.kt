package com.aichixigua.neuomorphism.drawable

import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Rect
import com.aichixigua.neuomorphism.drawable.base.NEDrawable
import com.aichixigua.neuomorphism.model.NEAttribute
import com.aichixigua.neuomorphism.shape.NECircle

/**
 * @author : JYM
 * @description : 圆形Drawable
 * @email : aichixiguaj@qq.com
 * @date : 2021/7/1 14:02
 */
class NECircleDrawable(
    attr: NEAttribute
) : NEDrawable(attr) {

    private var circle: NECircle = NECircle(attr)

    override fun draw(canvas: Canvas) {
        circle.drawShape(canvas)
    }

    override fun onBoundsChange(bounds: Rect?) {
        super.onBoundsChange(bounds)
        bounds?.let {
            circle.init(shapeWidth, shapeHeight)
        }
    }
}