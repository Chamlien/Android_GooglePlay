package ngyb.googleplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import ngyb.googleplay.R;
import ngyb.googleplay.bean.SubjectItemBean;
import ngyb.googleplay.utils.Constant;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/5 10:42
 */
public class SubjectItemView extends RelativeLayout {
    private static final String TAG = "SubjectItemView";
    @BindView(R.id.subject_item_image)
    ImageView subjectItemImage;
    @BindView(R.id.subject_item_title)
    TextView subjectItemTitle;

    public SubjectItemView(Context context) {
        this(context, null);
    }

    public SubjectItemView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SubjectItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_subject_item, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(SubjectItemBean subjectItemBean) {
        subjectItemTitle.setText(subjectItemBean.getDes());
        Log.e(TAG, "bindView: " + (Constant.IMAGE_URL + subjectItemBean.getUrl()));
        Glide.with(getContext()).load(Constant.IMAGE_URL + subjectItemBean.getUrl()).into(subjectItemImage);
    }
}
