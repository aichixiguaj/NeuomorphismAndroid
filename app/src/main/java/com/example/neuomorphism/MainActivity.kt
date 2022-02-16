package com.example.neuomorphism

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.aichixigua.neuomorphism.manager.NEBuilder
import com.aichixigua.neuomorphism.ne_const.NEDrawableType
import com.aichixigua.neuomorphism.ne_const.NEStateType
import com.aichixigua.neuomorphism.util.NEColorUtil
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author : JYM
 * @description : 主页
 * @email : aichixiguaj@qq.com
 * @date : 2021/6/25 15:32
 */
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val backgroundColor = Color.parseColor("#E0E0E0")

    override fun onResume() {
        super.onResume()

        llContainer.setBackgroundColor(backgroundColor)

        val brightColor = NEColorUtil.manipulateColor(backgroundColor, 1.095f)
        val dimColor = NEColorUtil.manipulateColor(backgroundColor, 0.94f)

        btn1.background = NEBuilder()
            .setType(NEStateType.PRESSED)
            .setCornerRadius(10f)
            .setLightColor(brightColor, dimColor)
            .buildDrawable()

        NEBuilder()
            .setType(NEStateType.FLAT)
            .setLightWidth(18f)
            .setCornerRadius(18f)
            .setBackgroundColor(backgroundColor)
            .setLightColor(brightColor, dimColor)
            .withView(btn2)

        btn3.background = NEBuilder()
            .setType(NEStateType.CONVEX)
            .setLightWidth(15f)
            .setCornerRadius(18f)
            .setLightColor(brightColor, dimColor)
            .buildDrawable()


        NEBuilder()
            .setType(NEStateType.CONCAVE)
            .setDrawableType(NEDrawableType.CIRCLE)
            .setBackgroundColor(backgroundColor)
            .setLightColor(brightColor, dimColor)
            .withView(btn4)
    }


}