package ngyb.googleplay.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ngyb.googleplay.R;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/5 8:39
 * 1.布局  2.开始加载数据  3加载成功  4 加载失败  5 点击重试
 */
public abstract class BaseFragment extends Fragment {
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.retry)
    Button retry;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.frame_content)
    FrameLayout frameContent;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_base, null);
        unbinder = ButterKnife.bind(this, root);//内部实际上调用FindViewById
        return root;
    }

    /**
     * 当Fragment视图创建完成后的一个回调
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startLoadData();//抽取初始化时加载数据的逻辑
    }

    /**
     * 每个页面的加载的数据是不一样的,子类自己实现该方法,完成自己数据的加载
     */
    protected abstract void startLoadData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 抽取加载数据成功后的逻辑
     */
    protected void onLoadDataSuccess() {
        progressBar.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        //添加页面的真正的布局
        frameContent.addView(onCreateContentView());
    }

    /**
     * @return 每个页面的布局都不是一样的, 子类自己实现布局, 返回给基类, 加入FrameLayout里面
     */
    protected abstract View onCreateContentView();

    /**
     * 抽取加载数据失败后的逻辑
     */
    public void onDataLoadFailed() {
        if (progressBar != null) {

            progressBar.setVisibility(View.GONE);
        }
        if (errorLayout != null) {
            errorLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.retry)
    public void onViewClicked() {
        progressBar.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
        startLoadData();
    }
}
