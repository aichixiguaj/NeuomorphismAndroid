package com.example.neuomorphism;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * 网上找的例子
 * 不好之处就是处处嵌套
 */
public class NeumorphicFrameLayout extends FrameLayout {

    // 长方形
    private Shape shape = Shape.RECTANGLE;
    // 表明状态:平面
    private State state = State.FLAT;
    // 计算层级
    private int level;
    // 路径
    private Path pathBase;
    private Path pathBright;
    private Path pathDim;
    // 画笔
    private Paint paintBase;
    private Paint paintBright;
    private Paint paintDim;
    // 背景颜色
    private int bgColor;
    private int brightColor;
    private int dimColor;
    // 宽高
    private float w;
    private float h;
    // 圆角半径
    private float cornerRadius;
    // 偏移值
    private float offset = DimensionsUtil.convertDpToPixel(10f, getContext());

    public NeumorphicFrameLayout(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public NeumorphicFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NeumorphicFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            // 获取自定义属性
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NeumorphicFrameLayout);
            // 解析形状
            String type = a.getString(R.styleable.NeumorphicFrameLayout_shape);
            if (type != null) {
                switch (type) {
                    case "0":
                        shape = Shape.RECTANGLE;
                        break;
                    case "1":
                        shape = Shape.CIRCLE;
                }
            }
            // 解析平面状态
            String stateType = a.getString(R.styleable.NeumorphicFrameLayout_state);
            if (stateType != null) {
                switch (stateType) {
                    case "0":
                        state = State.FLAT;
                        break;
                    case "1":
                        state = State.CONCAVE;
                        break;
                    case "2":
                        state = State.CONVEX;
                        break;
                    case "3":
                        state = State.PRESSED;
                        break;
                }
            }
            // 颜色（背景，高亮，暗光）
            bgColor = a.getColor(R.styleable.NeumorphicFrameLayout_background_color, Color.WHITE);
            brightColor = manipulateColor(bgColor, 1.1f);
            dimColor = manipulateColor(bgColor, 0.9f);
            // 圆角弧度
            cornerRadius = a.getDimensionPixelSize(R.styleable.NeumorphicFrameLayout_corner_radius, 0);
            a.recycle();
        }
        // 计算层级
        level = calculateLevel();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取宽高
        w = getMeasuredWidth();
        h = getMeasuredHeight();
        // 初始化线路和这些条件
        reset();

        clipParent();
    }

    private void clipParent() {
        ViewGroup parentView = (ViewGroup) getParent();
        if (parentView != null) {
            parentView.setClipChildren(false);
        }
    }

    /**
     * 初始化设置
     */
    private void reset() {
        // 初始化路径
        pathBase = new Path();
        pathBright = new Path();
        pathDim = new Path();
        // 初始化画笔
        paintBase = new Paint(ANTI_ALIAS_FLAG);
        paintBright = new Paint(ANTI_ALIAS_FLAG);
        paintDim = new Paint(ANTI_ALIAS_FLAG);
        // 初始化形状
        resetShapes();
        // 初始化画笔
        initPaints();
    }

    /**
     * 设置路径
     */
    private void resetShapes() {
        pathBase.reset();
        pathBright.reset();
        pathDim.reset();
        if (shape == Shape.RECTANGLE) {
            // 添加圆角矩形
            pathBase.addRoundRect(0f, 0f, w, h, cornerRadius, cornerRadius, Path.Direction.CW);
            pathBright.addRoundRect(0f, 0f, w, h, cornerRadius, cornerRadius, Path.Direction.CW);
            pathDim.addRoundRect(0f, 0f, w, h, cornerRadius, cornerRadius, Path.Direction.CW);
        } else {
            // 添加圆形
            float radius = h < w ? h / 2 : w / 2;
            pathBase.addCircle(w / 2, h / 2, radius, Path.Direction.CW);
            pathBright.addCircle(w / 2, h / 2, radius, Path.Direction.CW);
            pathDim.addCircle(w / 2, h / 2, radius, Path.Direction.CW);
        }
        // 判断状态
        if (state == State.PRESSED) {
            // 确定填充模式
            if (!pathBright.isInverseFillType()) {
                pathBright.toggleInverseFillType();
            }
            if (!pathDim.isInverseFillType()) {
                pathDim.toggleInverseFillType();
            }
        }
        // 闭环path(连接到最后一个点)
        pathBase.close();
        pathBright.close();
        pathDim.close();
    }

    /**
     * 初始化画笔
     */
    private void initPaints() {
        // 阴影半径
        float shadowRadius = DimensionsUtil.convertDpToPixel(20, getContext());
        // 控制阴影放大还是缩小
        float amplifier = level * (shadowRadius / 10) * (state == State.PRESSED ? -1 : 1);
        if (state == State.PRESSED || state == State.FLAT) {
            //  按下或者 表面平整
            paintBase.setColor(bgColor);
            paintBright.setColor(bgColor);
            paintDim.setColor(bgColor);
            Log.e("TAG", "表面样式按下或者平面");
        } else if (state == State.CONCAVE) {
            // 表面凹陷
            LinearGradient linearGradient = new LinearGradient(
                    0f, 0f, w, h,
                    dimColor, brightColor,
                    Shader.TileMode.CLAMP);
            paintBase.setShader(linearGradient);
            paintBright.setColor(bgColor);
            paintDim.setColor(bgColor);
            Log.e("TAG", "表面样式凹陷");
        } else {
            LinearGradient linearGradient = new LinearGradient(
                    0f, 0f, w, h,
                    brightColor, dimColor,
                    Shader.TileMode.CLAMP);
            paintBase.setShader(linearGradient);
            paintBright.setColor(bgColor);
            paintDim.setColor(bgColor);
            Log.e("TAG", "表面样式其它");
        }
        shadowRadius += amplifier;
        // 绘制阴影
        paintBright.setShadowLayer(shadowRadius, -offset, -offset, brightColor);
        paintDim.setShadowLayer(shadowRadius, offset, offset, dimColor);
    }

    /**
     * 计算层级
     *
     * @return 层级
     */
    private int calculateLevel() {
        // 获取父容器
        ViewParent parent = getParent();
        NeumorphicFrameLayout neoParent = null;
        // 如果父容器不为空
        while (parent != null) {
            // 并且父容器是拟物容器
            if (parent instanceof NeumorphicFrameLayout) {
                neoParent = (NeumorphicFrameLayout) parent;
                break;
            }
            parent = parent.getParent();
        }
        if (neoParent == null) {
            // 如果不是拟物容器
            return levelFromState();
        } else {
            // 如果是拟物容器 + 按下的状态
            return neoParent.getLevel() + levelFromState();
        }
    }

    /**
     * 状态
     */
    private int levelFromState() {
        if (state == State.PRESSED) {
            // 如果状态是按下
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (state == State.PRESSED) {
            canvas.clipPath(pathBase);
            canvas.drawPath(pathBase, paintBase);
            canvas.drawPath(pathBright, paintBright);
            Log.e("TAG", "绘制高光 按下");
            canvas.drawPath(pathDim, paintDim);
        } else {
            Log.e("TAG", "绘制高光");
            canvas.drawPath(pathBright, paintBright);
            canvas.drawPath(pathDim, paintDim);
            canvas.drawPath(pathBase, paintBase);
        }
        super.dispatchDraw(canvas);
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
        reset();
        invalidate();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        reset();
        invalidate();
    }

    public int getLevel() {
        return level;
    }

    private static int manipulateColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255));
    }

    public enum Shape {
        CIRCLE, RECTANGLE
    }

    public enum State {
        FLAT, CONCAVE, CONVEX, PRESSED
    }
}
