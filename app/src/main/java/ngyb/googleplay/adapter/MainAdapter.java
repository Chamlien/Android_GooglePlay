package ngyb.googleplay.adapter;

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
