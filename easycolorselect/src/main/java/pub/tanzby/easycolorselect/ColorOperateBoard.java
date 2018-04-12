package pub.tanzby.easycolorselect;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import pub.tanzby.easycolorselect.View.ColorGridView;
import pub.tanzby.easycolorselect.View.EasyColorSelectSeekBar;

/**
 * Created by tan on 2018/4/11.
 */

public class ColorOperateBoard extends ConstraintLayout {

    EasyColorSelectSeekBar alpha_bar;
    EasyColorSelectSeekBar color_bar;
    ColorGridView color_grid;

    OnColorOperateListener mOnListener;


    public ColorOperateBoard(Context context) {
        this(context,null,0);
    }

    public ColorOperateBoard(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorOperateBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.color_operate_board_style,this);

        alpha_bar =  findViewById(R.id.select_transparent_rate);
        color_bar =  findViewById(R.id.select_color_seekbar);
        color_grid=  findViewById(R.id.select_decrete_color);

        color_bar.setAsColorPicker(true);  // 设置为 选择颜色模式，可以使用setColor改变滑动slider 的位置
        alpha_bar.setAsColorPicker(false); // 设置为 不选择颜色模式

        toggleColorSection(false);


        setEven();
    }

    private void setEven(){
        color_grid.setOnColorItemSlect(new ColorGridView.OnColorItemSlect() {
            @Override
            public void onClick(int color, int postion) {
                alpha_bar.setColor(color);
                color_bar.setColor(color);
                if (null != mOnListener) {
                    color = alpha_bar.alphaFilter(color);
                    mOnListener.onColorSelect(color);
                }
            }

            @Override
            public void onLongClick(int color, int position) {
                alpha_bar.setColor(color);
                color_bar.setColor(color);
                toggleColorSection(true);
                if (null != mOnListener) {
                    color = alpha_bar.alphaFilter(color);
                    mOnListener.onColorSelect(color);
                }
            }
        });

        alpha_bar.setOnColorOperateListener(new EasyColorSelectSeekBar.OnColorOperateListener() {
            @Override
            public void onColorChange(int color) {
               if (null!=mOnListener)mOnListener.onColorChange(color);
            }

            @Override
            public void onColorSelect(int color) {
                if (null!=mOnListener) mOnListener.onColorSelect(color);
            }
        });

        color_bar.setOnColorOperateListener(new EasyColorSelectSeekBar.OnColorOperateListener() {
            @Override
            public void onColorChange(int color) {
                alpha_bar.setColor(color);
                if (null != mOnListener) {
                    color = alpha_bar.alphaFilter(color);
                    mOnListener.onColorChange(color);
                }
            }

            @Override
            public void onColorSelect(int color) {
                alpha_bar.setColor(color);
                if (null != mOnListener) {
                    color = alpha_bar.alphaFilter(color);
                    mOnListener.onColorSelect(color);
                }
            }
        });

        color_bar.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toggleColorSection(false);
                return false;
            }
        });

    }

    //region  operation
    public void setOnColorOperateListener(OnColorOperateListener listener){
        this.mOnListener = listener;
    }

    public void toggleColorSection(boolean useSeekbar){
        if (alpha_bar!=null){
            color_grid.setVisibility(useSeekbar?INVISIBLE:VISIBLE);
            color_bar.setVisibility(useSeekbar?VISIBLE:INVISIBLE);
        }
    }

    public interface OnColorOperateListener {
        void onColorChange(@ColorInt int color);

        void onColorSelect(@ColorInt int color);
    }

    //endregion


}
