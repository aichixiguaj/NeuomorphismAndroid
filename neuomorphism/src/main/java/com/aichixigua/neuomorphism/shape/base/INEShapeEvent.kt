package com.aichixigua.neuomorphism.shape.base

import android.graphics.Canvas

/**
 * @author : JYM
 * @description : 初始化事件
 * @email : aichixiguaj@qq.com
 * @date : 2021/6/29 14:34
 */
interface INEShapeEvent {

    /**
     *  初始化
     */
    fun init(w: Float, h: Float)

    /**
     *  初始化画笔
     */
    fun initPaint(width: Float, height: Float)

    /**
     *  初始化路径
     */
    fun initPaths()

    /**
     *  绘制形状
     */
    fun drawShape(canvas: Canvas)

}