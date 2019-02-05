package ngyb.googleplay.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ngyb.googleplay.R;
import ngyb.googleplay.bean.AppItemBean;
import ngyb.googleplay.network.DownloadInfo;
import ngyb.googleplay.network.DownloadManager;
import ngyb.googleplay.network.UpdateDownloadInfoListener;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/2 22:25
 */
public class CircleDownloadView extends FrameLayout implements UpdateDownloadInfoListener {
    @BindView(R.id.download_icon)
    ImageView downloadIcon;
    @BindView(R.id.download_text)
    TextView downloadText;
    private RectF rectF;
    private Paint paint;
    private boolean enableProgress = true;//开始绘制进度
    private int progress;//0---100
    private AppItemBean appItemBean;
    private DownloadInfo downloadInfo;

    public CircleDownloadView(@NonNull Context context) {
        this(context, null);
    }

    public CircleDownloadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CircleDownloadView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_circle_download, this);
        ButterKnife.bind(this, this);
        rectF = new RectF();
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);//画弧线  设置边框的样式
        //ViewGroup默认不会调用自己的onDraw方法来绘制自己,只会实现dispatchDraw方法绘制孩子
        //布局设置背景,2setWillNotDraw(false)
        setWillNotDraw(false);
    }

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

    public void setProgress(int progress) {
        this.progress = progress;
        //更新文本
        String progressString = progress + "%";
        downloadText.setText(progressString);
        //触发onDraw重新调用
        invalidate();
    }

    @Override
    public void OnUpdate(final DownloadInfo downloadInfo) {
        post(new Runnable() {
            @Override
            public void run() {
                update(downloadInfo);
            }
        });
    }

    @OnClick(R.id.download_icon)
    public void onViewClicked() {
        DownloadManager.getInstance().handleClick(getContext(), appItemBean.getPackageName());
    }

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

    private void clearProgress() {
        enableProgress = false;
        invalidate();
    }
}
