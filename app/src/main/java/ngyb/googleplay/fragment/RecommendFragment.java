package ngyb.googleplay.fragment;

import android.view.View;
import android.widget.Toast;

import com.leon.stellarmap.lib.StellarMap;

import java.util.List;

import es.dmoral.toasty.Toasty;
import ngyb.googleplay.R;
import ngyb.googleplay.adapter.RecommendAdapter;
import ngyb.googleplay.network.NGYBRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/5 17:35
 */
public class RecommendFragment extends BaseFragment {
    private List<String> dataList;
    private Call<List<String>> call;

    @Override
    protected void startLoadData() {
        call = NGYBRetrofit.getInstance().getApi().listRecommend();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                dataList = response.body();
                Toasty.info(getContext(), "" + dataList.get(0), Toast.LENGTH_SHORT).show();
                //网络回来的饿时候,网络比较慢,界面可能已经销毁
                if (!call.isCanceled()) {
                    onLoadDataSuccess();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                if (!call.isCanceled()) {
                    onDataLoadFailed();
                }
            }
        });
    }

    @Override
    protected View onCreateContentView() {
        StellarMap stellarMap = new StellarMap(getContext());
        int padding = getResources().getDimensionPixelSize(R.dimen.padding);
        stellarMap.setInnerPadding(padding, padding, padding, padding);
        stellarMap.setAdapter(new RecommendAdapter(getContext(), dataList));
        stellarMap.setRegularity(15, 20);//设置网络,// 避免重叠情况
        stellarMap.setGroup(0);//初始化显示第0组
        return stellarMap;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //当界面销毁后,就取消当前界面的网路请求
        call.cancel();

    }
}
