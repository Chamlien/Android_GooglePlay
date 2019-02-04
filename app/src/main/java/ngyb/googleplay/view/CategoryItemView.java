package ngyb.googleplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ngyb.googleplay.R;
import ngyb.googleplay.bean.CategoryItemBean;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/5 10:28
 */
public class CategoryItemView extends RelativeLayout {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.table_layout)
    TableLayout tableLayout;

    public CategoryItemView(Context context) {
        this(context, null);
    }

    public CategoryItemView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CategoryItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View root = LayoutInflater.from(context).inflate(R.layout.view_category_item, this);
        ButterKnife.bind(this, this);
    }

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
}
