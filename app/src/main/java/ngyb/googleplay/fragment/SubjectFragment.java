package ngyb.googleplay.fragment;

import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.List;

import es.dmoral.toasty.Toasty;
import ngyb.googleplay.adapter.SubjectAdapter;
import ngyb.googleplay.bean.SubjectItemBean;
import ngyb.googleplay.network.NGYBRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/5 17:42
 */
public class SubjectFragment extends BaseLoadMoreListFragment {
    private List<SubjectItemBean> dataList;

    @Override
    protected void loadMoreData() {
        Call<List<SubjectItemBean>> listCall = NGYBRetrofit.getInstance().getApi().listSubject(dataList.size());
        listCall.enqueue(new Callback<List<SubjectItemBean>>() {
            @Override
            public void onResponse(Call<List<SubjectItemBean>> call, Response<List<SubjectItemBean>> response) {
                //将更多的数据加入当前的数据集合中
                dataList.addAll(response.body());
                getListAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<SubjectItemBean>> call, Throwable t) {

            }
        });
    }

    @Override
    protected BaseAdapter onCreateAdapter() {
        return new SubjectAdapter(getContext(), dataList);
    }

    @Override
    protected void startLoadData() {
        Call<List<SubjectItemBean>> listCall = NGYBRetrofit.getInstance().getApi().listSubject(0);
        listCall.enqueue(new Callback<List<SubjectItemBean>>() {
            @Override
            public void onResponse(Call<List<SubjectItemBean>> call, Response<List<SubjectItemBean>> response) {
                dataList = response.body();
                Toasty.info(getContext(), "" + dataList.get(0).getDes(), Toast.LENGTH_SHORT).show();
                onLoadDataSuccess();
            }

            @Override
            public void onFailure(Call<List<SubjectItemBean>> call, Throwable t) {
                onDataLoadFailed();
            }
        });
    }
}
