package pub.tanzby.easycolorselect.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tan on 2018/4/11.
 */

public class ColorCircleView extends View{
    int mColor;
    Paint mPaint = new Paint();

    public ColorCircleView(Context context) {
        this(context,null);
    }

    public ColorCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        mColor = Color.WHITE;
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
    }
    public ColorCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs,defStyleAttr);
    }

    public void  setColor(@ColorInt int color){
        mColor = color;
        postInvalidate();
    }

    public int getColor(){
        return mColor;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height= canvas.getHeight();
        int K =  Math.min(width,height)/2;

        mPaint.setColor(Color.BLACK);
        mPaint.setMaskFilter(new BlurMaskFilter(K/6, BlurMaskFilter.Blur.SOLID));
        canvas.drawCircle(width/2,height/2,K*4/5,mPaint);

        mPaint.setColor(mColor);
        canvas.drawCircle(width/2,height/2,K*4/5,mPaint);

    }
}