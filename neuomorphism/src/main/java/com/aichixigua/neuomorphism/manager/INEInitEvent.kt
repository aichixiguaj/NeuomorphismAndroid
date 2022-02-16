package com.aichixigua.neuomorphism.manager

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import com.aichixigua.neuomorphism.ne_const.NEDrawableType
import com.aichixigua.neuomorphism.ne_const.NELightPosition
import com.aichixigua.neuomorphism.ne_const.NEStateType

/**
 * @author : JYM
 * @description : 拟物属性设置
 * @email : aichixiguaj@qq.com
 * @date : 2021/6/18 11:34
 */
interface INEInitEvent {

    // 光效宽度
    fun setLightWidth(width: Float): INEInitEvent

    // 设置光效颜色
    fun setLightColor(@ColorInt brightColor: Int, @ColorInt dimColor: Int): INEInitEvent

    // 背景颜色
    fun setBackgroundColor(@ColorInt bgColor: Int): INEInitEvent

    // 设置光效位置
    fun setLightPosition(lightPosition: NELightPosition): INEInitEvent

    // 边框宽度
    fun setBorderWidth(width: Float): INEInitEvent

    // 边框颜色
    fun setBorderColor(@ColorInt Color: Int): INEInitEvent

    // 显示边框
    fun showBorder(isShow: Boolean): INEInitEvent

    // 状态类型
    fun setType(state: NEStateType): INEInitEvent

    // 设置圆角
    fun setCornerRadius(cornerRadius: Float): INEInitEvent

    // 设置Drawable 类型
    fun setDrawableType(type: NEDrawableType):INEInitEvent

    // 构建Drawable
    fun buildDrawable():Drawable

    // 绑定View
    fun withView(view: View)

}