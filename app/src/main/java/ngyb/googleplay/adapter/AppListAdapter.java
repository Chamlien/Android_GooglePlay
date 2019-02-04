package ngyb.googleplay.adapter;

import android.content.Context;
import android.view.View;

import java.util.List;

import ngyb.googleplay.bean.AppItemBean;
import ngyb.googleplay.view.AppItemView;


/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/5 11:08
 */
public class AppListAdapter extends BaseLoadMoreListAdapter<AppItemBean> {
    public AppListAdapter(Context context, List<AppItemBean> dataList) {
        super(context, dataList);
    }

    @Override
    void onBindNormalItemView(View itemView, int position) {
        ((AppItemView) itemView).bindView(getDataList().get(position));
    }

    @Override
    View onCreateOneItemView() {
        return new AppItemView(getContext());
    }
}
