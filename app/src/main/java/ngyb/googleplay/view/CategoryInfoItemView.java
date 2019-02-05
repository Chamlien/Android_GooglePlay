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
import ngyb.googleplay.utils.Constant;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/5 10:25
 */
public class CategoryInfoItemView extends RelativeLayout {
    @BindView(R.id.category_info_image)
    ImageView categoryInfoImage;
    @BindView(R.id.category_info_title)
    TextView categoryInfoTitle;

    public CategoryInfoItemView(Context context) {
        this(context, null);
    }

    public CategoryInfoItemView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CategoryInfoItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_category_info_item, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(String name, String url) {
        categoryInfoTitle.setText(name);
        Glide.with(getContext()).load(Constant.IMAGE_URL + url).into(categoryInfoImage);
    }
}
