package ngyb.googleplay.fragment;

import java.util.List;

import ngyb.googleplay.bean.AppItemBean;
import ngyb.googleplay.network.NGYBRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/5 12:37
 */
public class ApplicationFragment extends BaseAppListFragment {
    @Override
    protected void loadMoreData() {
        Call<List<AppItemBean>> listCall = NGYBRetrofit.getInstance().getApi().listApp(dataList.size());
        listCall.enqueue(new Callback<List<AppItemBean>>() {
            @Override
            public void onResponse(Call<List<AppItemBean>> call, Response<List<AppItemBean>> response) {
                dataList.addAll(response.body());
                getListAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<AppItemBean>> call, Throwable t) {
            }
        });
    }

    @Override
    protected void startLoadData() {
        Call<List<AppItemBean>> listCall = NGYBRetrofit.getInstance().getApi().listApp(0);
        listCall.enqueue(new Callback<List<AppItemBean>>() {
            @Override
            public void onResponse(Call<List<AppItemBean>> call, Response<List<AppItemBean>> response) {
                if (response != null) {
                    if (response.body() != null && response.body().size() > 0) {

                        dataList.addAll(response.body());
                        onLoadDataSuccess();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<AppItemBean>> call, Throwable t) {
                onDataLoadFailed();
            }
        });
    }
}
