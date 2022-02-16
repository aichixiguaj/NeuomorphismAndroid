package com.aichixigua.neuomorphism.shape.base

import android.graphics.*
import androidx.annotation.ColorInt
import com.aichixigua.neuomorphism.model.NEAttribute
import com.aichixigua.neuomorphism.ne_const.NELightPosition
import com.aichixigua.neuomorphism.ne_const.NEStateType
import com.aichixigua.neuomorphism.util.NEColorUtil


/**
 *@author：JYM
 *@description：基础属性
 *@email：aichixiguaj@qq.com
 *@date：2021/6/17 10:14
 */
abstract class NEShape(private val attrs: NEAttribute) : INEShapeEvent {

    // 光效(高光、暗光)、背景、边框画笔
    protected val paintBright = Paint(Paint.ANTI_ALIAS_FLAG)
    protected val paintDim = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintBackground = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintBorder = Paint(Paint.ANTI_ALIAS_FLAG)

    // 光效(高光、暗光)、 背景(边框)、路径
    protected var pathBright = Path()
    protected var pathDim = Path()
    protected var pathBackground = Path()

    protected var width: Float = 0f
    protected var height: Float = 0f

    // 光偏移方向
    private var lightOffsetX = -1
    private var lightOffsetY = -1

    // 背景光渐变方向
    private var bgStartX = 0f
    private var bgStartY = 0f
    private var bgEndX = 0f
    private var bgEndY = 0f

    init {
        handleLightOffset()
    }

    /**
     *  初始化画笔的颜色
     */
    override fun initPaint(width: Float, height: Float) {
        attrs.let {
            // 光效颜色
            val bgColor = it.background.bgColor
            paintBright.color = bgColor
            paintDim.color = bgColor
            // 边框
            paintBorder.color = it.border.Color
        }
        // 背景
        initBackgroundPaint(width, height)
    }

    /**
     * 绘制光效 背景  边框
     */
    override fun drawShape(canvas: Canvas) {
        // 光效
        if (attrs.stateType != NEStateType.PRESSED) {
            // 如果是按压状态就不需要光效
            canvas.drawPath(pathBright, paintBright)
            canvas.drawPath(pathDim, paintDim)
        }

        // 绘制背景
        canvas.drawPath(pathBackground, paintBackground)

        // 边框样式
        val borderAttr = attrs.border
        if (borderAttr.isShow) {
            val borderPaint = getStrokePaint(paintBorder, borderAttr.width, borderAttr.Color)
            canvas.drawPath(pathBackground, borderPaint)
        }
    }

    /**
     *  设置阴影
     *  @param paint 画笔
     *  @param color 阴影颜色
     */
    protected fun setShadowLayer(
        paint: Paint,
        cornerRadius: Float,
        offsetWidth: Float,
        @ColorInt color: Int
    ) {
        paint.setShadowLayer(
            cornerRadius, offsetWidth * lightOffsetX, offsetWidth * lightOffsetY, color
        )
    }

    /**
     *  处理光效偏移
     */
    private fun handleLightOffset() {
        when (attrs.lightEffect.lightPosition) {
            NELightPosition.LEFT_TOP -> {
                lightOffsetX = 1
                lightOffsetY = 1
            }
            NELightPosition.LEFT_BOTTOM -> {
                lightOffsetX = 1
                lightOffsetY = -1
            }
            NELightPosition.RIGHT_TOP -> {
                lightOffsetX = -1
                lightOffsetY = 1
            }
            NELightPosition.RIGHT_BOTTOM -> {
                lightOffsetX = -1
                lightOffsetY = -1
            }
        }
    }

    /**
     *  中空画笔
     *  @param paint 画笔
     *  @param width 宽度
     */
    private fun getStrokePaint(paint: Paint, width: Float, @ColorInt colorInt: Int): Paint {
        return paint.apply {
            strokeWidth = width
            style = Paint.Style.STROKE
            color = colorInt
        }
    }

    /**
     * 处理颜色渐变
     */
    private fun getLinearGradient(
        startColor: Int,
        endColor: Int,
        width: Float,
        height: Float
    ): LinearGradient {
        when (attrs.lightEffect.lightPosition) {
            NELightPosition.LEFT_TOP -> {
                bgEndX = width
                bgEndY = height
            }
            NELightPosition.LEFT_BOTTOM -> {
                bgStartY = height
                bgEndX = width
            }
            NELightPosition.RIGHT_TOP -> {
                bgStartX = width
                bgEndY = height
            }
            NELightPosition.RIGHT_BOTTOM -> {
                bgStartX = width
                bgStartY = height
            }
        }
        return LinearGradient(
            bgStartX, bgStartY, bgEndX, bgEndY,
            startColor, endColor,
            Shader.TileMode.CLAMP
        )
    }

    /**
     *  背景的光效
     */
    private fun initBackgroundPaint(width: Float, height: Float) {
        val lightEffect = attrs.lightEffect
        // 设置渐变背景样式
        when (attrs.stateType) {
            NEStateType.CONCAVE -> {
                // 表面凹陷但是周围有光
                paintBackground.shader =
                    getLinearGradient(lightEffect.dimColor, lightEffect.brightColor, width, height)
            }
            NEStateType.PRESSED -> {
                // 按下凹陷但是周围没有光
                paintBackground.shader =
                    getLinearGradient(lightEffect.dimColor, lightEffect.brightColor, width, height)
            }
            NEStateType.CONVEX -> {
                // 表明突出
                val computeColor = NEColorUtil.manipulateColor(lightEffect.brightColor, 0.965f)
                paintBackground.shader =
                    getLinearGradient(computeColor, lightEffect.dimColor, width, height)
            }
            else -> {
                // 普通样式平面背景
                paintBackground.color = attrs.background.bgColor
            }
        }
    }

}