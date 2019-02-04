package ngyb.googleplay.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import ngyb.googleplay.R;
import ngyb.googleplay.bean.AppDetailBean;
import ngyb.googleplay.network.DownloadInfo;
import ngyb.googleplay.network.DownloadManager;
import ngyb.googleplay.network.UpdateDownloadInfoListener;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/2 22:35
 */
public class DownloadButton extends AppCompatButton implements UpdateDownloadInfoListener {
    private Paint paint;
    private int progress;
    private boolean enableProgress = true;//开关  是否画进度
    private Drawable drawable = new ColorDrawable(Color.BLUE);//可绘制

    public DownloadButton(Context context) {
        this(context, null);
    }

    public DownloadButton(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public DownloadButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

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

    public void setProgress(int progress) {
        this.progress = progress;
        String progressString = String.format(getContext().getString(R.string.download_progress), progress);
        setText(progressString);
    }


    @Override
    public void OnUpdate(final DownloadInfo downloadInfo) {
        //在主线程更新进度
        post(new Runnable() {
            @Override
            public void run() {
//                setProgress(downloadInfo.progress);
                update(downloadInfo);
            }
        });
    }

    /**
     * @param data 同步apk下载状态
     */
    public void syncState(AppDetailBean data) {
        DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(getContext(), data.getPackageName(), data.getSize(), data.getDownloadUrl());
        //设置监听器,监听DownloadInfo的更新
        downloadInfo.listener = this;
        //更加DownloadInfo信息更新ui
        update(downloadInfo);
    }

    private void update(DownloadInfo downloadInfo) {
        switch (downloadInfo.downloadStatus) {
            case DownloadManager.STATE_INSTALLED:
                setText("打开");
                break;
            case DownloadManager.STATE_DOWNLOADED:
                setText("安装");
                //清除进度
                clearProgress();
                break;
            case DownloadManager.STATE_UN_DOWNLOAD:
                setText("下载");
                break;
            case DownloadManager.STATE_DOWNLOADING:
                enableProgress = true;//打开绘制进度的开关
                setProgress(downloadInfo.progress);
                break;
            case DownloadManager.STATE_PAUSE:
                setText("继续");
                break;
            case DownloadManager.STATE_WAITING:
                setText("等待");
                break;
            case DownloadManager.STATE_FAILED:
                setText("重试");
                //清除进度
                clearProgress();
                break;
        }
    }

    private void clearProgress() {
        enableProgress = false;//关闭开关,不画进度
        invalidate();//触发重新绘制
    }
}
