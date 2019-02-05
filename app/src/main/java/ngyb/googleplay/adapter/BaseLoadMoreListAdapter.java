package ngyb.googleplay.adapter;

import android.content.Context;
import android.view.View;

import java.util.List;

import ngyb.googleplay.view.LoadingView;


/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/5 10:59
 * 抽取多条目,进度条布局
 */
public abstract class BaseLoadMoreListAdapter<T> extends BaseListAdapter<T> {
    public static final int ITEM_TYPE_ZERO = 0;
    public static final int ITEM_TYPE_ONE = 1;

    public BaseLoadMoreListAdapter(Context context, List<T> dataList) {
        super(context, dataList);
    }

    @Override
    public int getCount() {
        if (getDataList() == null) {
            return 0;
        }
        return getDataList().size() + 1; //多了一进度条的条目
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return ITEM_TYPE_ZERO;
        } else {
            return ITEM_TYPE_ONE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    void onBindItemView(View itemView, int position) {
        if (getItemViewType(position) == ITEM_TYPE_ONE) {
            onBindNormalItemView(itemView, position);
        }
    }

    abstract void onBindNormalItemView(View itemView, int position);

    @Override
    View onCreateItemView(int position) {
        if (getItemViewType(position) == ITEM_TYPE_ONE) {
            //创建普通类的条目
            return onCreateOneItemView();
        } else {
            //创建一个进度条
            return new LoadingView(getContext());
        }
    }

    /**
     * @return 子类必须实现方法实现一个普通的条目
     */
    abstract View onCreateOneItemView();
}
