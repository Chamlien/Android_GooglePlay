# GooglePlayN
谷歌市场


# 项目简介 #
本项目是应用市场的一个示例项目，展示了一个应用市场基本的功能：应用展示，应用分类，应用详情，应用下载等。


# 服务器搭建 #

[服务器你代码] (https://gitee.com/nangongyibin/Java_GooglePlayServer](https://gitee.com/nangongyibin/Java_GooglePlayServer) 


* 文件说明
	* GooglePlayServer:java ee工程,我们的服务器
	* GooglePlayServer.war:java ee工程的war包形式
	* WebInfos:资源文件

* 搭建方式:

	* war包方式:
		1. 把war放到tomact的webapps目录下面就可以,然后启动tomcat会自动解压war包.
		2. 启动tomcat,自动解压war包,并运行程序 
		3. 修改`webapps\GooglePlayServer\WEB-INF\classes`目录下system.properties为`dir=D:/WorkSpace/GooglePlayServer/resource`(`WebInfos`所在的目录),需要注意要么用"/"或者"\\\"
		4. 在pc和手机上分别验证

 	* 源码形式.
		1. 用java ee 版eclipse导入工程GooglePlayServer.
		2. 修改目录下system.properties为`dir=D:/WorkSpace/GooglePlayServer/resource`(`WebInfos`所在的目录),需要注意要么用"/"或者"\\\"
		3. 部署java ee工程到tomcat,然后运行
		4. 在pc和手机上分别验证。

		
>注意
> tomcat必须使用7以上版本，Eclipse工作区间编码格式改成utf-8

# 项目初始化 #
* BaseActivity
* BaseFragment
* [ButterKnife](https://github.com/JakeWharton/butterknife)集成
* Git初始化

# 侧滑菜单 #

## 布局 ##

    <?xml version="1.0" encoding="utf-8"?>
	<android.support.v4.widget.DrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:app="http://schemas.android.com/apk/res-auto"
	    xmlns:tools="http://schemas.android.com/tools"
	    android:id="@+id/drawer_layout"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    android:fitsSystemWindows="true"
	    tools:context=".activity.MainActivity">
	
	    <!--<TextView-->
	    <!--android:layout_gravity="start"-->
	    <!--android:background="@color/colorPrimary"-->
	    <!--android:layout_width="match_parent"-->
	    <!--android:layout_height="match_parent" -->
	    <!--android:text="Hello World!"/>-->
	    <include layout="@layout/main_content" />
	
	    <android.support.design.widget.NavigationView
	        android:id="@+id/navigation_view"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent"
	        android:layout_gravity="start"
	        app:headerLayout="@layout/nav_header"
	        app:menu="@menu/drawer_main"></android.support.design.widget.NavigationView>
	</android.support.v4.widget.DrawerLayout>

## DrawLayout ##
在DrawerLayout出现之前，我们需要做侧滑菜单时，不得不自己实现一个或者使用Github上的开源的项目SlidingMenu，也许是Google也看到了SlidingMenu的强大之处，于是在Android的后期版本中添加了DrawerLayout来实现SlidingMenu同样功能的组件，而且为了兼容早期版本，将其添加在android,support.v4包下。


### 布局 ###

    <?xml version="1.0" encoding="utf-8"?>
	<android.support.v4.widget.DrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:app="http://schemas.android.com/apk/res-auto"
	    xmlns:tools="http://schemas.android.com/tools"
	    android:id="@+id/drawer_layout"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    android:fitsSystemWindows="true"
	    tools:context=".activity.MainActivity">
	
	    <!--<TextView-->
	    <!--android:layout_gravity="start"-->
	    <!--android:background="@color/colorPrimary"-->
	    <!--android:layout_width="match_parent"-->
	    <!--android:layout_height="match_parent" -->
	    <!--android:text="Hello World!"/>-->
	    <include layout="@layout/main_content" />
	
	    <android.support.design.widget.NavigationView
	        android:id="@+id/navigation_view"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent"
	        android:layout_gravity="start"
	        app:headerLayout="@layout/nav_header"
	        app:menu="@menu/drawer_main"></android.support.design.widget.NavigationView>
	</android.support.v4.widget.DrawerLayout>

>使用layout_gravity属性来控制是左侧还是右侧菜单

## NavigationView ##


### NavigationView的使用 ###
DrawerLayout里面的菜单布局我们可以自己定义，但谷歌也提供的相应的控件NavigationView，方便开发者完成菜单布局。

	//需添加依赖	
    implementation 'com.android.support:design:28.0.0'

>        app:headerLayout="@layout/nav_header" 定义菜单的头布局
>
>        app:menu="@menu/drawer_main" 定义菜单选项



### 设置菜单点击监听 ###

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
	            //返回true,表示选中该选项
	            @Override
	            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
	                //关闭侧滑菜单
	                drawerLayout.closeDrawer(Gravity.START);
	                return false;
	            }
	        });

## ActionBar ##

### ActionBar基本使用

            ActionBar supportActionBar = getSupportActionBar();
	        supportActionBar.setDisplayHomeAsUpEnabled(true);
	        supportActionBar.setTitle(getString(R.string.app_name));

### ActionBar和DrawerLayout联动

    
    private void initActionBar() {
        //用Toolbar替换actionbar
        setSupportActionBar(toolBar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle(getString(R.string.app_name));
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        //同步状态
        actionBarDrawerToggle.syncState();//根据DrawerLayout开关的状态来改变它的显示效果
        drawerLayout.addDrawerListener(actionBarDrawerToggle);//将侧滑滚动的状态通知actionBarDrawerToggle
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //封装了drawerLayout的打开和关闭
                actionBarDrawerToggle.onOptionsItemSelected(item);
//                drawerLayout.openDrawer(Gravity.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


## ToolBar ##
官方在某些程度上认为 ActionBar 限制了 android app 的开发与设计的弹性,
Toolbar 是在 Android 5.0 开始推出的一个 Material Design 风格的导航控件 ，Google 非常推荐大家使用 Toolbar 
来作为Android客户端的导航栏，以此来取代之前的 Actionbar 。与 Actionbar 相比，Toolbar 明显要灵活的多。它不像 
Actionbar一样，一定要固定在Activity的顶部，而是可以放到界面的任意位置。ToolBar继承ViewGroup，内部可以摆放孩子，方便定制。

### Toolbar使用 ###
#### 1. 去掉ActionBar ####

        <style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">

#### 2. 在布局中添加Toolbar ####

    `
    <android.support.v7.widget.Toolbar
        android:id="@+id/tool_bar"
        android:layout_width="match_parent"
        android:layout_height="?android:actionBarSize"
        android:background="@color/colorPrimary"
        app:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"></android.support.v7.widget.Toolbar>`

>注意，如果将Toolbar直接放入DrawerLayout，其高度设置会失效（原因回想下View的测量）

#### 3. 替换ActionBar ####

    
    private void initActionBar() {
        //用Toolbar替换actionbar
        setSupportActionBar(toolBar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle(getString(R.string.app_name));
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        //同步状态
        actionBarDrawerToggle.syncState();//根据DrawerLayout开关的状态来改变它的显示效果
        drawerLayout.addDrawerListener(actionBarDrawerToggle);//将侧滑滚动的状态通知actionBarDrawerToggle
    }

## 状态栏配置 ##
#### 1. 给DrawerLayout配置fitsSystemWindows ####

        android:fitsSystemWindows="true"


#### 2. 创建v21样式 ####
在v21版本及以上可以配置状态栏颜色，所以

        <!-- Base application theme. -->
	    <style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
	        <!-- Customize your theme here. -->
	        <item name="colorPrimary">@color/colorPrimary</item>
	        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
	        <item name="colorAccent">@color/colorAccent</item>
	        <item name="android:windowDrawsSystemBarBackgrounds">true</item>
	        <item name="android:statusBarColor">@android:color/transparent</item>
	    </style>

#### 3. 配置状态栏颜色与Toolbar背景色一致 ####

            <item name="colorPrimaryDark">@color/colorPrimaryDark</item>


# 主界面 #
## 布局 ##

    <?xml version="1.0" encoding="utf-8"?>
	<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:app="http://schemas.android.com/apk/res-auto"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    android:orientation="vertical">
	
	    <android.support.v7.widget.Toolbar
	        android:id="@+id/tool_bar"
	        android:layout_width="match_parent"
	        android:layout_height="?android:actionBarSize"
	        android:background="@color/colorPrimary"
	        app:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"></android.support.v7.widget.Toolbar>
	
	    <android.support.design.widget.TabLayout
	        android:id="@+id/tab_layout"
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content"
	        android:background="@color/colorPrimary"
	        app:tabMode="scrollable"
	        app:tabSelectedTextColor="@android:color/white"
	        app:tabTextColor="@android:color/darker_gray"></android.support.design.widget.TabLayout>
	
	    <android.support.v4.view.ViewPager
	        android:id="@+id/view_pager"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent"></android.support.v4.view.ViewPager>
	</LinearLayout>

## 初始化布局 ##
    
            titles = getResources().getStringArray(R.array.main_titles);
	        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager(), titles));
	        viewPager.addOnPageChangeListener(onPageChangeListener);
	        //关联viewPager
	        tabLayout.setupWithViewPager(viewPager);

	`package ngyb.googleplay.adapter;
	
	import android.support.annotation.Nullable;
	import android.support.v4.app.Fragment;
	import android.support.v4.app.FragmentManager;
	import android.support.v4.app.FragmentPagerAdapter;
	
	import ngyb.googleplay.factory.FragmentFactory;
	
	
	/**
	 * 作者：南宫燚滨
	 * 描述:
	 * 邮箱：nangongyibin@gmail.com
	 * 时间: 2018/5/5 11:13
	 */
	public class MainAdapter extends FragmentPagerAdapter {
	    private final String[] titles;
	
	    public MainAdapter(FragmentManager fm, String[] titles) {
	        super(fm);
	        this.titles = titles;
	    }
	
	    /**
	     * @param position
	     * @return 只会创建一次Fragment
	     */
	    @Override
	    public Fragment getItem(int position) {
	        return FragmentFactory.getFragment(position);
	    }
	
	    @Override
	    public int getCount() {
	        return titles.length;
	    }
	
	    @Nullable
	    @Override
	    public CharSequence getPageTitle(int position) {
	        return titles[position];
	    }
	}
`

    package ngyb.googleplay.factory;

	import android.support.v4.app.Fragment;
	
	import ngyb.googleplay.fragment.ApplicationFragment;
	import ngyb.googleplay.fragment.CategoryFragment;
	import ngyb.googleplay.fragment.GameFragment;
	import ngyb.googleplay.fragment.HomeFragment;
	import ngyb.googleplay.fragment.HotFragment;
	import ngyb.googleplay.fragment.RecommendFragment;
	import ngyb.googleplay.fragment.SubjectFragment;
	
	
	/**
	 * 作者：南宫燚滨
	 * 描述:
	 * 邮箱：nangongyibin@gmail.com
	 * 时间: 2018/5/5 11:15
	 */
	public class FragmentFactory {
	    public static final int FRAGMENT_HOME = 0;
	    public static final int FRAGMENT_APP = 1;
	    public static final int FRAGMENT_GAME = 2;
	    public static final int FRAGMENT_SUBJECT = 3;
	    public static final int FRAGMENT_RECOMMEND = 4;
	    public static final int FRAGMENT_CATEGORY = 5;
	    public static final int FRAGMENT_HOT = 6;
	
	    /**
	     * @param position
	     * @return 根据不同的位置返回不同的实例
	     */
	    public static Fragment getFragment(int position) {
	        switch (position) {
	            case FRAGMENT_HOME:
	                return new HomeFragment();
	            case FRAGMENT_APP:
	                return new ApplicationFragment();
	            case FRAGMENT_GAME:
	                return new GameFragment();
	            case FRAGMENT_SUBJECT:
	                return new SubjectFragment();
	            case FRAGMENT_RECOMMEND:
	                return new RecommendFragment();
	            case FRAGMENT_CATEGORY:
	                return new CategoryFragment();
	            case FRAGMENT_HOT:
	                return new HotFragment();
	        }
	        return null;
	    }
	}


## BaseFragment抽取 ##
BaseFragment抽取了所有Fragment的共性，特性交给子类去实现。
### 界面的公共点 ###
#### 布局 ####
* 加载进度条
* 加载出错布局

#### 加载成功 ####

    
    /**
     * 抽取加载数据成功后的逻辑
     */
    protected void onLoadDataSuccess() {
        progressBar.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        //添加页面的真正的布局
        frameContent.addView(onCreateContentView());
    }

#### 加载失败 ####

    
    /**
     * 抽取加载数据失败后的逻辑
     */
    public void onDataLoadFailed() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        if (errorLayout != null) {
            errorLayout.setVisibility(View.VISIBLE);
        }
    }

### 界面的不同点 ###
#### 加载数据 ####

    
    /**
     * 当Fragment视图创建完成后的一个回调
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startLoadData();//抽取初始化时加载数据的逻辑
    }

    /**
     * 每个页面的加载的数据是不一样的,子类自己实现该方法,完成自己数据的加载
     */
    protected abstract void startLoadData();

#### 创建视图 ####

    
    /**
     * @return 每个页面的布局都不是一样的, 子类自己实现布局, 返回给基类, 加入FrameLayout里面
     */
    protected abstract View onCreateContentView();

# Retrofit集成 #
* [Github](https://github.com/square/retrofit)
* [Wiki](http://square.github.io/retrofit/)

## 添加依赖 ##

        implementation 'com.squareup.retrofit2:retrofit:2.1.0'
    	implementation 'com.squareup.retrofit2:converter-gson:2.1.0'

## 创建Api接口 ##

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


## 初始化Api接口 ##

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



## 配置JSON转换 ##
由于返回的数据时中括号开头，属于畸形的json字符串，需要配置Gson进行宽大处理。
            Gson gson = new GsonBuilder().setLenient().create();//创建Gson  设置宽大处理,可以接受畸形json字符串

            //创建Retrofit对象
	        Retrofit retrofit = new Retrofit.Builder()
	                .addConverterFactory(GsonConverterFactory.create(gson))//添加Gson转换器的工厂
	                .client(okHttpClient).baseUrl(Constant.BASE_URL).build();
# 热门页面 #
## 加载数据 ##

    
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

## 创建视图 ##

    `
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
    }`

    
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

    

## Drawable ##

## FlowLayout原理 ##
FlowLayout有一个行的概念，即内部有个Line的类来描述FlowLayout中的一行。测量的时候根据孩子的宽度放入一行行中，如果一行已经放满孩子，则新建一行。
布局时，FlowLayout会按行来布局孩子。

# 推荐页面 #


## 功能分析 ##
* 文本随机分布，随机颜色，随机大小
* 滑动切换页面，缩放动画，淡入淡出动画
* [StellarMap](https://github.com/uncleleonfan/StellarMap)


## 加载数据 ##

    
    @GET("recommend")
    Call<List<String>> listRecommend();


    
    @Override
    protected void startLoadData() {
        call = NGYBRetrofit.getInstance().getApi().listRecommend();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                dataList = response.body();
                Toasty.info(getContext(), "" + dataList.get(0), Toast.LENGTH_SHORT).show();
                //网络回来的饿时候,网络比较慢,界面可能已经销毁
                if (!call.isCanceled()) {
                    onLoadDataSuccess();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                if (!call.isCanceled()) {
                    onDataLoadFailed();
                }
            }
        });
    }

## 创建视图 ##
    
    @Override
    protected View onCreateContentView() {
        StellarMap stellarMap = new StellarMap(getContext());
        int padding = getResources().getDimensionPixelSize(R.dimen.padding);
        stellarMap.setInnerPadding(padding, padding, padding, padding);
        stellarMap.setAdapter(new RecommendAdapter(getContext(), dataList));
        stellarMap.setRegularity(15, 20);//设置网络,// 避免重叠情况
        stellarMap.setGroup(0);//初始化显示第0组
        return stellarMap;
    }

## 创建StellarMap.Adapter ##

    
    /**
     * @return 获取组的个数
     */
    @Override
    public int getGroupCount() {
        int count = list.size() / DEFAULT_PAGE_SIZE;
        if (list.size() % DEFAULT_PAGE_SIZE != 0) {
            //有余数,增加一个页面
            count++;
        }
        return count;
    }

    /**
     * @param i 组的下标
     * @return 获取一个组对应的元素的个数
     */
    @Override
    public int getCount(int i) {
        if (list.size() % DEFAULT_PAGE_SIZE != 0) {
            //有余数,最后一个页面的元素的个数就是余数的大小
            if (i == getGroupCount() - 1) {
                return list.size() % DEFAULT_PAGE_SIZE;
            }
        }
        return DEFAULT_PAGE_SIZE;
    }

    /**
     * @param i    组的下标
     * @param i1   在某个组里面某个位置
     * @param view 回收的view
     * @return 返回对应组对应位置的条目
     */
    @Override
    public View getView(int i, int i1, View view) {
        if (view == null) {
            view = new TextView(context);
        }
        TextView textview = (TextView) view;
        //获取对应位置的数据
        int i2 = i * DEFAULT_PAGE_SIZE + i1;
        String data = list.get(i2);
        textview.setText(data);
        int textSize = 14 + random.nextInt(6);//14--20
        textview.setTextSize(textSize);
        textview.setTextColor(getRandomColor());
        return view;
    }

    private int getRandomColor() {
        int alpha = 255;
        int red = 30 + random.nextInt(170);//30-200
        int green = 30 + random.nextInt(170);
        int blue = 30 + random.nextInt(170);
        int argb = Color.argb(alpha, red, green, blue);
        return argb;
    }

    /**
     * @param i 组的下标
     * @param b 放大
     * @return 返回下一组放大动画的下标
     */
    @Override
    public int getNextGroupOnZoom(int i, boolean b) {
        if (b) {
            return (i + 1) % getGroupCount();
        } else {
            return (i - 1 + getGroupCount()) % getGroupCount();
        }
    }

## StellarMap原理 ##
StellarMap内部维护两个RandomLayout, 一个显示，一个隐藏。RandomLayout随机分散摆放里面的子控件。StellarMap同时监听用户手势来动画切换两个RandomLayout。
### RandomLayout的基本原理 ###


通过调用`stellarMap.setRegularity(15, 20);`将RandomLayout划分成很多的小方格，在布局子控件时，随机选取一个小方格摆放，如果小方格已经有控件摆放或者摆放时跟其他的控件发生重叠
则再随机选取一个小方格摆放，直到找到合适的位置。

# BaseListFragment抽取 #
## 共性 ##
### 布局 ###
由于首页，应用，游戏，专题，分类都是List的形式，所以可以抽取一个ListView。

### 点击监听 ###

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

### ListView的头 ###

            if (view != null) {
	            listView.addHeaderView(view);
	        }

## 特性 ##
### 不同的adpater ###

    
    /**
     * @return 子类实现该方法返回一个adapter
     */
    protected abstract BaseAdapter onCreateAdapter();

### 对条目点击事件的处理 ###

    
    /**
     * @param position 子类覆盖该方法,完成自己点击事件的处理
     */
    protected void onListItemClick(int position) {

    }


# BaseListAdapter的抽取 #
## 共性 ##
### 上下文Context和数据集合 ###

    
    public BaseListAdapter(Context context, List<T> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

### getCount ###

    
    @Override
    public int getCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();

    }

### getItem ###

    
    /**
     * @param position
     * @return 返回对应位置的数据
     */
    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

### getItemId ###

    
    @Override
    public long getItemId(int position) {
        return position;
    }

### getView ###

    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = onCreateItemView(position);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        onBindItemView(vh.itemView, position);
        return convertView;
    }


### ViewHolder ###

    
    public static class ViewHolder {
        private View itemView;

        public ViewHolder(View item) {
            itemView = item;
        }
    }


## 特性 ##
### 条目视图的创建 ###

    
    /**
     * @param position
     * @return 子类必须实现该方法来完成条目的创建
     */
    abstract View onCreateItemView(int position);

### 条目视图的绑定

    
    /**
     * @param itemView
     * @param position 子类必须实现该方法来完成条目的绑定
     */
    abstract void onBindItemView(View itemView, int position);


# 分类界面 #

为了便于页面的扩展，分类界面显示采用ListView, 所以继承BaseListFragment。
## 加载数据 ##

    
    @Override
    protected void startLoadData() {
        Call<List<CategoryItemBean>> listCall = NGYBRetrofit.getInstance().getApi().listCategory();
        listCall.enqueue(new Callback<List<CategoryItemBean>>() {
            @Override
            public void onResponse(Call<List<CategoryItemBean>> call, Response<List<CategoryItemBean>> response) {
                dataList = response.body();
                Toasty.info(getContext(), "" + dataList.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                onLoadDataSuccess();
            }

            @Override
            public void onFailure(Call<List<CategoryItemBean>> call, Throwable t) {
                onDataLoadFailed();
            }
        });
    }

## 创建Adapter ##

    
    @Override
    protected BaseAdapter onCreateAdapter() {
        //数据集合肯定有值,数据加载成功后才创建布局
        return new CategoryAdapter(getContext(), dataList);
    }


## CategoryItemView ##
分类界面列表的每个条目为CategoryItemView，它由一个标题（TextView）和一个网格布局（TableLayout）组成，根据数据动态地向网格中添加视图。其他网格布局还可以是GridView, RecyclerView加GridLayoutMananger, GridLayout。这里根据网络返回的数据结构选择TableLayout。



    
    public void bindView(CategoryItemBean categoryItemBean) {
        title.setText(categoryItemBean.getTitle());
        //因为CategoryInfoItemView要加入到TableRow里面,所以它的布局参数是TableRow来定义的
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
        //获取屏幕宽度
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        //TableLayout的1/3
        layoutParams.width = (displayMetrics.widthPixels - tableLayout.getPaddingLeft() - tableLayout.getPaddingRight()) / 3;
        layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT;
        //清除TableLayout里面所有的行
        tableLayout.removeAllViews();
        //刷新TableLayout里面所有的行
        for (int i = 0; i < categoryItemBean.getInfos().size(); i++) {
            TableRow tableRow = new TableRow(getContext());
            //获取一行的数据
            CategoryItemBean.InfosBean infosBean = categoryItemBean.getInfos().get(i);
            CategoryInfoItemView categoryInfoItemView = new CategoryInfoItemView(getContext());
            //将CategoryInfoItemView宽度设置为TableLayout宽度的1/3
            categoryInfoItemView.setLayoutParams(layoutParams);
            categoryInfoItemView.bindView(infosBean.getName1(), infosBean.getUrl1());
            tableRow.addView(categoryInfoItemView);
            if (infosBean.getName2().length() > 0) {
                CategoryInfoItemView categoryInfoItemView1 = new CategoryInfoItemView(getContext());
                categoryInfoItemView1.bindView(infosBean.getName2(), infosBean.getUrl2());
                categoryInfoItemView1.setLayoutParams(layoutParams);
                tableRow.addView(categoryInfoItemView1);
            }
            if (infosBean.getName3().length() > 0) {
                CategoryInfoItemView categoryInfoItemView1 = new CategoryInfoItemView(getContext());
                categoryInfoItemView1.bindView(infosBean.getName3(), infosBean.getUrl3());
                categoryInfoItemView1.setLayoutParams(layoutParams);
                tableRow.addView(categoryInfoItemView1);
            }
            //往TableLayout加入一行
            tableLayout.addView(tableRow);
        }
    }

## CategoryInfoItemView ##
CategoryInfoItemView为CategoryItemView中一个子条目的视图。




### 加载图片 ###

        public static final String IMAGE_URL = "http://47.105.71.243:8080/GooglePlayServer/image?name=";

            Glide.with(getContext()).load(Constant.IMAGE_URL + url).into(categoryInfoImage);


# BaseLoadMoreListFragment的抽取 #
## 共性 ##
首页，应用，游戏，专题都能够滚动到底部加载更多，都显示一个加载进度条。

    
    @Override
    protected void initListView() {
        super.initListView();
        //ListView初始化
        getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    //滚动停止时,判断是否滚动到底部
                    if (shouldStartLoadMore()) {
                        //加载更多数据
                        loadMoreData();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

## 特性 ##
加载更多数据的实现

        protected abstract void loadMoreData();

# BaseLoadMoreListAdapter的抽取 #
由于列表的item的视图都是由Adapter来决定的，且首页，应用，游戏，专题都有一个加载进度条，所以可以抽取一个Adapter来封装加载进度条的创建


## 共性 ##
### getCount ###

    
    @Override
    public int getCount() {
        if (getDataList() == null) {
            return 0;
        }
        return getDataList().size() + 1; //多了一进度条的条目
    }

### getViewTypeCount ###

    
    @Override
    public int getViewTypeCount() {
        return 2;
    }

### getItemViewType ###

    
    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return ITEM_TYPE_ZERO;
        } else {
            return ITEM_TYPE_ONE;
        }
    }

### onCreateItemView###

    
    @Override
    View onCreateItemView(int position) {
        if (getItemViewType(position) == ITEM_TYPE_ONE) {
            //创建普通类的条目
            return onCreateOneItemView();
        } else {
            //创建一个进度条
            return new LoadingView(getContext());
        }
    }

### onBindItemView ###

    
    @Override
    void onBindItemView(View itemView, int position) {
        if (getItemViewType(position) == ITEM_TYPE_ONE) {
            onBindNormalItemView(itemView, position);
        }
    }


### LoadingMoreProgressView ###


## 特性 ##
### onCreateNormalView ###

    
    
    /**
     * @return 子类必须实现方法实现一个普通的条目
     */
    abstract View onCreateOneItemView();


### onBindNormalView

    
    abstract void onBindNormalItemView(View itemView, int position);



# 专题界面 #

## 创建Api接口 ##

    
    @GET("subject")
    Call<List<SubjectItemBean>> listSubject(@Query("index") int index);

> 注意：index为分页查询的参数，默认20条为一页，可以传入0,20,40,60, 60之后就没有更多数据。

## 加载数据 ##

    
    @Override
    protected void startLoadData() {
        Call<List<SubjectItemBean>> listCall = NGYBRetrofit.getInstance().getApi().listSubject(0);
        listCall.enqueue(new Callback<List<SubjectItemBean>>() {
            @Override
            public void onResponse(Call<List<SubjectItemBean>> call, Response<List<SubjectItemBean>> response) {
                dataList = response.body();
                Toasty.info(getContext(), "" + dataList.get(0).getDes(), Toast.LENGTH_SHORT).show();
                onLoadDataSuccess();
            }

            @Override
            public void onFailure(Call<List<SubjectItemBean>> call, Throwable t) {
                onDataLoadFailed();
            }
        });
    }

## 创建Adapter ##

    
    @Override
    protected BaseAdapter onCreateAdapter() {
        return new SubjectAdapter(getContext(), dataList);
    }


## 加载更多数据 ##
    
    
    @Override
    protected void loadMoreData() {
        Call<List<SubjectItemBean>> listCall = NGYBRetrofit.getInstance().getApi().listSubject(dataList.size());
        listCall.enqueue(new Callback<List<SubjectItemBean>>() {
            @Override
            public void onResponse(Call<List<SubjectItemBean>> call, Response<List<SubjectItemBean>> response) {
                //将更多的数据加入当前的数据集合中
                dataList.addAll(response.body());
                getListAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<SubjectItemBean>> call, Throwable t) {

            }
        });
    }

# BaseAppListFragment的抽取 #
除了首页多了一个轮播图外，首页，应用，游戏界面具有相同的app的列表，所以抽取一个BaseAppListFragment。
## 共性 ##
### 数据列表 ###

        protected List<AppItemBean> dataList = new ArrayList<>();

> 注意 AppListItemBean中的starts字段要是float类型，不能是int类型，否则json解析会出错, Retrofit会回调onFailure方法。

### 相同的Adapter ###

    
    @Override
    protected BaseAdapter onCreateAdapter() {
        return new AppListAdapter(getContext(), dataList);
    }


# AppListAdapter的抽取 #

## onCreateNormalView ##

    
    @Override
    View onCreateOneItemView() {
        return new AppItemView(getContext());
    }

## onBindNormalView ##

    
    @Override
    void onBindNormalItemView(View itemView, int position) {
        ((AppItemView) itemView).bindView(getDataList().get(position));
    }

## AppListItemView ##


### CircleDownloadView ###



### AppListItemView的绑定 ###

    
    /**
     * @param appItemBean 当listview滚动,绑定条目
     */
    public void bindView(AppItemBean appItemBean) {
        Glide.with(getContext()).load(Constant.IMAGE_URL + appItemBean.getIconUrl()).into(appIcon);
        appName.setText(appItemBean.getName());
        ratingBar.setRating(appItemBean.getStars());
        size.setText(Formatter.formatFileSize(getContext(), appItemBean.getSize()));
        appDesc.setTag(appItemBean.getDes());
        circleDownloadView.syncState(appItemBean);
    }

>注意，创建AppListItemBean时，starts字段需为float类型


# 游戏页面 #

## 创建接口 ##

    
    @GET("game")
    Call<List<AppItemBean>> listGame(@Query("index") int index);

## 加载数据 ##

    
    @Override
    protected void startLoadData() {
        Call<List<AppItemBean>> listCall = NGYBRetrofit.getInstance().getApi().listGame(0);
        listCall.enqueue(new Callback<List<AppItemBean>>() {
            @Override
            public void onResponse(Call<List<AppItemBean>> call, Response<List<AppItemBean>> response) {
                if (response != null && response.body() != null && response.body().size() > 0) {

                    dataList.addAll(response.body());
                    onLoadDataSuccess();
                }
            }

            @Override
            public void onFailure(Call<List<AppItemBean>> call, Throwable t) {
                onDataLoadFailed();
            }
        });
    }

## 加载更多数据 ##

        @Override
	    protected void loadMoreData() {
	        Call<List<AppItemBean>> listCall = NGYBRetrofit.getInstance().getApi().listGame(dataList.size());
	        listCall.enqueue(new Callback<List<AppItemBean>>() {
	            @Override
	            public void onResponse(Call<List<AppItemBean>> call, Response<List<AppItemBean>> response) {
	                dataList.addAll(response.body());
	                getListAdapter().notifyDataSetChanged();
	            }
	
	            @Override
	            public void onFailure(Call<List<AppItemBean>> call, Throwable t) {
	
	            }
	        });
	    }


# 应用页面 #


## 创建接口 ##

    
    @GET("app")
    Call<List<AppItemBean>> listApp(@Query("index") int index);

## 加载数据 ##

    
    @Override
    protected void startLoadData() {
        Call<List<AppItemBean>> listCall = NGYBRetrofit.getInstance().getApi().listApp(0);
        listCall.enqueue(new Callback<List<AppItemBean>>() {
            @Override
            public void onResponse(Call<List<AppItemBean>> call, Response<List<AppItemBean>> response) {
                if (response != null) {
                    if (response.body() != null && response.body().size() > 0) {

                        dataList.addAll(response.body());
                        onLoadDataSuccess();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<AppItemBean>> call, Throwable t) {
                onDataLoadFailed();
            }
        });
    }

## 加载更多数据 ##

        @Override
	    protected void loadMoreData() {
	        Call<List<AppItemBean>> listCall = NGYBRetrofit.getInstance().getApi().listApp(dataList.size());
	        listCall.enqueue(new Callback<List<AppItemBean>>() {
	            @Override
	            public void onResponse(Call<List<AppItemBean>> call, Response<List<AppItemBean>> response) {
	                dataList.addAll(response.body());
	                getListAdapter().notifyDataSetChanged();
	            }
	
	            @Override
	            public void onFailure(Call<List<AppItemBean>> call, Throwable t) {
	            }
	        });
	    }

# 首页页面 #


## 创建接口 ##

    
    @GET("home")
    Call<HomeBean> listHome(@Query("index") int index);

## 加载数据 ##

    
    @Override
    protected void startLoadData() {
        Call<HomeBean> homeBeanCall = NGYBRetrofit.getInstance().getApi().listHome(0);
        homeBeanCall.enqueue(new Callback<HomeBean>() {
            @Override
            public void onResponse(Call<HomeBean> call, Response<HomeBean> response) {
                Toasty.success(getContext(), "success", Toast.LENGTH_SHORT).show();
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getList() != null && response.body().getList().size() > 0) {
                            dataList.addAll(response.body().getList());
                            pictures = response.body().getPicture();
                            onLoadDataSuccess();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<HomeBean> call, Throwable t) {
                Toasty.error(getContext(), "failed" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                onDataLoadFailed();
            }
        });

    }

## 加载更多数据 ##

    
    @Override
    protected void loadMoreData() {
        Call<HomeBean> homeBeanCall = NGYBRetrofit.getInstance().getApi().listHome(dataList.size());
        homeBeanCall.enqueue(new Callback<HomeBean>() {
            @Override
            public void onResponse(Call<HomeBean> call, Response<HomeBean> response) {
                dataList.addAll(response.body().getList());
                getListAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<HomeBean> call, Throwable t) {

            }
        });
    }

## 添加轮播图 ##

### 集成FunBanner ###

    
	allprojects {
	    repositories {
	        google()
	        jcenter()
	        maven { url 'https://www.jitpack.io' }
	    }
	}

    implementation 'com.github.uncleleonfan:funbanner:1.2.0'

### 初始化FunBanner ###

    
    @Override
    protected View onCreateHeaderView() {
        FunBanner funBanner = new FunBanner.Builder(getContext())
                .setHeightWidthRatio(0.377f)
                .setDotNormalColor(Color.WHITE)
                .setImageUrlHost(Constant.IMAGE_URL)
                .setImageUrls(pictures)
                .setDotSelectedColor(getResources().getColor(R.color.colorPrimary))
                .build();
        return funBanner;
    }

### 调整加载更多时的位置 ###

    
    protected void initListView() {
        //listview的初始化
        listView.setDivider(null);//去掉分割线
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //position会将头计算在内
                position = position - getListView().getHeaderViewsCount();//去掉头的计算
                onListItemClick(position);
            }
        });
    }

### 调整item点击的位置 ###

    
    protected void initListView() {
        //listview的初始化
        listView.setDivider(null);//去掉分割线
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //position会将头计算在内
                position = position - getListView().getHeaderViewsCount();//去掉头的计算
                onListItemClick(position);
            }
        });
    }


# 应用详情页面 #

## AppDetailActivity ##
### 初始化ActionBar ###

    
	    @Override
	    protected void init() {
	        super.init();
	        setSupportActionBar(toolBar);
	        ActionBar supportActionBar = getSupportActionBar();
	        supportActionBar.setTitle("应用详情");
	        supportActionBar.setDisplayHomeAsUpEnabled(true);
	        setStatusBarColor();
	//        packageName = getIntent().getStringExtra("package_name");
	//        Toast.makeText(this, ""+packageName, Toast.LENGTH_SHORT).show();
	    }

### 配置状态条颜色 ###

    
    /**
     * 由于状态栏的颜色在主题中配置成透明了,所以需要些代码将状态栏的颜色改成想要的颜色
     */
    private void setStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }

#### MainActivity中状态栏背景颜色怎么实现的？ ####
MainActivity中的状态栏颜色也是透明的，但为什么我在style中配置下colorPrimaryDark，也能改变状态栏底部的颜色？这是怎么做到的呢？这其实是DrawLayout自己画出来的！！！ DrawLayout会获取colorPrimaryDark的属性值来绘制一个背景。

    
    public void onDraw(Canvas c) {
        super.onDraw(c);
        if (this.mDrawStatusBarBackground && this.mStatusBarBackground != null) {
            int inset;
            if (VERSION.SDK_INT >= 21) {
                inset = this.mLastInsets != null ? ((WindowInsets)this.mLastInsets).getSystemWindowInsetTop() : 0;
            } else {
                inset = 0;
            }

            if (inset > 0) {
                this.mStatusBarBackground.setBounds(0, 0, this.getWidth(), inset);
                this.mStatusBarBackground.draw(c);
            }
        }

    }



### 返回按钮 ###

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

## AppDetailFragment ##
### 加载数据 ###

    
    @Override
    protected void startLoadData() {
        String packageName = getActivity().getIntent().getStringExtra("package_name");
        Call<AppDetailBean> appDetail = NGYBRetrofit.getInstance().getApi().getAppDetail(packageName);
        appDetail.enqueue(new Callback<AppDetailBean>() {
            @Override
            public void onResponse(Call<AppDetailBean> call, Response<AppDetailBean> response) {
                data = response.body();
                Toasty.info(getContext(), "" + data.getName(), Toast.LENGTH_SHORT).show();
                onLoadDataSuccess();
            }

            @Override
            public void onFailure(Call<AppDetailBean> call, Throwable t) {
                onDataLoadFailed();
            }
        });
    }

### 创建视图并且绑定 ###

    
    @Override
    protected View onCreateContentView() {
        View inflate = View.inflate(getContext(), R.layout.fragment_app_detail, null);
        //app信息
        AppDetailInfoView appDetailInfoView = inflate.findViewById(R.id.app_detail_info);
        appDetailInfoView.bindView(data);
        //app安全
        AppDetailSecurityView appDetailSecurity = inflate.findViewById(R.id.app_detail_security);
        appDetailSecurity.bindView(data);
        //app截图
        AppDetailGalleryView appDetailGallery = inflate.findViewById(R.id.app_detail_gallery);
        appDetailGallery.bindView(data);
        //app描述
        AppDetailDesView appDetailDes = inflate.findViewById(R.id.app_detail_des);
        appDetailDes.bindView(data);
        DownloadButton downloadButton = inflate.findViewById(R.id.download_button);
        downloadButton.syncState(data);//同步apk的下载状态
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager.getInstance().handleClick(getContext(), data.getPackageName());
            }
        });
        return inflate;
    }


### 应用信息 AppDetailInfoView ###


### 应用安全 AppDetailSecurityView ###


#### 绑定视图 ####

    
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

#### 初始化高度和保存展开后高度 ####
    `
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
    }`

#### 打开或者关闭 ####

    
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

#### 动画工具类 ####

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



###  应用截图 AppDetailGalleryView ###

#### 绑定视图 ####

    
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


### 应用描述 AppDetailDesView ###

#### 初始化高度和保存展开后高度 ####

    
    public AppDetailDesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_app_detail_des, this);
        ButterKnife.bind(this, this);
        //绘制流程没有走,测量没有走就没有高度
        expandHeight = appDesc.getMeasuredHeight();
        //视图树的观察者监听绘制流程中布局的完成
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //获取正常展开后的高度
                expandHeight = appDesc.getMeasuredHeight();
                //如果高度大于7行,则设置成7行
                if (appDesc.getLineCount() > 7) {
                    appDesc.setLines(7);
                }
            }
        });
    }

#### 打开或者关闭 ####

    
    @OnClick(R.id.arrow)
    public void onViewClicked() {
        if (isExpand) {
            //折叠  从展开后的高度到起始高度
            AnimationUtils.animationViewHeight(appDesc, expandHeight, startHeight);
            AnimationUtils.rotateView(arrow, -180f, 0);
        } else {
            //展开
            startHeight = appDesc.getMeasuredHeight();
            AnimationUtils.animationViewHeight(appDesc, startHeight, expandHeight);
            AnimationUtils.rotateView(arrow, 0, -180f);
        }
        isExpand = !isExpand;
    }


#### 自定义控件DownloadButton ####


    
	    @Override
	    protected void onDraw(Canvas canvas) {
	        if (enableProgress) {
	            //绘制进度
	            int right = (int) (progress * 1.0f / 100 * getWidth());//根据进度计算矩形的右边位置
	//            canvas.drawRect(0,,right,getHeight(),paint);
	            drawable.setBounds(0, 0, right, getHeight());
	            drawable.draw(canvas);
	        }
	        super.onDraw(canvas);//不要去掉,super.onDraw实现了按钮文本的绘制
	    }

# 应用下载 #
## DownloadManager ##
DownloadManger完成对应用下载的管理，使用单例模式。

    
    public static DownloadManager getInstance() {
        if (downloadManager == null) {
            synchronized (DownloadManager.class) {
                if (downloadManager == null) {
                    downloadManager = new DownloadManager();
                }
            }
        }
        return downloadManager;
    }

## 创建APK存放目录 ##
    
    public void createDownloadDir() {
        File file = new File(DOWNLOAD_DIR);
        if (!file.exists()) {
            //如果文件夹的路径不存在,则创建该文件夹
            file.mkdirs();//如果父目录不存在,则还会创建父目录
        }
    }

## 下载状态 ##

        public static final int STATE_UN_DOWNLOAD = 0; //未下载
	    public static final int STATE_DOWNLOADING = 1; //下载中
	    public static final int STATE_PAUSE = 2; //暂停下载
	    public static final int STATE_WAITING = 3; //等待下载
	    public static final int STATE_FAILED = 4; //下载失败
	    public static final int STATE_DOWNLOADED = 5; //下载完成
	    public static final int STATE_INSTALLED = 6;//已安装




## 下载数据结构 DownloadInfo##
由于下载一个app的过程中会产成很多数据，包括下载app的名字，下载的进度，下载的状态等，这里构建一个描述下载一个app的数据结构。另外在app列表界面和app详情界面需要共享一个应用的下载信息的。
	
    public class DownloadInfo {
	    public int progress; //0---100
	    public int downloadStatus;//下载状态
	    public String packageName;//包名
	    public long apkSize;//apk大小
	    public long downloadSize;//已经下载了多少
	    public String filePath;//apk文件路径
	    public String downloadUrl;//apk下载的url
	    public UpdateDownloadInfoListener listener; //监听器
	    public DownloadManager.DownloadTask downloadTask; //下载任务
	}


## 获取DownloadInfo ##

    
    public DownloadInfo getDownloadInfo(Context context, String packageName, int apkSize, String downloadUrl) {
        //先查缓存,如果缓存里面存在,则直接返回
        if (downloadInfoHashMap.get(packageName) != null) {
            return downloadInfoHashMap.get(packageName);
        }

        DownloadInfo downloadInfo = new DownloadInfo();
        //添加下载相关的信息
        downloadInfo.apkSize = apkSize;
        downloadInfo.packageName = packageName;
        //保存下载的url
        downloadInfo.downloadUrl = downloadUrl;
        //拿到对应包名的app里面的activity信息
        if (isInstalled(context, packageName)) {
            //判断是否已经安装
            downloadInfo.downloadStatus = STATE_INSTALLED;
        } else if (isDownloaded(downloadInfo)) {
            downloadInfo.downloadStatus = STATE_DOWNLOADED;
        } else {
            downloadInfo.downloadStatus = STATE_UN_DOWNLOAD;
        }
        //缓存downloadinfo
        downloadInfoHashMap.put(packageName, downloadInfo);
        return downloadInfo;
    }
## 是否安装 ##

    
    /**
     * @param context
     * @param packageName
     * @return 判断是否已经安装apk
     */
    private boolean isInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

## 打开App ##

    
    private void openApp(Context context, String packageName) {
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(launchIntentForPackage);
    }

## 是否已下载 ##

    
    /**
     * @param downloadInfo
     * @return 是否已经下载完成
     */
    private boolean isDownloaded(DownloadInfo downloadInfo) {
        //获取下载目录中的apk文件的大小,比较是否很apk应该有的大小一致,如果一致,则下载完成
        //创建一个apk对应文件
        String filePath = DOWNLOAD_DIR + downloadInfo.packageName + ".apk";//apk对应的路径
        downloadInfo.filePath = filePath;//更新apk 的文件路径
        File apk = new File(filePath);
        if (apk.exists()) {
            //保存已经下载的大小,为了断电续传
            downloadInfo.downloadSize = apk.length();
            //判断大小,是否一致
            if (apk.length() == downloadInfo.apkSize) {
                return true;
            }
        }
        return false;
    }

## 安装App ##

    
    private void installApk(Context context, String packageName) {
        //7.0以前,跳转系统安装界面,让系统安装程序获取apk文件去安装
        //FileUriExposedException
        String filePath = DOWNLOAD_DIR + packageName + ".apk";//apk对应的路径
        File file = new File(filePath);
        Uri uri = null;//apk文件对应
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (file.exists()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //大于等于版本7.0
                uri = FileProvider.getUriForFile(context, "cqq.googleplay.fileprovider", file);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//暂时授权获取apk文件
            } else {
                uri = Uri.fromFile(file);
            }
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            context.startActivity(intent);
        }
    }

### 安装apk7.0适配 ###

    
        <provider
            android:name="android.support.v4.content.FileProvider"
            android:authorities="cqq.googleplay.fileprovider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths" />
        </provider>

## 下载等待 ##
当执行下载任务之前，先进入等待状态

    
    private void downloadApk(DownloadInfo downloadInfo) {
	        //开始下载是,状态更新等待状态
	        downloadInfo.downloadStatus = STATE_WAITING;
	        //通知UI   更新状态
	//        downloadInfo.listener.onUpdate(downloadInfo);
	        notifyDownloadInfoChange(downloadInfo);
	//        new Thread(new DownloadTask(downloadInfo)).start();
	        DownloadTask downloadTask = new DownloadTask(downloadInfo);
	        //将下载任务保存在下载信息里面
	        downloadInfo.downloadTask = downloadTask;
	        threadPoolExecutor.execute(downloadTask);
	    }


## 下载任务 ##

    
	    public class DownloadTask implements Runnable {
	        private DownloadInfo downloadInfo;
	
	        public DownloadTask(DownloadInfo downloadInfo) {
	            this.downloadInfo = downloadInfo;
	        }
	
	        @Override
	        public void run() {
	            //开始下载apk
	            String url = "http://47.105.71.243:8080/GooglePlayServer/download?name=" + downloadInfo.downloadUrl + "&range=" + downloadInfo.downloadSize;
	//            String url = "http://192.168.0.103:8080/GooglePlayServer/download?name="+ downloadInfo.downloadUrl+"&range="+downloadInfo.downloadSize;
	            Request request = new Request.Builder().get().url(url).build();
	            OkHttpClient okHttpClient = new OkHttpClient();
	            InputStream inputStream = null;
	            FileOutputStream fileOutputStream = null;
	            try {
	                Response res = okHttpClient.newCall(request).execute();
	                if (res.isSuccessful()) {
	                    File file = new File(downloadInfo.filePath);
	                    if (!file.exists()) {
	                        file.createNewFile();//不存在文件则创建
	                    }
	                    inputStream = res.body().byteStream();//输入流
	                    //输出流
	                    fileOutputStream = new FileOutputStream(file, true);//true写到文件末尾,处理断电续传
	                    //定义Buffer
	                    byte[] buffer = new byte[1024];//每次读1kb
	                    int len = -1;//读取字节数
	                    while ((len = inputStream.read(buffer)) != -1) {//从输入入流读取1024字节,如果读取结束read返回-1
	                        //判断是否为暂停下载状态,如果是则跳出循环
	                        if (downloadInfo.downloadStatus == STATE_PAUSE) {
	                            return;
	                        }
	                        //将buffer字节写入输出流
	                        fileOutputStream.write(buffer, 0, len);
	                        downloadInfo.downloadSize += len;//计算已经下载字节大小
	                        //计算进度0--100
	                        int progress = (int) (downloadInfo.downloadSize * 1.0f / downloadInfo.apkSize * 100);
	                        //进度变大才进行刷新绘制,优化
	                        if (progress > downloadInfo.progress) {
	                            downloadInfo.progress = progress;
	                            downloadInfo.downloadStatus = STATE_DOWNLOADING;//更新状态,正在下载的状态
	//                            downloadInfo.listener.onUpdate(downloadInfo);
	                            notifyDownloadInfoChange(downloadInfo);
	                        }
	                        //断电续传,如果进入达到100,那么不要在读数据,直接跳出循环,否则出现超时
	                        if (progress == 100) {
	                            break;
	                        }
	                    }
	                    //更新状态为已经下载完成
	                    downloadInfo.downloadStatus = STATE_DOWNLOADED;
	//                    downloadInfo.listener.onUpdate(downloadInfo);
	                    notifyDownloadInfoChange(downloadInfo);
	                } else {
	                    //失败
	                    downloadInfo.downloadStatus = STATE_FAILED;
	//                    downloadInfo.listener.onUpdate(downloadInfo);
	                    notifyDownloadInfoChange(downloadInfo);
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	                //失败
	                downloadInfo.downloadStatus = STATE_FAILED;
	//                    downloadInfo.listener.onUpdate(downloadInfo);
	                notifyDownloadInfoChange(downloadInfo);
	            } finally {
	                //关闭输入输出流
	                closeStream(inputStream);
	                closeStream(fileOutputStream);
	            }
	        }
	    }

## 通知更新进度 ##

    
    private void notifyDownloadInfoChange(DownloadInfo downloadInfo) {
        if (downloadInfo != null) {
            downloadInfo.listener.OnUpdate(downloadInfo);
        }
    }

## 通知等待状态 ##

    
	    private void downloadApk(DownloadInfo downloadInfo) {
	        //开始下载是,状态更新等待状态
	        downloadInfo.downloadStatus = STATE_WAITING;
	        //通知UI   更新状态
	//        downloadInfo.listener.onUpdate(downloadInfo);
	        notifyDownloadInfoChange(downloadInfo);
	//        new Thread(new DownloadTask(downloadInfo)).start();
	        DownloadTask downloadTask = new DownloadTask(downloadInfo);
	        //将下载任务保存在下载信息里面
	        downloadInfo.downloadTask = downloadTask;
	        threadPoolExecutor.execute(downloadTask);
	    }

## 通知下载进度 ##

            if (apk.exists()) {
	            //保存已经下载的大小,为了断电续传
	            downloadInfo.downloadSize = apk.length();
	            //判断大小,是否一致
	            if (apk.length() == downloadInfo.apkSize) {
	                return true;
	            }
	        }
	        return false;

## 下载完成去除矩形进度条 ##

    
    private void cancelDownload(DownloadInfo downloadInfo) {
        //从任务队列移除任务
        threadPoolExecutor.remove(downloadInfo.downloadTask);
        //通知更新UI
        downloadInfo.downloadStatus = STATE_UN_DOWNLOAD;
        notifyDownloadInfoChange(downloadInfo);
    }

## 暂停下载 ##
暂停下载只需设置下载状态为STATE_PAUSE，在下载任务while循环中判断是否为暂停状态，如果是则跳出循环。

    
	    private void pauseDownload(DownloadInfo downloadInfo) {
	        //将状态改为暂停状态
	        downloadInfo.downloadStatus = STATE_PAUSE;
	        //通知更新ui更新状态
	//        downloadInfo.listener.onUpdate(downloadInfo);
	        notifyDownloadInfoChange(downloadInfo);
	    }


## 继续下载 ##
继续下载只需重新执行下载即可。

                case STATE_PAUSE:
	                //继续下载
	                downloadApk(downloadInfo);
	                break;


## 下载失败更新ui ##

                case STATE_FAILED:
	                //重试下载,即断电续传
	                downloadApk(downloadInfo);
	                break;


## 重试下载 ##

                case STATE_FAILED:
	                //重试下载,即断电续传
	                downloadApk(downloadInfo);
	                break;


# CircleDownloadView的实现 #


## 同步状态

    
    /**
     * @param appItemBean 同步下载的信息
     */
    public void syncState(AppItemBean appItemBean) {
        //如果是ListView回收回来的,断掉与之前app下载的联系
        if (downloadInfo != null) {
            //二手的CircleDownloadView  appItemBean还是之前绑定过的数据, downloadInfo也是之前的app的数据
            //断掉与之前app的联系,将监听器置为null,就不会收到以前app下载的更新
            downloadInfo.listener = null;
        }
        this.appItemBean = appItemBean;
        //拿到新的app的下载信息
        downloadInfo = DownloadManager.getInstance().getDownloadInfo(getContext(), appItemBean.getPackageName(),
                appItemBean.getSize(), appItemBean.getDownloadUrl());
        downloadInfo.listener = this;//CircleDownloadView监听新的app下载信息
        //更新下载信息更新UI
        update(downloadInfo);
    }
## 点击处理 ##

    
    @OnClick(R.id.download_icon)
    public void onViewClicked() {
        DownloadManager.getInstance().handleClick(getContext(), appItemBean.getPackageName());
    }


## 等待状态更新 ##

                case STATE_WAITING:
	                //取消下载
	                cancelDownload(downloadInfo);
	                break;


## 绘制 ##

    
	    @Override
	    protected void onDraw(Canvas canvas) {
	        if (enableProgress) {
	            //更加ImageView的边界,计算圆弧的边界
	            int left = downloadIcon.getLeft() - 5;
	            int top = downloadIcon.getTop() - 5;
	            int right = downloadIcon.getRight() + 5;
	            int bottom = downloadIcon.getBottom() + 5;
	            rectF.set(left, top, right, bottom);
	            float startAngle = -90;
	//            float sweepAngle = 45;
	            //根据进度计算扫过的角度
	            float sweepAngel = (progress * 1.0f / 100) * 360;
	            boolean userCenter = false;
	            canvas.drawArc(rectF, startAngle, sweepAngel, userCenter, paint);
	        }
	    }

## 更新进度 ##

    
    public void setProgress(int progress) {
        this.progress = progress;
        //更新文本
        String progressString = progress + "%";
        downloadText.setText(progressString);
        //触发onDraw重新调用
        invalidate();
    }

## 更新下载完成 ##

## 更新暂停状态 ##

## 更新下载失败状态 ##

## 断点续传问题 ##

    `            //判断大小,是否一致
	            if (apk.length() == downloadInfo.apkSize) {
	                return true;
	            }`

## ListView回收问题 ##

    
    /**
     * @param appItemBean 同步下载的信息
     */
    public void syncState(AppItemBean appItemBean) {
        //如果是ListView回收回来的,断掉与之前app下载的联系
        if (downloadInfo != null) {
            //二手的CircleDownloadView  appItemBean还是之前绑定过的数据, downloadInfo也是之前的app的数据
            //断掉与之前app的联系,将监听器置为null,就不会收到以前app下载的更新
            downloadInfo.listener = null;
        }
        this.appItemBean = appItemBean;
        //拿到新的app的下载信息
        downloadInfo = DownloadManager.getInstance().getDownloadInfo(getContext(), appItemBean.getPackageName(),
                appItemBean.getSize(), appItemBean.getDownloadUrl());
        downloadInfo.listener = this;//CircleDownloadView监听新的app下载信息
        //更新下载信息更新UI
        update(downloadInfo);
    }

    private void update(DownloadInfo downloadInfo) {
        switch (downloadInfo.downloadStatus) {
            case DownloadManager.STATE_INSTALLED:
                downloadText.setText("打开");
                downloadIcon.setImageResource(R.mipmap.ic_install);
                break;
            case DownloadManager.STATE_DOWNLOADED:
                downloadText.setText("安装");
                downloadIcon.setImageResource(R.mipmap.ic_install);
                clearProgress();
                break;
            case DownloadManager.STATE_UN_DOWNLOAD:
                clearProgress();
                downloadIcon.setImageResource(R.mipmap.action_download);
                downloadText.setText("下载");
                break;
            case DownloadManager.STATE_WAITING:
                downloadText.setText("等待");
                downloadIcon.setImageResource(R.mipmap.ic_cancel);
                break;
            case DownloadManager.STATE_DOWNLOADING:
                enableProgress = true;//确保开启绘制进度
                setProgress(downloadInfo.progress);//更新进度
                downloadIcon.setImageResource(R.mipmap.ic_pause);
                break;
            case DownloadManager.STATE_PAUSE:
                downloadText.setText("继续");
                downloadIcon.setImageResource(R.mipmap.action_download);
                break;
            case DownloadManager.STATE_FAILED:
                downloadText.setText("重试");
                downloadIcon.setImageResource(R.mipmap.ic_redownload);
                clearProgress();
                break;
        }
    }

## 详情界面返回时刷新界面 ##

    
    @Override
    public void onResume() {
        super.onResume();
        if (getListAdapter()!=null){
            getListAdapter().notifyDataSetChanged();
        }
    }

# 多线程下载 #

##线程运行机制

* 单核cpu，同一时刻只能处理一个线程，多核cpu同一时刻可以处理多个线程
* 操作系统为每个运行线程安排一定的CPU时间----`时间片`，系统通过一种循环的方式为线程提供时间片，线程在自己的时间内运行，因为时间相当短，多个线程频繁地发生切换，因此给用户的感觉就是好像多个线程同时运行一样。



## 线程池 ##
Android中耗时的操作，都会开子线程，线程的创建和销毁是要消耗系统资源的。为了减少频繁的线程的创建和销毁带来的不必要的开销，可以使用线程池。
线程池的优点：

* 重用线程池中的线程,减少因对象创建,销毁所带来的性能开销;

* 能有效的控制线程的最大并发数,提高系统资源利用率,同时避免过多的资源竞争,避免堵塞;

* 能够多线程进行简单的管理,使线程的使用简单、高效。

线程池的应用非常广泛，在众多的开源框架中也总能看到线程池的踪影。

## 线程池涉及的类


* Executor:Java里面线程池的顶级接口。
* ExecutorService:真正的线程池接口。
* ScheduledExecutorService:能和Timer/TimerTask类似，解决那些需要任务重复执行的问题。
* ThreadPoolExecutor(重点):ExecutorService的默认实现。
* ScheduledThreadPoolExecutor:继承ThreadPoolExecutor的ScheduledExecutorService接口实现，周期性任务调度的类实现。
* Executors:可以一行代码创建一些常见的线程池。

## ThreadPoolExecutor介绍 ##
	//构造方法
	public ThreadPoolExecutor(int corePoolSize，//核心池的大小
	                              int maximumPoolSize，//线程池最大线程数
	                              long keepAliveTime，//保持时间
	                              TimeUnit unit，//时间单位
	                              BlockingQueue<Runnable> workQueue，//任务队列
	                              ThreadFactory threadFactory，//线程工厂
	                              RejectedExecutionHandler handler) //异常的捕捉器
###构造相关参数解释
* corePoolSize：`核心池的大小`，这个参数跟后面讲述的线程池的实现原理有非常大的关系。在创建了线程池后，默认情况下，线程池中并没有任何线程，而是等待有任务到来才创建线程去执行任务，除非调用了prestartAllCoreThreads()或者prestartCoreThread()方法，从这2个方法的名字就可以看出，是预创建线程的意思，即在没有任务到来之前就创建corePoolSize个线程或者一个线程。默认情况下，在创建了线程池后，线程池中的线程数为0，当有任务来之后，就会创建一个线程去执行任务，当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中；
* maximumPoolSize：`线程池最大线程数`，这个参数也是一个非常重要的参数，它表示在线程池中最多能创建多少个线程；
* keepAliveTime：`表示线程没有任务执行时最多保持多久时间会终止`。默认情况下，只有当线程池中的线程数大于corePoolSize时，keepAliveTime才会起作用，直到线程池中的线程数不大于corePoolSize，即当线程池中的线程数大于corePoolSize时，如果一个线程空闲的时间达到keepAliveTime，则会终止，直到线程池中的线程数不超过corePoolSize。但是如果调用了allowCoreThreadTimeOut(boolean)方法，在线程池中的线程数不大于corePoolSize时，keepAliveTime参数也会起作用，直到线程池中的线程数为0；
* unit：参数keepAliveTime的`时间单位`，有7种取值

		TimeUnit.DAYS;               //天
		TimeUnit.HOURS;             //小时
		TimeUnit.MINUTES;           //分钟
		TimeUnit.SECONDS;           //秒
		TimeUnit.MILLISECONDS;      //毫秒
		TimeUnit.MICROSECONDS;      //微妙
		TimeUnit.NANOSECONDS;       //纳秒
* workQueue ： `任务队列`，是一个阻塞队列，用来存储等待执行的任务，这个参数的选择也很重要，会对线程池的运行过程产生重大影响，参考BlockingQueue

		ArrayBlockingQueue;
		LinkedBlockingQueue;
		SynchronousQueue;
* threadFactory : `线程工厂`，如何去创建线程的
* handler ： 任务队列添加`异常的捕捉器`，参考 RejectedExecutionHandler
		
		ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。 
		ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。 
		ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
		ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务 

###基础API的介绍
* isShutdown() ： 判断线程池是否关闭
* isTerminated() : 判断线程池中任务是否执行完成
* shutdown() : 调用后不再接收新任务，如果里面有任务，就执行完
* shutdownNow() : 调用后不再接受新任务，如果有等待任务，移出队列；有正在执行的，尝试停止之
* submit() : 提交执行任务
* execute() : 执行任务


###任务提交给线程池之后的处理策略
1. 如果当前线程池中的线程数目小于corePoolSize，则每来一个任务，就会创建新的线程执行这个任务；
2. 如果当前线程池中的线程数目等于corePoolSize，则每来一个任务，会尝试将其添加到任务缓存队列当中
	1. 若添加成功，则该任务会等待空闲线程将其取出去执行；
	2. 若添加失败（一般来说是任务缓存队列已满，针对的是有界队列），则会尝试创建新的线程去执行这个任务；
3. 如果当前线程池中的线程数目达到maximumPoolSize，则会采取任务拒绝策略进行处理；
4. 如果线程池中的线程数量大于 corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止，直至线程池中的线程数目不大于corePoolSize；如果允许为核心池中的线程设置存活时间，那么核心池中的线程空闲时间超过keepAliveTime，线程也会被终止。

###任务提交给线程池之后的处理策略（比喻）
假如有一个工厂，工厂里面有10(`corePoolSize`)个工人，每个工人同时只能做一件任务。因此只要当10个工人中有工人是空闲的，`来了任务就分配`给空闲的工人做；
当10个工人都有任务在做时，如果还来了任务，就把任务进行排队等待(`任务队列`)；如果说新任务数目增长的速度远远大于工人做任务的速度，那么此时工厂主管可能会想补救措施，比如重新招4个临时工人(`创建新线程`)进来；然后就将任务也分配给这4个临时工人做；
如果说着14个工人做任务的速度还是不够，此时工厂主管可能就要考虑不再接收新的任务或者抛弃前面的一些任务了(`拒绝执行`)。
当这14个工人当中有人空闲时，而且空闲超过一定时间(`空闲时间`)，新任务增长的速度又比较缓慢，工厂主管可能就考虑辞掉4个临时工了，只保持原来的10个工人，毕竟请额外的工人是要花钱的

## 阻塞队列的介绍（BlockingQueue）
阻塞队列，如果BlockingQueue是空的，从BlockingQueue取东西的操作将会被阻断进入等待状态，直到BlockingQueue进了东西才会被唤醒，同样，如果BlockingQueue是满的，任何试图往里存东西的操作也会被阻断进入等待状态，直到BlockingQueue里有空间时才会被唤醒继续操作。

1. 基础API介绍
	* 往队列中加元素的方法
		* add(E) : 非阻塞方法， 把元素加到BlockingQueue里，如果BlockingQueue可以容纳，则返回true，否则抛出异常。
		* offer(E) : 非阻塞， 表示如果可能的话，将元素加到BlockingQueue里，即如果BlockingQueue可以容纳，则返回true，否则返回false。
		* put(E)：阻塞方法， 把元素加到BlockingQueue里，如果BlockingQueue没有空间，则调用此方法的线程被阻断直到BlockingQueue里有空间再继续。
	
	* 从队列中取元素的方法
		* poll(time)： 阻塞方法，取走BlockingQueue里排在首位的元素，若不能立即取出，则可以等time参数规定的时间，取不到时返回null。
		* take()：取走BlockingQueue里排在首位的对象，若BlockingQueue为空，阻断进入等待状态直到BlockingQueue有新的对象被加入为止。

2. 子类介绍
	* `ArrayBlockingQueue(有界队列)`： FIFO 队列，规定大小的BlockingQueue，其构造函数必须带一个int参数来指明其大小
	
	* `LinkedBlockingQueue(无界队列)`：FIFO 队列，大小不定的BlockingQueue，若其构造函数带一个规定大小的参数，生成的BlockingQueue有大小限制，若不带大小参数，所生成的BlockingQueue的大小由Integer.MAX_VALUE来决定。

	* `PriorityBlockingQueue`：优先级队列， 类似于LinkedBlockingQueue，但队列中元素非 FIFO， 依据对象的自然排序顺序或者是构造函数所带的Comparator决定的顺序

	* `SynchronousQueue(直接提交策略)`: 交替队列，`队列中操作时必须是先放进去，接着取出来`，交替着去处理元素的添加和移除，这是一个很有意思的阻塞队列，其中每个插入操作必须等待另一个线程的移除操作，同样任何一个移除操作都等待另一个线程的插入操作。因此此队列内部其 实没有任何一个元素，或者说容量是0，严格说并不是一种容器。由于队列没有容量，因此不能调用peek操作，因为只有移除元素时才有元素。
 
## RejectedExecutionHandler介绍
###实现的子类介绍

* ThreadPoolExecutor.AbortPolicy 
	>当添加任务出错时的策略捕获器，如果出现错误，则直接`抛出异常`

* ThreadPoolExecutor.CallerRunsPolicy
	> 当添加任务出错时的策略捕获器，如果出现错误，`直接执行`加入的任务

* ThreadPoolExecutor.DiscardOldestPolicy
	> 当添加任务出错时的策略捕获器，如果出现错误，`移除第一个任务，执行加入的任务`

* ThreadPoolExecutor.DiscardPolicy
	> 当添加任务出错时的策略捕获器，如果出现错误，`不做处理`

## Executors ##
帮助我们方便的生成一些常用的线程池，ThreadPoolExecutor是Executors类的底层实现

### newSingleThreadExecutor ###
创建一个单线程的线程池。这个线程池只有一个线程在工作，也就是相当于单线程串行执行所有任务。如果这个唯一的线程因为异常结束，那么会有一个新的线程来替代它。此线程池>保证所有任务的执行顺序按照任务的提交顺序执行。

### newFixedThreadPool ###
创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程。

### newCachedThreadPool ###
创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，那么就会回收部分空闲（60秒不执行任务）的线程，当任务数增加时，此线程池又可以智能的添加新线程来处理任务。此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小。

### newScheduledThreadPool###
创建一个大小无限的线程池。此线程池支持定时以及周期性执行任务的需求。



## 线程池的创建 ##

    private ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);//创建固定大小的线程池,2个线程,返回的为ThreadPoolExecutor

## 线程池中执行Runnable ##

            threadPoolExecutor.execute(downloadTask);

## 取消下载 ##

    
    private void cancelDownload(DownloadInfo downloadInfo) {
        //从任务队列移除任务
        threadPoolExecutor.remove(downloadInfo.downloadTask);
        //通知更新UI
        downloadInfo.downloadStatus = STATE_UN_DOWNLOAD;
        notifyDownloadInfoChange(downloadInfo);
    }

# 扩展内容 #

## 缓存 ##
### Why? ###
缓存的目的减少服务器负荷，降低延迟提升用户体验。复杂的缓存策略会根据用户当前的网络情况采取不同的缓存策略，比如在2g网络很差的情况下，提高缓存使用的时间；
不用的应用、业务需求、接口所需要的缓存策略也会不一样，有的要保证数据的实时性，所以不能有缓存，有的你可以缓存5分钟，等等。你要根据具体情况所需数据的时效性情况给出不同的方案。

### What? ###
* 内存缓存：通俗的讲就是一个类中的成员变量 （例如LruCache）
* 磁盘缓存：以文件的形式存储在磁盘上 (例如 DiskLruCache)

### How？ ###
#### Cache-Controll ####
Cache-Control 是最重要的规则。这个字段用于指定所有缓存机制在整个请求/响应链中必须服从的指令。缓存指令是单向的，即请求中存在一个指令并不意味着响应中将存在同一个指令。

* [Header Field Definitions](https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html)

#### 配置OKhttp的缓存目录 ####

    
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

#### 初始化 ####

    public class MyApplication extends Application {
	    @Override
	    public void onCreate() {
	        super.onCreate();
	        NGYBRetrofit.getInstance().init(getApplicationContext());
	    }
	}


#### 重写网络响应的Cache-Control ####
如果服务器在网络响应头里配置了Cache-Contorol，那么其实客户端是不需要做任何事情就能使用缓存的，但如果服务器没有配置，我们可以拦截这个网络响应，加入我们自己的配置。

    
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

> [OKhttp Intercepter](https://github.com/square/okhttp/wiki/Interceptors)

## FragmentPagerAdapter和FragmentStatePagerAdapter的区别
### FragmentPagerAdapter ###
* 该类内的每一个生成的 Fragment 都将保存在内存之中，因此适用于那些相对静态的页，数量也比较少的那种；如果需要处理有很多页，并且数据动态性较大、占用内存较多的情况，应该使用FragmentStatePagerAdapter
* 现象：每个位置getItem(position)只走一次

### FragmentStatePagerAdapter ###
* 当页面离开视线后，就会被消除，释放其资源；而在页面需要显示时，生成新的页面。这么实现的好处就是当拥有大量的页面时，不必消耗大量的内存。
* 现象：每个位置getItem(position)可能走多次


## 设计模式 ##

### 使用工厂模式创建Fragment ###
根据不同的条件，生产出不同的对象。

    public class FragmentFactory {
	    public static final int FRAGMENT_HOME = 0;
	    public static final int FRAGMENT_APP = 1;
	    public static final int FRAGMENT_GAME = 2;
	    public static final int FRAGMENT_SUBJECT = 3;
	    public static final int FRAGMENT_RECOMMEND = 4;
	    public static final int FRAGMENT_CATEGORY = 5;
	    public static final int FRAGMENT_HOT = 6;
	
	    /**
	     * @param position
	     * @return 根据不同的位置返回不同的实例
	     */
	    public static Fragment getFragment(int position) {
	        switch (position) {
	            case FRAGMENT_HOME:
	                return new HomeFragment();
	            case FRAGMENT_APP:
	                return new ApplicationFragment();
	            case FRAGMENT_GAME:
	                return new GameFragment();
	            case FRAGMENT_SUBJECT:
	                return new SubjectFragment();
	            case FRAGMENT_RECOMMEND:
	                return new RecommendFragment();
	            case FRAGMENT_CATEGORY:
	                return new CategoryFragment();
	            case FRAGMENT_HOT:
	                return new HotFragment();
	        }
	        return null;
	    }
	}


例如： Retrofit里面的GsonConverterFactory 


### 单例模式 ###

    
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


### 建造者模式 ###
可以链式的配置和创建一个对象

AlertDialog.Builder, Retrofit.Builder，OkHttp.Builder

### 适配器模式 ###

    public class OnPageChangeListenerAdapter implements ViewPager.OnPageChangeListener {
	    @Override
	    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	
	    }
	
	    @Override
	    public void onPageSelected(int position) {
	
	    }
	
	    @Override
	    public void onPageScrollStateChanged(int state) {
	
	    }
	}



### 代理模式 ###


### 观察者模式 ###
* Observable (被观察者)
* Observer （被观察者）
	


    public class Student  implements Observer {
	    @Override
	    public void update(Observable o, Object arg) {
	        System.out.println("收到:"+arg);
	    }
	}

    public class Teacher extends Observable {
	    public void publishMessage(){
	        setChanged();
	        notifyObservers("放假了");
	    }
	}

    
    @Test
    public void test() {
        Teacher teacher = new Teacher();
        Student student = new Student();
        Student student1 = new Student();
        teacher.addObserver(student);
        teacher.addObserver(student1);
        teacher.publishMessage();
    }

	类似接口回调，EventBus，广播




效果图:


![](https://github.com/nangongyibin7219/GooglePlayN/blob/master/aa.gif)