package ngyb.googleplay.application;

import android.app.Application;

import ngyb.googleplay.network.NGYBRetrofit;


/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/3 20:54
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NGYBRetrofit.getInstance().init(getApplicationContext());
    }
}
