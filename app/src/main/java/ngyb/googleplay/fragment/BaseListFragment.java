package ngyb.googleplay.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/5 11:51
 * <p>
 * 由于首页,应用,游戏,专题,分类都是List的形式,所以抽取一个ListView  1.adapter 2header 3条目点击
 */
public abstract class BaseListFragment extends BaseFragment {
    private ListView listView;
    private BaseAdapter listAdapter;

    @Override
    protected View onCreateContentView() {
        listView = new ListView(getContext());
        listAdapter = onCreateAdapter();
        View view = onCreateHeaderView();
        if (view != null) {
            listView.addHeaderView(view);
        }
        listView.setAdapter(listAdapter);
        initListView();
        return listView;
    }

    protected View onCreateHeaderView() {
        return null;
    }

    protected void initListView() {
        //listview的初始化
        listView.setDivider(null);//去掉分割线
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //position会将头计算在内
                position = position - getListView().getHeaderViewsCount();//去掉头的计算
                onListItemClick(position);
            }
        });
    }

    /**
     * @param position 子类覆盖该方法,完成自己点击事件的处理
     */
    protected void onListItemClick(int position) {

    }

    protected ListView getListView() {
        return listView;
    }

    public BaseAdapter getListAdapter() {
        return listAdapter;
    }

    /**
     * @return 子类实现该方法返回一个adapter
     */
    protected abstract BaseAdapter onCreateAdapter();
}
