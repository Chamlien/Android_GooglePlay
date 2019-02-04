package ngyb.googleplay.utils;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/3 22:38
 */
public class AnimationUtils {
    public static void animationViewHeight(final View view, int start, int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();//中间某一个变化值
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = height;
                view.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.start();
    }

    public static void rotateView(View view, float startAngle, float endAngle) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation", startAngle, endAngle);
        objectAnimator.setDuration(500);
        objectAnimator.start();
    }
}
