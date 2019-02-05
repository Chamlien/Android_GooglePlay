package ngyb.googleplay.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.leon.loopviewpagerlib.FunBanner;

import java.util.List;

import es.dmoral.toasty.Toasty;
import ngyb.googleplay.R;
import ngyb.googleplay.bean.HomeBean;
import ngyb.googleplay.network.NGYBRetrofit;
import ngyb.googleplay.utils.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/5 12:55
 */
public class HomeFragment extends BaseAppListFragment {
    private List<String> pictures;

    @Override
    protected void loadMoreData() {
        Call<HomeBean> homeBeanCall = NGYBRetrofit.getInstance().getApi().listHome(dataList.size());
        homeBeanCall.enqueue(new Callback<HomeBean>() {
            @Override
            public void onResponse(Call<HomeBean> call, Response<HomeBean> response) {
                dataList.addAll(response.body().getList());
                getListAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<HomeBean> call, Throwable t) {

            }
        });
    }

    @Override
    protected void startLoadData() {
        Call<HomeBean> homeBeanCall = NGYBRetrofit.getInstance().getApi().listHome(0);
        homeBeanCall.enqueue(new Callback<HomeBean>() {
            @Override
            public void onResponse(Call<HomeBean> call, Response<HomeBean> response) {
                Toasty.success(getContext(), "success", Toast.LENGTH_SHORT).show();
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getList() != null && response.body().getList().size() > 0) {
                            dataList.addAll(response.body().getList());
                            pictures = response.body().getPicture();
                            onLoadDataSuccess();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<HomeBean> call, Throwable t) {
                Toasty.error(getContext(), "failed" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                onDataLoadFailed();
            }
        });

    }

    @Override
    protected View onCreateHeaderView() {
        FunBanner funBanner = new FunBanner.Builder(getContext())
                .setHeightWidthRatio(0.377f)
                .setDotNormalColor(Color.WHITE)
                .setImageUrlHost(Constant.IMAGE_URL)
                .setImageUrls(pictures)
                .setDotSelectedColor(getResources().getColor(R.color.colorPrimary))
                .build();
        return funBanner;
    }
}
