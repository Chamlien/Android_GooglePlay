package ngyb.googleplay.fragment;

import android.widget.AbsListView;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/5 12:10
 * 由于首页,应用,游戏,专题,都有加载更多的功能,抽取加载更多的逻辑
 */
public abstract class BaseLoadMoreListFragment extends BaseListFragment {

    @Override
    protected void initListView() {
        super.initListView();
        //ListView初始化
        getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    //滚动停止时,判断是否滚动到底部
                    if (shouldStartLoadMore()) {
                        //加载更多数据
                        loadMoreData();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    protected abstract void loadMoreData();

    protected boolean shouldStartLoadMore() {
        //getLastVisiblePosition也将头的个数计算在内
        return getListView().getLastVisiblePosition() == getListAdapter().getCount() - 1 + getListView().getHeaderViewsCount();
    }
}
