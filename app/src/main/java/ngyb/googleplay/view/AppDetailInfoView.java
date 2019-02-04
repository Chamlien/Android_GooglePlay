package ngyb.googleplay.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import ngyb.googleplay.R;
import ngyb.googleplay.bean.AppDetailBean;
import ngyb.googleplay.utils.Constant;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/2 21:50
 */
public class AppDetailInfoView extends RelativeLayout {
    @BindView(R.id.app_icon)
    ImageView appIcon;
    @BindView(R.id.app_name)
    TextView appName;
    @BindView(R.id.app_ratting)
    RatingBar appRatting;
    @BindView(R.id.download_count)
    TextView downloadCount;
    @BindView(R.id.version_code)
    TextView versionCode;
    @BindView(R.id.app_time)
    TextView appTime;
    @BindView(R.id.app_size)
    TextView appSize;

    public AppDetailInfoView(Context context) {
        this(context, null);
    }

    public AppDetailInfoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AppDetailInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_app_detail_info, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(AppDetailBean data) {
        Glide.with(getContext()).load(Constant.IMAGE_URL + data.getIconUrl()).into(appIcon);
        appName.setText(data.getName());
        appRatting.setRating((float) data.getStars());
        String downloadCount1 = String.format(getResources().getString(R.string.download_count), data.getDownloadNum());
        downloadCount.setText(downloadCount1);
        String versionCode1 = String.format(getResources().getString(R.string.version_code), data.getVersion());
        versionCode.setText(versionCode1);
        String date = String.format(getResources().getString(R.string.time), data.getDate());
        appTime.setText(date);
        String size = String.format(getResources().getString(R.string.app_size), Formatter.formatFileSize(getContext(), data.getSize()));
        appSize.setText(size);
    }
}
