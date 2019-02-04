package ngyb.googleplay.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ngyb.googleplay.R;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/5 18:17
 */
public class AppDetailActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    //    @BindView(R.id.app_detail_fragment)
//    AppDeatilFragment appDetailFragment;
    private String packageName;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_app_detail;
    }

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

    /**
     * 由于状态栏的颜色在主题中配置成透明了,所以需要些代码将状态栏的颜色改成想要的颜色
     */
    private void setStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
