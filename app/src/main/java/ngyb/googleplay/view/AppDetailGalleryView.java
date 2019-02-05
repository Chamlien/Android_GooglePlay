package ngyb.googleplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ngyb.googleplay.R;
import ngyb.googleplay.bean.AppDetailBean;
import ngyb.googleplay.utils.Constant;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/4 21:42
 */
public class AppDetailGalleryView extends RelativeLayout {
    @BindView(R.id.image_container)
    LinearLayout imageContainer;

    public AppDetailGalleryView(Context context) {
        this(context, null);
    }

    public AppDetailGalleryView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AppDetailGalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_app_detail_gallery, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(AppDetailBean data) {
        List<String> screen = data.getScreen();
        int padding = getResources().getDimensionPixelSize(R.dimen.padding);
        for (int i = 0; i < screen.size(); i++) {
            String url = screen.get(i);
            ImageView imageView = new ImageView(getContext());
            if (i == screen.size() - 1) {
                //最后一个图片给一个右边距
                imageView.setPadding(padding, padding, padding, padding);
            } else {
                imageView.setPadding(padding, padding, 0, padding);
            }
            Glide.with(getContext()).load(Constant.IMAGE_URL + url).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(imageView);
            //加入图片的容器
            imageContainer.addView(imageView);
        }
    }
}
