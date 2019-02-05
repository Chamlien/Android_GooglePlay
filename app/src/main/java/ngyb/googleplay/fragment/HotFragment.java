package ngyb.googleplay.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import es.dmoral.toasty.Toasty;
import ngyb.googleplay.R;
import ngyb.googleplay.network.NGYBRetrofit;
import ngyb.googleplay.view.FlowLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/5 13:04
 */
public class HotFragment extends BaseFragment {
    private List<String> dataList;

    @Override
    protected void startLoadData() {
        //获取一个Call
        Call<List<String>> listCall = NGYBRetrofit.getInstance().getApi().listHot();
        listCall.enqueue(new Callback<List<String>>() {
            @Override
            //在主线程回调
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                dataList = response.body();
                /*for (int i = 0; i < dataList.size(); i++) {
                    String s = dataList.get(i);
                    Log.e(TAG, "onResponse: "+s );
                }*/
                onLoadDataSuccess();
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                onDataLoadFailed();
            }
        });
    }

    @Override
    protected View onCreateContentView() {
        ScrollView scrollView = new ScrollView(getContext());
        FlowLayout flowLayout = new FlowLayout(getContext());
        int padding = getResources().getDimensionPixelSize(R.dimen.padding);
        flowLayout.setPadding(padding, padding, padding, padding);
        for (int i = 0; i < dataList.size(); i++) {
            String data = dataList.get(i);
            TextView textView = getTextView(padding, data);
            StateListDrawable stateListDrawable = getStateListDrawable();
            //给TextView设置背景
            textView.setBackgroundDrawable(stateListDrawable);
            flowLayout.addView(textView);
        }
        scrollView.addView(flowLayout);
        return scrollView;
    }

    private StateListDrawable getStateListDrawable() {
        //给TextView创建一个带圆角的shape,正常情况下背景
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(8);
        //设置一个随机的颜色
        gradientDrawable.setColor(getRandomColor());
        //创建一个按下去的背景
        GradientDrawable pressedDrawable = new GradientDrawable();
        pressedDrawable.setCornerRadius(8);
        //设置一个随机的颜色
        pressedDrawable.setColor(Color.DKGRAY);
        //创建一个选择器,将两种状态的背景组合起来
        StateListDrawable stateListDrawable = new StateListDrawable();
        //按下去的状态
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        //其他状态
        stateListDrawable.addState(new int[]{}, gradientDrawable);
        return stateListDrawable;
    }

    private int getRandomColor() {
        Random random = new Random();
        int alpha = 255;
        int red = 70 + random.nextInt(170); //30--200
        int green = 30 + random.nextInt(170);
        int blue = 30 + random.nextInt(170);
        int argb = Color.argb(alpha, red, green, blue);
        return argb;
    }

    private TextView getTextView(int padding, String data) {
        final TextView textView = new TextView(getContext());
        textView.setTextSize(20);
        textView.setTextColor(Color.WHITE);
        textView.setPadding(padding, padding, padding, padding);
        textView.setGravity(Gravity.CENTER);
        textView.setClickable(true);
        textView.setText(data);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                Toasty.info(getContext(), "" + tv.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        return textView;
    }
}
