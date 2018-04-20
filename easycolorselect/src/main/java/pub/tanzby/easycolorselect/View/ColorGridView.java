package pub.tanzby.easycolorselect.View;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import pub.tanzby.easycolorselect.Adapter.ColorGridAdapter;


/**
 * Created by tan on 2018/4/11.
 */

public class ColorGridView extends GridLayout {

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
    ColorGridAdapter mAdapter;
    GridView mGridview;
    List<Integer> ColorList;
    private OnColorItemSlect mOnColorItemSlect;

    public ColorGridView(Context context) {
        this(context,null,0);
    }

    public ColorGridView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mGridview = new GridView(getContext());
        mGridview.setGravity(Gravity.CENTER);
        mGridview.setNumColumns(4);
        mGridview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        mAdapter = new ColorGridAdapter(getContext());

        addView(mGridview);

        drawColorGrid();
    }

    // region 操作
    private void drawColorGrid(){

        if (null==ColorList){
            List<Integer> intList = new ArrayList<Integer>();
            for (int i : COLORS)
            {
                intList.add(i);
            }
            mAdapter.setColorList(intList);
        }
        else{
            mAdapter.setColorList(ColorList);
        }


        mAdapter.setOnItemClickListener(new ColorGridAdapter.OnItemClickListener() {
            @Override
            public void onClick(int Color, int postion) {
                if (null!=mOnColorItemSlect){
                    mOnColorItemSlect.onClick(Color,postion);
                }
            }

            @Override
            public void onLongClick(int Color, int postion) {
                if (null!=mOnColorItemSlect){
                    mOnColorItemSlect.onLongClick(Color,postion);
                }
            }
        });


        mGridview.setAdapter(mAdapter);
    }

    public void  setOnColorItemSlect(OnColorItemSlect listener){
        mOnColorItemSlect = listener;
    }
    public interface OnColorItemSlect{
        void onClick(@ColorInt int color, int postion);
        void onLongClick(@ColorInt int color, int position);
    }

}
