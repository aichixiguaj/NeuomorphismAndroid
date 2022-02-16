package com.aichixigua.neuomorphism.ne_const

/**
 *  光源位置
 *  LEFT_TOP (左上角)
 *  LEFT_BOTTOM (左下角)
 *  RIGHT_TOP (右上角)
 *  RIGHT_BOTTOM (右下角)
 */
enum class NELightPosition {
    LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
}

/**
 *  主体样式
 *  FLAT (表明平整)
 *  CONCAVE (表面中部凹陷)
 *  CONVEX (表面中部凸出)
 *  PRESSED (按下)
 */
enum class NEStateType {
    FLAT, CONCAVE, CONVEX, PRESSED
}

/**
 *  Drawable的形状
 *  SQUARE 矩形
 *  CIRCLE 圆形
 */
enum class NEDrawableType{
    SQUARE,CIRCLE
}