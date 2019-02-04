package ngyb.googleplay.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/5 10:49
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    private final Context context;
    private final List<T> dataList;

    public BaseListAdapter(Context context, List<T> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }

    /**
     * @param position
     * @return 返回对应位置的数据
     */
    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = onCreateItemView(position);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        onBindItemView(vh.itemView, position);
        return convertView;
    }

    public Context getContext() {
        return context;
    }

    public List<T> getDataList() {
        return dataList;
    }

    /**
     * @param itemView
     * @param position 子类必须实现该方法来完成条目的绑定
     */
    abstract void onBindItemView(View itemView, int position);

    public static class ViewHolder {
        private View itemView;

        public ViewHolder(View item) {
            itemView = item;
        }
    }

    /**
     * @param position
     * @return 子类必须实现该方法来完成条目的创建
     */
    abstract View onCreateItemView(int position);
}
