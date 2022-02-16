package com.aichixigua.neuomorphism.shape

import android.graphics.Path
import com.aichixigua.neuomorphism.model.NEAttribute
import com.aichixigua.neuomorphism.ne_const.NEStateType
import com.aichixigua.neuomorphism.shape.base.NEShape
import kotlin.math.min

/**
 * @author : JYM
 * @description : 圆形
 * @email : aichixiguaj@qq.com
 * @date : 2021/7/1 10:01
 */
class NECircle(private val attrs: NEAttribute) : NEShape(attrs) {

    override fun init(w: Float, h: Float) {
        this.width = w
        this.height = h
        initPaths()
        initPaint(width, height)
    }

    override fun initPaint(width: Float, height: Float) {
        super.initPaint(width, height)
        attrs.let {
            // 光效颜色
            val lightAttr = it.lightEffect
            // 光效宽度(偏移值)
            val offsetWidth = lightAttr.width
            val shadowRadius = min(width, height) / 4
            setShadowLayer(paintBright, shadowRadius, -offsetWidth, lightAttr.brightColor)
            setShadowLayer(paintDim, shadowRadius, offsetWidth, lightAttr.dimColor)
        }
    }

    override fun initPaths() {
        handlePath(pathBright, true)
        handlePath(pathDim, true)
        handlePath(pathBackground, false)
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
            val radius = min(width, height) / 2
            addCircle(width / 2, height / 2, radius, Path.Direction.CW)
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