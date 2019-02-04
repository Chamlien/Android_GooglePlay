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
