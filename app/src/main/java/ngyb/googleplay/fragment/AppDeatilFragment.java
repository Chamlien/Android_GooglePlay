package ngyb.googleplay.fragment;

import android.view.View;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import ngyb.googleplay.R;
import ngyb.googleplay.bean.AppDetailBean;
import ngyb.googleplay.network.DownloadManager;
import ngyb.googleplay.network.NGYBRetrofit;
import ngyb.googleplay.view.AppDetailDesView;
import ngyb.googleplay.view.AppDetailGalleryView;
import ngyb.googleplay.view.AppDetailInfoView;
import ngyb.googleplay.view.AppDetailSecurityView;
import ngyb.googleplay.view.DownloadButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/2 22:42
 */
public class AppDeatilFragment extends BaseFragment {
    private AppDetailBean data;

    @Override
    protected void startLoadData() {
        String packageName = getActivity().getIntent().getStringExtra("package_name");
        Call<AppDetailBean> appDetail = NGYBRetrofit.getInstance().getApi().getAppDetail(packageName);
        appDetail.enqueue(new Callback<AppDetailBean>() {
            @Override
            public void onResponse(Call<AppDetailBean> call, Response<AppDetailBean> response) {
                data = response.body();
                Toasty.info(getContext(), "" + data.getName(), Toast.LENGTH_SHORT).show();
                onLoadDataSuccess();
            }

            @Override
            public void onFailure(Call<AppDetailBean> call, Throwable t) {
                onDataLoadFailed();
            }
        });
    }

    @Override
    protected View onCreateContentView() {
        View inflate = View.inflate(getContext(), R.layout.fragment_app_detail, null);
        //app信息
        AppDetailInfoView appDetailInfoView = inflate.findViewById(R.id.app_detail_info);
        appDetailInfoView.bindView(data);
        //app安全
        AppDetailSecurityView appDetailSecurity = inflate.findViewById(R.id.app_detail_security);
        appDetailSecurity.bindView(data);
        //app截图
        AppDetailGalleryView appDetailGallery = inflate.findViewById(R.id.app_detail_gallery);
        appDetailGallery.bindView(data);
        //app描述
        AppDetailDesView appDetailDes = inflate.findViewById(R.id.app_detail_des);
        appDetailDes.bindView(data);
        DownloadButton downloadButton = inflate.findViewById(R.id.download_button);
        downloadButton.syncState(data);//同步apk的下载状态
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager.getInstance().handleClick(getContext(), data.getPackageName());
            }
        });
        return inflate;
    }
}
