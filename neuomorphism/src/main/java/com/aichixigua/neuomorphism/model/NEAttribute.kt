package com.aichixigua.neuomorphism.model

import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import com.aichixigua.neuomorphism.ne_const.NELightPosition
import  com.aichixigua.neuomorphism.ne_const.NEStateType

/**
 *@author：JYM
 *@description：拟物属性
 *@email：aichixiguaj@qq.com
 *@date：2021/6/17 15:05
 */
data class NEAttribute(
    var lightEffect: NELightEffect,
    var border: NEBorder,
    var background: NEBackground,
    var stateType: NEStateType
)

/**
 *  光效
 *  @param width 光效相对背景的宽度
 *  @param dimColor 暗色的光
 *  @param brightColor 高光
 *  @param lightPosition 光照射的位置
 */
data class NELightEffect(
    var width: Float,
    @ColorInt var brightColor: Int,
    @ColorInt var dimColor: Int,
    var lightPosition: NELightPosition = NELightPosition.LEFT_TOP
)

/**
 *  背景效果
 *  @param bgColor 背景颜色
 */
data class NEBackground(
    @ColorInt var bgColor: Int,
    @FloatRange(from = 0.0, to = 1.0) var alpha: Float
)

/**
 *  边框
 *  @param width 宽度
 *  @param Color 颜色
 *  @param isShow 是否显示
 */
data class NEBorder(
    var width: Float,
    @ColorInt var Color: Int,
    var isShow: Boolean = false
)
