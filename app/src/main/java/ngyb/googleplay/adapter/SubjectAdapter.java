package ngyb.googleplay.adapter;

import android.content.Context;
import android.view.View;

import java.util.List;

import ngyb.googleplay.bean.SubjectItemBean;
import ngyb.googleplay.view.SubjectItemView;


/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/5 11:45
 */
public class SubjectAdapter extends BaseLoadMoreListAdapter<SubjectItemBean> {
    public SubjectAdapter(Context context, List<SubjectItemBean> dataList) {
        super(context, dataList);
    }

    @Override
    void onBindNormalItemView(View itemView, int position) {
        ((SubjectItemView) itemView).bindView(getDataList().get(position));
    }

    @Override
    View onCreateOneItemView() {
        return new SubjectItemView(getContext());
    }
}
