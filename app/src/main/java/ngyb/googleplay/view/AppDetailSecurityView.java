package ngyb.googleplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ngyb.googleplay.R;
import ngyb.googleplay.bean.AppDetailBean;
import ngyb.googleplay.utils.AnimationUtils;
import ngyb.googleplay.utils.Constant;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/4 22:03
 */
public class AppDetailSecurityView extends RelativeLayout {
    @BindView(R.id.tag_container)
    LinearLayout tagContainer;
    @BindView(R.id.arrow)
    ImageView arrow;
    @BindView(R.id.desc_container)
    LinearLayout descContainer;
    private int expandHeight;
    private boolean isExpand = false;

    public AppDetailSecurityView(Context context) {
        this(context, null);
    }

    public AppDetailSecurityView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AppDetailSecurityView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.view_app_detail_security, this);
        ButterKnife.bind(this, this);
        //监听布局完成,获取正常情况下描述容器高度,并保存,再初始化高度为0
        //获取视图树的观察者,监听布局完成
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //只监听一次就可以了
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //获取正常情况下容器高度,即展开后的高度
                expandHeight = descContainer.getMeasuredHeight();
                //初始化高度为0
                ViewGroup.LayoutParams layoutParams = descContainer.getLayoutParams();
                layoutParams.height = 0;
                descContainer.setLayoutParams(layoutParams);
            }
        });
    }

    public void bindView(AppDetailBean data) {
        int padding = getResources().getDimensionPixelSize(R.dimen.padding);
        //遍历safe数组,获取每个元素的safeUrl(标签图片的url),创建ImageView添加到标签图片的容器tag_container
        for (int i = 0; i < data.getSafe().size(); i++) {
            AppDetailBean.SafeBean safeBean = data.getSafe().get(i);
            ImageView tag = new ImageView(getContext());
            Glide.with(getContext()).load(Constant.IMAGE_URL + safeBean.getSafeUrl()).override(100, 40).into(tag);
            tagContainer.addView(tag);
            //遍历safe数组,获取描述safeDes以及描述对应的图片safeDesUrl,创建一个水平方向的LinearLayout(1行)
            //创建一行
            LinearLayout line = new LinearLayout(getContext());
            line.setOrientation(LinearLayout.HORIZONTAL);
            line.setPadding(0, padding, 0, 0);
            ImageView desIcon = new ImageView(getContext());
            Glide.with(getContext()).load(Constant.IMAGE_URL + safeBean.getSafeDesUrl()).override(100, 40).into(desIcon);
            //将图片加入一行的LinearLayout
            line.addView(desIcon);
            TextView des = new TextView(getContext());
            des.setText(safeBean.getSafeDes());
            //将描述文本加入一行的LinearLayout
            line.addView(des);
            //将一行加入描述的容器里面
            descContainer.addView(line);
        }
    }

    @OnClick(R.id.arrow)
    public void onViewClicked() {
        //如果展开,则折叠
        if (isExpand) {
            //将高度从展开后的高度到0
//            ViewGroup.LayoutParams layoutParams = descContainer.getLayoutParams();
//            layoutParams.height = 0;
//            descContainer.setLayoutParams(layoutParams);
            AnimationUtils.animationViewHeight(descContainer, expandHeight, 0);
//            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(arrow, "rotation", -180, 0f);
//            objectAnimator.setDuration(500);
//            objectAnimator.start();
            AnimationUtils.rotateView(arrow, -180f, 0);
        } else {
//            //如果折叠,则展开
//            //将高度从0到展开后的高度
//            final ViewGroup.LayoutParams layoutParams = descContainer.getLayoutParams();
//            layoutParams.height = expandHeight;
//            //使用一个工具产生0---展开后高度的乙烯类变化值
//            ValueAnimator valueAnimator = ValueAnimator
//                    .ofInt(0, expandHeight);
//            valueAnimator.setDuration(500);
//            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    int height = (int) animation.getAnimatedValue();
//                    Log.e(TAG, "onAnimationUpdate: "+height );
//                    ViewGroup.LayoutParams layoutParams1 = descContainer.getLayoutParams();
//                    layoutParams1.height = height;
//                    descContainer.setLayoutParams(layoutParams1);
//                }
//            });
//            valueAnimator.start();;
            AnimationUtils.animationViewHeight(descContainer, 0, expandHeight);
//            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(arrow, "rotation", 0, -180f);
//            objectAnimator.setDuration(500);
//            objectAnimator.start();
            AnimationUtils.rotateView(arrow, 0, -180f);
        }
        isExpand = !isExpand;
    }
}
