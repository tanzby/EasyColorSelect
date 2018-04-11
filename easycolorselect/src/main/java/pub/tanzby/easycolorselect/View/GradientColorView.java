package pub.tanzby.easycolorselect.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by tan on 2018/4/10.
 */

public class GradientColorView extends View {

    int cur_color = Color.GREEN;
    Paint mPaint=new Paint();
    Shader mShader;

    boolean mIsColorPickerMode;

    float mCurrentPosition;

    public static final int[]
    COLORS = new int[]{
            Color.WHITE,
            Color.RED,
            Color.YELLOW,
            Color.GREEN,
            Color.CYAN,
            Color.BLUE,
            Color.MAGENTA,
            Color.BLACK

    };

    public GradientColorView(Context context) {
        this(context,null);
    }

    public GradientColorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mIsColorPickerMode = true;
        mPaint.setAntiAlias(true);
    }

    public Boolean isColorPickerMode(){
        return this.mIsColorPickerMode;
    }


    /**
     * 根据颜色找最相近的unit
     * @param color 需要判断的颜色
     * @return 返回的是在屏幕的绝对x位置
     */
    public float setColor(@ColorInt int color){
        if (mIsColorPickerMode){
            int unit = getWidth() / (COLORS.length - 1);
            int best_unit = 0;
            int best_score= 9999999;
            int a = Color.alpha(color);
            int r = Color.red(color);
            int g = Color.green(color);
            int b = Color.blue(color);

            for (int unit_index = 0; unit_index < COLORS.length;++unit_index){
                int a_dis =  a-Color.alpha(COLORS[unit_index]);
                int r_dis =  r-Color.red(COLORS[unit_index]);
                int g_dis =  g-Color.green(COLORS[unit_index]);
                int b_dis =  b-Color.blue(COLORS[unit_index]);
                int score =  a_dis*a_dis + r_dis*r_dis + g_dis*g_dis + b_dis*b_dis;
                if (score < best_score){
                    best_score = score;
                    best_unit = unit_index;
                }
            }

            Log.i("best index", best_unit+"");
            postInvalidate();
            return getLeft()+best_unit*unit;


        }else{
            // 直接去掉透明色
            cur_color = Color.rgb(Color.red(color),Color.green(color),Color.blue(color));
            postInvalidate();
            return 0;
        }
    }

    public void setAsColorPicker(boolean isColorPicker){
        this.mIsColorPickerMode = isColorPicker;
        if ( !this.mIsColorPickerMode){ // alpha mode
            setColor(Color.BLACK);
        }
        postInvalidate();
    }

    /**
     * 输入一个在滑动条上的步值，获取颜色值
     * @param currentRAWX [0,1]
     * @return 颜色值
     */
    public int getColor(float currentRAWX){
        int posX = (int) (currentRAWX-getLeft());
        int unit;
        int c0,c1,step;

        if (this.mIsColorPickerMode){
            unit = getWidth() / (COLORS.length - 1);
            int index = posX/unit;
            if (index == COLORS.length-1) return COLORS[index];


            c0 = COLORS[index];
            c1 = COLORS[index+1];
        }
        else{ // alpha

            unit = getWidth();
            mCurrentPosition = posX%unit;
            c0 = cur_color;
            c1 = Color.TRANSPARENT;
        }
        step = posX%unit;
        int a = ave(Color.alpha(c0), Color.alpha(c1), unit, step);
        int r = ave(Color.red(c0), Color.red(c1), unit, step);
        int g = ave(Color.green(c0), Color.green(c1), unit, step);
        int b = ave(Color.blue(c0), Color.blue(c1), unit, step);
        return Color.argb(a,r,g,b);
    }
    static private int ave(int s,int e,float total,float cur){
//        if (s > e) {s = s + e; e = s - e; s = s - e ;}
        return (int) (cur*(e-s)/total+s);
    }


    public int getColorWithAlpha(@ColorInt int color){
        if (!isColorPickerMode()){
            return Color.argb(
                    (int)(255*(1-mCurrentPosition)),
                    Color.red(color),
                    Color.green(color),
                    Color.blue(color));
        }
        return color;
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int  width = canvas.getWidth();
        int  height = canvas.getHeight();

        if (mIsColorPickerMode){
            mShader = new LinearGradient(0,0,width,height,COLORS,null,Shader.TileMode.CLAMP);
            mPaint.setShader(mShader);
            canvas.drawRoundRect(0,0,width,height,height/2,height/2,mPaint);
        }
        else{
            mPaint.setShader(null);
            mPaint.setColor(Color.WHITE);
            canvas.drawRoundRect(0,0,width,height,height/2,height/2,mPaint);
            mShader = new LinearGradient(0,0,width,height,cur_color,Color.TRANSPARENT,Shader.TileMode.REPEAT);
            mPaint.setShader(mShader);
            canvas.drawRoundRect(8,8,width-8,height-8,height/2-4,height/2-4,mPaint);
        }
    }
}
