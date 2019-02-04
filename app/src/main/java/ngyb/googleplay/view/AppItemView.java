package ngyb.googleplay.view;

import android.content.Context;
import android.text.format.Formatter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import ngyb.googleplay.R;
import ngyb.googleplay.bean.AppItemBean;
import ngyb.googleplay.utils.Constant;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/4 22:30
 */
public class AppItemView extends RelativeLayout {
    @BindView(R.id.app_icon)
    ImageView appIcon;
    @BindView(R.id.app_name)
    TextView appName;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.size)
    TextView size;
    @BindView(R.id.circle_download_view)
    CircleDownloadView circleDownloadView;
    @BindView(R.id.app_desc)
    TextView appDesc;
    private static final String TAG = "AppItemView";

    public AppItemView(Context context) {
        this(context, null);
    }

    public AppItemView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AppItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_app_item_view, this);
        ButterKnife.bind(this, this);
    }

    /**
     * @param appItemBean 当listview滚动,绑定条目
     */
    public void bindView(AppItemBean appItemBean) {
        Log.e(TAG, "bindView: " + (Constant.IMAGE_URL + appItemBean.getIconUrl()));
        Glide.with(getContext()).load(Constant.IMAGE_URL + appItemBean.getIconUrl()).into(appIcon);
        appName.setText(appItemBean.getName());
        ratingBar.setRating(appItemBean.getStars());
        size.setText(Formatter.formatFileSize(getContext(), appItemBean.getSize()));
        appDesc.setTag(appItemBean.getDes());
        circleDownloadView.syncState(appItemBean);
    }
}
