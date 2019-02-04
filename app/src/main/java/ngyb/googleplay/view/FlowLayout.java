package ngyb.googleplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/5 14:31
 */
public class FlowLayout extends ViewGroup {
    private List<Line> lines = new ArrayList<Line>(); //用来记录描述有多少行view
    private int horizontalSpace = 10;
    private int verticalSpace = 10;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int offsetTop = getPaddingTop();
        //布局每一行
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            line.layout(paddingLeft, offsetTop);
            //增加竖直方向的偏移量
            offsetTop += line.height + verticalSpace;
        }
    }

    public void setSpace(int horizontalSpace, int verticalSpace) {
        this.horizontalSpace = horizontalSpace;
        this.verticalSpace = verticalSpace;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //清空
        lines.clear();
        Line currentLine = null;
        //获取父容器对FlowLayout期望的宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //获取行最大的宽度
        int maxLineWidth = width - getPaddingLeft() - getPaddingRight();
        //测量孩子,并将孩子添加到他所属的Line中
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            //如果孩子不可见
            if (view.getVisibility() == GONE) {
                continue;
            }
            //测量孩子
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            //往lines添加孩子
            if (currentLine == null) {//说明还没有开始添加孩子
                currentLine = new Line(maxLineWidth, horizontalSpace);
                //添加到Lines中
                lines.add(currentLine);
                //添加一个孩子
                currentLine.addView(view);
            } else {
                //行不为空,行中有孩子了
                boolean canAdd = currentLine.canAdd(view);
                if (canAdd) {//可以添加
                    currentLine.addView(view);
                } else {//不可以添加,装不下去,需要新建行
                    currentLine = new Line(maxLineWidth, horizontalSpace);
                    //添加到Lines中
                    lines.add(currentLine);
                    //添加一个孩子
                    currentLine.addView(view);
                }
            }
        }
        float allHeight = 0;
        //计算所有行高和间距的和
        for (int i = 0; i < lines.size(); i++) {
            allHeight += lines.get(i).height;
            //如果不是一行,则需要加间距
            if (i != 0) {
                allHeight += verticalSpace;
            }
        }
        //计算FlowLayout的高度
        int measuredHeight = (int) (allHeight + getPaddingTop() + getPaddingBottom() + 0.5f);
        //设置FlowLayout的宽高
        setMeasuredDimension(width, measuredHeight);
    }

    /**
     * FlowLayout每一行的数据结构
     */
    class Line {
        //属性
        private List<View> viewsLine = new ArrayList<>();//用来记录每一行有几个View
        private float maxWidth; //行最大的宽度
        private float usedWidth; //已经使用了多少宽度
        private float height; //行的高度
        private float marginLeft;
        private float marginRight;
        private float marginTop;
        private float marginBottom;
        private float horizontalSpace; //View和View之间的水平间距

        public Line(int maxWidth, int horizontalSpace) {
            this.maxWidth = maxWidth;
            this.horizontalSpace = horizontalSpace;
        }

        public void addView(View view) {
            //加载View的方法
            int size = viewsLine.size();
            //获取添加view的宽和高
            int viewWidth = view.getMeasuredWidth();
            int viewHeight = view.getMeasuredHeight();
            //size ==0表示还没有添加view
            if (size == 0) {
                //如果添加view的宽度大于最大宽度,name已经使用的宽度就复制给最大宽度
                if (viewWidth > maxWidth) {
                    usedWidth = maxWidth;
                } else {
                    //如果添加view的宽度小于或者等于最大宽度,name已经使用的宽度就赋值添加view的宽度
                    usedWidth = viewWidth;
                }
                height = viewHeight;
            } else {
                //size不为0表示一行里面已经添加了一个view
                //已经使用的宽度就增加一个view的宽度和水平间隔
                usedWidth += viewWidth + horizontalSpace;
                //行的高度去所有view中最大的高度
                height = height < viewHeight ? viewHeight : height;
            }
            //将view记录到集合中
            viewsLine.add(view);
        }

        /**
         * 用来判断是否可以将view添加到行中
         *
         * @param view 要添加到行里面的view
         * @return 表示可以添加, false表示不可以添加
         */
        private boolean canAdd(View view) {
            //判断是否能添加view
            int size = viewsLine.size();
            //如果该行没有添加过view则返回true
            if (size == 0) {
                return true;
            }
            int viewWidth = view.getMeasuredWidth();
            //预计使用的宽度
            float planWidth = usedWidth + horizontalSpace + viewWidth;
            //预计宽度小于等于最大宽度表示可以添加
            return planWidth <= maxWidth;
        }

        /**
         * 给孩子布局
         *
         * @param offsetLeft 孩子左边位置的偏移量
         * @param offsetTop  孩子顶部位置的偏移量
         */
        public void layout(int offsetLeft, int offsetTop) {
            int currentLeft = offsetLeft;
            int size = viewsLine.size();
            float extra = 0;
            float widthAvg = 0;
            if (usedWidth < maxWidth) {//判断已经使用的宽度是否小于最大的宽度
                extra = maxWidth - usedWidth; //计算出剩余的宽度
                widthAvg = extra / size; //将剩余宽度平均分配到一行的所有的view
            }
            for (int i = 0; i < size; i++) {
                View view = viewsLine.get(i);
                int height = view.getMeasuredHeight();
                int width = view.getMeasuredWidth();
                //判断是否有富余
                if (widthAvg != 0) {
                    //改变宽度
                    int newWidth = (int) (width + widthAvg + 0.5f);
                    int widthMeasureSpec = MeasureSpec.makeMeasureSpec(newWidth, MeasureSpec.EXACTLY);
                    int heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
                    //重新的测量孩子
                    view.measure(widthMeasureSpec, heightMeasureSpec);
                    width = view.getMeasuredWidth();
                    height = view.getMeasuredHeight();
                }
                //布局
                int left = currentLeft;
                //为了使View能够在竖直方向摆在一行的中间,计算top
                int top = (int) (offsetTop + (this.height - height) / 2 + 0.5f);
                int right = left + width;
                int bottom = top + height;
                //布局一个view
                view.layout(left, top, right, bottom);
                //调整水平方向的偏移量,布局下一个view
                currentLeft += width + horizontalSpace;
            }
        }
    }
}
