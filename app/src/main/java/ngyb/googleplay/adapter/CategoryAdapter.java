package ngyb.googleplay.adapter;

import android.content.Context;
import android.view.View;

import java.util.List;

import ngyb.googleplay.bean.CategoryItemBean;
import ngyb.googleplay.view.CategoryItemView;


/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/5 11:10
 */
public class CategoryAdapter extends BaseListAdapter<CategoryItemBean> {
    public CategoryAdapter(Context context, List<CategoryItemBean> dataList) {
        super(context, dataList);
    }

    @Override
    void onBindItemView(View itemView, int position) {
        ((CategoryItemView) itemView).bindView(getDataList().get(position));
    }

    @Override
    View onCreateItemView(int position) {
        return new CategoryItemView(getContext());
    }
}
