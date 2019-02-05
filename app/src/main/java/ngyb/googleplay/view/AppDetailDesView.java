package ngyb.googleplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ngyb.googleplay.R;
import ngyb.googleplay.bean.AppDetailBean;
import ngyb.googleplay.utils.AnimationUtils;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/4 21:31
 */
public class AppDetailDesView extends RelativeLayout {
    @BindView(R.id.app_desc)
    TextView appDesc;
    @BindView(R.id.app_name)
    TextView appName;
    @BindView(R.id.arrow)
    ImageView arrow;
    private int expandHeight;
    private boolean isExpand = false;
    private int startHeight;

    public AppDetailDesView(Context context) {
        this(context, null);
    }

    public AppDetailDesView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AppDetailDesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_app_detail_des, this);
        ButterKnife.bind(this, this);
        //绘制流程没有走,测量没有走就没有高度
        expandHeight = appDesc.getMeasuredHeight();
        //视图树的观察者监听绘制流程中布局的完成
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //获取正常展开后的高度
                expandHeight = appDesc.getMeasuredHeight();
                //如果高度大于7行,则设置成7行
                if (appDesc.getLineCount() > 7) {
                    appDesc.setLines(7);
                }
            }
        });
    }

    public void bindView(AppDetailBean data) {
        appDesc.setText(data.getDes());
        appName.setText(data.getName());
    }

    @OnClick(R.id.arrow)
    public void onViewClicked() {
        if (isExpand) {
            //折叠  从展开后的高度到起始高度
            AnimationUtils.animationViewHeight(appDesc, expandHeight, startHeight);
            AnimationUtils.rotateView(arrow, -180f, 0);
        } else {
            //展开
            startHeight = appDesc.getMeasuredHeight();
            AnimationUtils.animationViewHeight(appDesc, startHeight, expandHeight);
            AnimationUtils.rotateView(arrow, 0, -180f);
        }
        isExpand = !isExpand;
    }
}
