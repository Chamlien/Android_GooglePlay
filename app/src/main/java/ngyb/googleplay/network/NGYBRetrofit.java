package ngyb.googleplay.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;

import ngyb.googleplay.constant.Constant;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/3 22:19
 */
public class NGYBRetrofit {
    private static NGYBRetrofit ngybRetrofit;
    private Api api;
    public static final int DEFAULT_CACHE_SIZE = 5 * 1024 * 1024;//5m

    public static NGYBRetrofit getInstance() {
        if (ngybRetrofit == null) {
            synchronized (NGYBRetrofit.class) {
                if (ngybRetrofit == null) {
                    ngybRetrofit = new NGYBRetrofit();
                }
            }
        }
        return ngybRetrofit;
    }

    public void init(Context context) {
        Gson gson = new GsonBuilder().setLenient().create();//创建Gson  设置宽大处理,可以接受畸形json字符串
        String cacheDir = context.getCacheDir() + "/responses";
        File file = new File(cacheDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        //创建OkhttpClient配置磁盘缓存目录
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
//                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(new Cache(file, DEFAULT_CACHE_SIZE))
                .build();
        //创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))//添加Gson转换器的工厂
                .client(okHttpClient).baseUrl(Constant.BASE_URL).build();
        //使用retrofit初始化API接口
        api = retrofit.create(Api.class);
    }

    private NGYBRetrofit() {

    }

    public Api getApi() {
        return api;
    }

    /**
     * Dangerous interceptor that rewrites the server's cache-control header.
     * 网络拦截器
     */
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder().header("Cache-Control", "max-age=300").build();
        }
    };
}
