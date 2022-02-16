package com.aichixigua.neuomorphism.util

import android.graphics.Color
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * @author : JYM
 * @description : 颜色工具
 * @email : aichixiguaj@qq.com
 * @date : 2021/7/1 17:26
 */
object NEColorUtil {

    /**
     *  处理颜色
     */
    fun manipulateColor(color: Int, factor: Float): Int {
        val a = Color.alpha(color)
        val r = (Color.red(color) * factor).roundToInt()
        val g = (Color.green(color) * factor).roundToInt()
        val b = (Color.blue(color) * factor).roundToInt()
        return Color.argb(
            a,
            min(r, 255),
            min(g, 255),
            min(b, 255)
        )
    }

}