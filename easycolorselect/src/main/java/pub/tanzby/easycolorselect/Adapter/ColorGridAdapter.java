package pub.tanzby.easycolorselect.Adapter;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import pub.tanzby.easycolorselect.View.ColorCircleView;

/**
 * Created by tan on 2018/4/11.
 */

public class ColorGridAdapter extends BaseAdapter{
    Context mContext;
    List<Integer> mData;

    public  ColorGridAdapter(Context mContext){
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return mData==null?0:mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData==null?null:mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static int dip2px(Context context, float dipValue){

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);  //+0.5是为了向上取整
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null){
            holder = new ViewHolder(mContext);
            int radius = dip2px(mContext,36);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(radius, (int) (radius*1.4));
            holder.t.setLayoutParams(params);
            convertView = holder.t;
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.t.setColor(mData.get(position));
        if (mOnItemClickListener!=null){
            holder.t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(mData.get(position),position);
                }
            });
            holder.t.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(mData.get(position),position);
                    return true;
                }
            });
        }
        return convertView;

    }
    public void setColorList(List<Integer> colorList){
        mData = colorList;
        notifyDataSetChanged();
    }

    static private class ViewHolder {
        ColorCircleView t;
        ViewHolder(Context context){t = new ColorCircleView(context);}
    }

    OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener=listener;
    }
    public interface OnItemClickListener{
        void onClick(@ColorInt int Color, int postion);
        void onLongClick(@ColorInt int Color, int postion);
    }
}
