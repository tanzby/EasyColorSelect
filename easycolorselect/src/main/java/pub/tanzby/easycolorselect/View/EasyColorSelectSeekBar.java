package pub.tanzby.easycolorselect.View;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import pub.tanzby.easycolorselect.R;

/**
 * Created by tan on 2018/4/10.
 */

public class EasyColorSelectSeekBar extends RelativeLayout {
    float lastX = 0;
    float sliderStep = 0;
    View Slider;
    GradientColorView ColorBar;
    OnColorOperateListener mOnColorOperateListener;

    public EasyColorSelectSeekBar(Context context) {
       this(context,null);
    }
    public EasyColorSelectSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public EasyColorSelectSeekBar(Context context, AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.color_select_main_style,this);
        ColorBar =findViewById(R.id.slide_select_view);
        Slider = findViewById(R.id.slider);
        Slider.setLongClickable(true);
        setEven();
    }

    public void setAsColorPicker(boolean isAsColorPicker){
        ColorBar.setAsColorPicker(isAsColorPicker);
        if (!isAsColorPicker) { // alpha 模式
            lastX = -Slider.getWidth()/2;
            Slider.setLeft(0);
            Slider.postInvalidate();
        }
    }

    public void setColor(@ColorInt int color){
        float rawX = ColorBar.setColor(color);
        if (ColorBar.isColorPickerMode()){
            lastX = rawX-Slider.getWidth()/2;
            Slider.setX(lastX);
        }
    }

    public int alphaFilter(@ColorInt int color){
        return ColorBar.getColorWithAlpha(sliderStep, color);
    }

    private void setEven(){
        Slider.setOnTouchListener(new OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Integer vWidth = v.getWidth()/2;
                Integer leftBound = ColorBar.getLeft()-vWidth;
                Integer rightBound = ColorBar.getRight()-vWidth;
                float mov_X = v.getX();
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        lastX = event.getRawX();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE:{
                        mov_X = event.getRawX() - lastX + v.getX(); // X' = dx + X
                        if (mov_X < leftBound){
                            mov_X = leftBound;
                        }
                        else if (mov_X > rightBound){
                            mov_X = rightBound;
                        }
                        v.setX(mov_X);
                        lastX = event.getRawX();
                        Slider.postInvalidate();
                        sliderStep = (mov_X + vWidth); // center
                        if (null!=mOnColorOperateListener){
                            mOnColorOperateListener.onColorChange(ColorBar.getColor(sliderStep));
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        if (null!=mOnColorOperateListener){
                            mOnColorOperateListener.onColorSelect(ColorBar.getColor(sliderStep));
                        }
                        break;
                    }
                }
                return false;
            }
        });
    }

    public void setOnColorOperateListener(OnColorOperateListener listener){
        this.mOnColorOperateListener = listener;
    }


    public interface OnColorOperateListener{
        void onColorChange(@ColorInt int color);
        void onColorSelect(@ColorInt int color);
    }
    // endregion
}
