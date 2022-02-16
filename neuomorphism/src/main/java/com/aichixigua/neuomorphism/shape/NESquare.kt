package com.aichixigua.neuomorphism.shape

import android.graphics.Path
import com.aichixigua.neuomorphism.model.NEAttribute
import com.aichixigua.neuomorphism.ne_const.NEStateType
import com.aichixigua.neuomorphism.shape.base.NEShape

/**
 * @author : JYM
 * @description : 四边形
 * @email : aichixiguaj@qq.com
 * @date : 2021/6/21 10:36
 */
open class NESquare(
    private var cornerRadius: Float = 0f,
    private val attrs: NEAttribute
) : NEShape(attrs) {

    override fun init(w: Float, h: Float) {
        this.width = w
        this.height = h
        initPaths()
        initPaint(width, height)
    }

    /**
     *  初始化画笔
     */
    override fun initPaths() {
        handlePath(pathBright, true)
        handlePath(pathDim, true)
        handlePath(pathBackground, false)
    }

    /**
     *  初始化画笔
     */
    override fun initPaint(width: Float, height: Float) {
        super.initPaint(width, height)
        attrs.let {
            // 设置光效颜色
            val lightAttr = it.lightEffect
            // 光效宽度(偏移值)
            val offsetWidth = lightAttr.width
            val shadowRadius = cornerRadius + lightAttr.width * 0.9f
            setShadowLayer(paintBright, shadowRadius, -offsetWidth, lightAttr.brightColor)
            setShadowLayer(paintDim, shadowRadius, offsetWidth, lightAttr.dimColor)
        }
    }

    /**
     *  处理线路
     */
    private fun handlePath(
        path: Path,
        isBright: Boolean
    ) {
        path.apply {
            reset()
            addRoundRect(
                0f, 0f, width, height,
                cornerRadius, cornerRadius, Path.Direction.CW
            )
            if (attrs.stateType == NEStateType.PRESSED && isBright) {
                // 如果当前状态已经按下并且是光效
                if (!isInverseFillType) {
                    toggleInverseFillType()
                }
            }
            close()
        }
    }

}