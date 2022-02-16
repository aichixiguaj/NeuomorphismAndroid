package com.aichixigua.neuomorphism.manager

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.aichixigua.neuomorphism.drawable.NECircleDrawable
import com.aichixigua.neuomorphism.drawable.NESquareDrawable
import com.aichixigua.neuomorphism.model.NEAttribute
import com.aichixigua.neuomorphism.model.NEBackground
import com.aichixigua.neuomorphism.model.NEBorder
import com.aichixigua.neuomorphism.model.NELightEffect
import com.aichixigua.neuomorphism.ne_const.NEDrawableType
import com.aichixigua.neuomorphism.ne_const.NELightPosition
import com.aichixigua.neuomorphism.ne_const.NEStateType
import com.aichixigua.neuomorphism.util.NEColorUtil

/**
 * @author : JYM
 * @description : 拟物构建
 * @email : aichixiguaj@qq.com
 * @date : 2021/7/1 17:06
 */
class NEBuilder : INEInitEvent {

    // 状态(表面平整 表面凹陷  表面凸出 按下)
    private var state = NEStateType.FLAT

    // 边框
    private lateinit var border: NEBorder

    // 光效
    private lateinit var lightEffect: NELightEffect

    // 背景
    private lateinit var background: NEBackground

    // 圆角值
    private var shapeRadius: Float = 0f

    // drawable类型
    private var drawableType = NEDrawableType.SQUARE

    // 默认颜色
    private var defaultColor = Color.parseColor("#FFFFFF")

    // 设置光效、背景的颜色
    private val brightColor = NEColorUtil.manipulateColor(defaultColor, 1.4f)
    private val dimColor = NEColorUtil.manipulateColor(defaultColor, 0.85f)
    private val backgroundColor = NEColorUtil.manipulateColor(defaultColor, 0.95f)

    init {
        if (!::lightEffect.isInitialized) {
            lightEffect = NELightEffect(10f, brightColor, dimColor)
        }
        if (!::border.isInitialized) {
            border = NEBorder(0f, dimColor, false)
        }
        if (!::background.isInitialized) {
            background = NEBackground(backgroundColor, 1f)
        }
    }

    override fun setLightWidth(width: Float): INEInitEvent {
        lightEffect.width = width
        return this
    }

    override fun setLightColor(brightColor: Int, dimColor: Int): INEInitEvent {
        lightEffect.brightColor = brightColor
        lightEffect.dimColor = dimColor
        return this
    }

    override fun setBackgroundColor(bgColor: Int): INEInitEvent {
        background.bgColor = bgColor
        return this
    }

    override fun setLightPosition(lightPosition: NELightPosition): INEInitEvent {
        lightEffect.lightPosition = lightPosition
        return this
    }

    override fun setBorderWidth(width: Float): INEInitEvent {
        border.width = width
        return this
    }

    override fun setBorderColor(Color: Int): INEInitEvent {
        border.Color = Color
        return this
    }

    override fun showBorder(isShow: Boolean): INEInitEvent {
        border.isShow = isShow
        return this
    }

    override fun setType(state: NEStateType): INEInitEvent {
        this.state = state
        return this
    }

    override fun setCornerRadius(cornerRadius: Float): INEInitEvent {
        this.shapeRadius = cornerRadius
        return this
    }

    override fun setDrawableType(type: NEDrawableType): INEInitEvent {
        this.drawableType = type
        return this
    }

    override fun buildDrawable() = getDrawable()

    override fun withView(view: View) {
        // 让父View不要裁剪子视图
        val parentView = view.parent as ViewGroup
        parentView.clipChildren = false
        view.background = getDrawable()
    }

    private fun getDrawable() = when (drawableType) {
        NEDrawableType.SQUARE -> {
            NESquareDrawable(
                shapeRadius,
                NEAttribute(lightEffect, border, background, state)
            )
        }
        NEDrawableType.CIRCLE -> {
            NECircleDrawable(NEAttribute(lightEffect, border, background, state))
        }
    }
}