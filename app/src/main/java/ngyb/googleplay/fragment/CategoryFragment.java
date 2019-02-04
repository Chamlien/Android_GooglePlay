package ngyb.googleplay.fragment;

import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.List;

import es.dmoral.toasty.Toasty;
import ngyb.googleplay.adapter.CategoryAdapter;
import ngyb.googleplay.bean.CategoryItemBean;
import ngyb.googleplay.network.NGYBRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/5 12:46
 */
public class CategoryFragment extends BaseListFragment {
    private List<CategoryItemBean> dataList;

    @Override
    protected BaseAdapter onCreateAdapter() {
        //数据集合肯定有值,数据加载成功后才创建布局
        return new CategoryAdapter(getContext(), dataList);
    }

    @Override
    protected void startLoadData() {
        Call<List<CategoryItemBean>> listCall = NGYBRetrofit.getInstance().getApi().listCategory();
        listCall.enqueue(new Callback<List<CategoryItemBean>>() {
            @Override
            public void onResponse(Call<List<CategoryItemBean>> call, Response<List<CategoryItemBean>> response) {
                dataList = response.body();
                Toasty.info(getContext(), "" + dataList.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                onLoadDataSuccess();
            }

            @Override
            public void onFailure(Call<List<CategoryItemBean>> call, Throwable t) {
                onDataLoadFailed();
            }
        });
    }
}
