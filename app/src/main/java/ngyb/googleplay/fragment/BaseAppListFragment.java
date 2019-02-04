package ngyb.googleplay.fragment;

import android.content.Intent;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import ngyb.googleplay.activity.AppDetailActivity;
import ngyb.googleplay.adapter.AppListAdapter;
import ngyb.googleplay.bean.AppItemBean;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/5 12:21
 * 1.AppListAdapter 首页 应用 游戏使用的是同样的Adapter  2.3个界面的数据集合的类型是一样  3.条目点击跳转到详情界面
 */
public abstract class BaseAppListFragment extends BaseLoadMoreListFragment {
    protected List<AppItemBean> dataList = new ArrayList<>();

    @Override
    protected BaseAdapter onCreateAdapter() {
        return new AppListAdapter(getContext(), dataList);
    }

    @Override
    protected void onListItemClick(int position) {
        //跳转到app详情
        Intent intent = new Intent(getContext(), AppDetailActivity.class);
        //传入一个包名
        intent.putExtra("package_name", dataList.get(position).getPackageName());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getListAdapter() != null) {
            getListAdapter().notifyDataSetChanged();
        }
    }
}
