package ngyb.googleplay.network;

import java.util.List;

import ngyb.googleplay.bean.AppDetailBean;
import ngyb.googleplay.bean.AppItemBean;
import ngyb.googleplay.bean.CategoryItemBean;
import ngyb.googleplay.bean.HomeBean;
import ngyb.googleplay.bean.SubjectItemBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/3 20:57
 */
public interface Api {
    @GET("hot")
    Call<List<String>> listHot();

    @GET("recommend")
    Call<List<String>> listRecommend();

    @GET("category")
    Call<List<CategoryItemBean>> listCategory();

    @GET("subject")
    Call<List<SubjectItemBean>> listSubject(@Query("index") int index);

    @GET("game")
    Call<List<AppItemBean>> listGame(@Query("index") int index);

    @GET("app")
    Call<List<AppItemBean>> listApp(@Query("index") int index);

    @GET("home")
    Call<HomeBean> listHome(@Query("index") int index);

    @GET("detail")
    Call<AppDetailBean> getAppDetail(@Query("packageName") String packageName);
}
