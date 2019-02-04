package ngyb.googleplay.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.leon.stellarmap.lib.StellarMap;

import java.util.List;
import java.util.Random;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/5 11:25
 */
public class RecommendAdapter implements StellarMap.Adapter {
    private final Context context;
    private final List<String> list;
    public static final int DEFAULT_PAGE_SIZE = 15;
    private final Random random;

    public RecommendAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        random = new Random();
    }

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
}
