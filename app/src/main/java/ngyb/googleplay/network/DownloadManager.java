package ngyb.googleplay.network;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/3 21:09
 * 下载管理器,管理所有的下载相关的业务逻辑
 */
public class DownloadManager {
    public static final int STATE_UN_DOWNLOAD = 0; //未下载
    public static final int STATE_DOWNLOADING = 1; //下载中
    public static final int STATE_PAUSE = 2; //暂停下载
    public static final int STATE_WAITING = 3; //等待下载
    public static final int STATE_FAILED = 4; //下载失败
    public static final int STATE_DOWNLOADED = 5; //下载完成
    public static final int STATE_INSTALLED = 6;//已安装
    /**
     * Apk下载存放的路径
     */
    public static final String DOWNLOAD_DIR = Environment.getExternalStorageDirectory() + "/Android/data/ngyb.googleplay/apk/";
    /**
     * 下载信息的缓存
     */
    HashMap<String, DownloadInfo> downloadInfoHashMap = new HashMap<>();//内存缓存SparseArray LruCache
    private static DownloadManager downloadManager;
    private ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);//创建固定大小的线程池,2个线程,返回的为ThreadPoolExecutor

    private DownloadManager() {
    }

    public static DownloadManager getInstance() {
        if (downloadManager == null) {
            synchronized (DownloadManager.class) {
                if (downloadManager == null) {
                    downloadManager = new DownloadManager();
                }
            }
        }
        return downloadManager;
    }

    public void createDownloadDir() {
        File file = new File(DOWNLOAD_DIR);
        if (!file.exists()) {
            //如果文件夹的路径不存在,则创建该文件夹
            file.mkdirs();//如果父目录不存在,则还会创建父目录
        }
    }

    public DownloadInfo getDownloadInfo(Context context, String packageName, int apkSize, String downloadUrl) {
        //先查缓存,如果缓存里面存在,则直接返回
        if (downloadInfoHashMap.get(packageName) != null) {
            return downloadInfoHashMap.get(packageName);
        }

        DownloadInfo downloadInfo = new DownloadInfo();
        //添加下载相关的信息
        downloadInfo.apkSize = apkSize;
        downloadInfo.packageName = packageName;
        //保存下载的url
        downloadInfo.downloadUrl = downloadUrl;
        //拿到对应包名的app里面的activity信息
        if (isInstalled(context, packageName)) {
            //判断是否已经安装
            downloadInfo.downloadStatus = STATE_INSTALLED;
        } else if (isDownloaded(downloadInfo)) {
            downloadInfo.downloadStatus = STATE_DOWNLOADED;
        } else {
            downloadInfo.downloadStatus = STATE_UN_DOWNLOAD;
        }
        //缓存downloadinfo
        downloadInfoHashMap.put(packageName, downloadInfo);
        return downloadInfo;
    }

    /**
     * @param downloadInfo
     * @return 是否已经下载完成
     */
    private boolean isDownloaded(DownloadInfo downloadInfo) {
        //获取下载目录中的apk文件的大小,比较是否很apk应该有的大小一致,如果一致,则下载完成
        //创建一个apk对应文件
        String filePath = DOWNLOAD_DIR + downloadInfo.packageName + ".apk";//apk对应的路径
        downloadInfo.filePath = filePath;//更新apk 的文件路径
        File apk = new File(filePath);
        if (apk.exists()) {
            //保存已经下载的大小,为了断电续传
            downloadInfo.downloadSize = apk.length();
            //判断大小,是否一致
            if (apk.length() == downloadInfo.apkSize) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param context
     * @param packageName
     * @return 判断是否已经安装apk
     */
    private boolean isInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void handleClick(Context context, String packageName) {
        //通过包名获取下载的状态,根据状态来处理不同逻辑,实际上从缓存里面获取
//        DownloadInfo downloadInfo = getDownloadInfo(context,packageName,data.getSize(),data.getDownloadUrl());
        DownloadInfo downloadInfo = downloadInfoHashMap.get(packageName);
        switch (downloadInfo.downloadStatus) {
            case STATE_INSTALLED:
                //打开应用
                openApp(context, packageName);
                break;
            case STATE_DOWNLOADED:
                //安装应用
                installApk(context, packageName);
                break;
            case STATE_UN_DOWNLOAD:
                downloadApk(downloadInfo);
                break;
            case STATE_DOWNLOADING:
//                如果是正在下载,那就暂停下载
                pauseDownload(downloadInfo);
                break;
            case STATE_PAUSE:
                //继续下载
                downloadApk(downloadInfo);
                break;
            case STATE_FAILED:
                //重试下载,即断电续传
                downloadApk(downloadInfo);
                break;
            case STATE_WAITING:
                //取消下载
                cancelDownload(downloadInfo);
                break;
        }
    }

    private void openApp(Context context, String packageName) {
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(launchIntentForPackage);
    }

    private void installApk(Context context, String packageName) {
        //7.0以前,跳转系统安装界面,让系统安装程序获取apk文件去安装
        //FileUriExposedException
        String filePath = DOWNLOAD_DIR + packageName + ".apk";//apk对应的路径
        File file = new File(filePath);
        Uri uri = null;//apk文件对应
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (file.exists()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //大于等于版本7.0
                uri = FileProvider.getUriForFile(context, "cqq.googleplay.fileprovider", file);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//暂时授权获取apk文件
            } else {
                uri = Uri.fromFile(file);
            }
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            context.startActivity(intent);
        }
    }

    private void downloadApk(DownloadInfo downloadInfo) {
        //开始下载是,状态更新等待状态
        downloadInfo.downloadStatus = STATE_WAITING;
        //通知UI   更新状态
//        downloadInfo.listener.onUpdate(downloadInfo);
        notifyDownloadInfoChange(downloadInfo);
//        new Thread(new DownloadTask(downloadInfo)).start();
        DownloadTask downloadTask = new DownloadTask(downloadInfo);
        //将下载任务保存在下载信息里面
        downloadInfo.downloadTask = downloadTask;
        threadPoolExecutor.execute(downloadTask);
    }

    private void notifyDownloadInfoChange(DownloadInfo downloadInfo) {
        if (downloadInfo != null) {
            downloadInfo.listener.OnUpdate(downloadInfo);
        }
    }

    private void pauseDownload(DownloadInfo downloadInfo) {
        //将状态改为暂停状态
        downloadInfo.downloadStatus = STATE_PAUSE;
        //通知更新ui更新状态
//        downloadInfo.listener.onUpdate(downloadInfo);
        notifyDownloadInfoChange(downloadInfo);
    }

    private void cancelDownload(DownloadInfo downloadInfo) {
        //从任务队列移除任务
        threadPoolExecutor.remove(downloadInfo.downloadTask);
        //通知更新UI
        downloadInfo.downloadStatus = STATE_UN_DOWNLOAD;
        notifyDownloadInfoChange(downloadInfo);
    }

    public class DownloadTask implements Runnable {
        private DownloadInfo downloadInfo;

        public DownloadTask(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
        }

        @Override
        public void run() {
            //开始下载apk
            String url = "http://47.105.71.243:8080/GooglePlayServer/download?name=" + downloadInfo.downloadUrl + "&range=" + downloadInfo.downloadSize;
//            String url = "http://192.168.0.103:8080/GooglePlayServer/download?name="+ downloadInfo.downloadUrl+"&range="+downloadInfo.downloadSize;
            Request request = new Request.Builder().get().url(url).build();
            OkHttpClient okHttpClient = new OkHttpClient();
            InputStream inputStream = null;
            FileOutputStream fileOutputStream = null;
            try {
                Response res = okHttpClient.newCall(request).execute();
                if (res.isSuccessful()) {
                    File file = new File(downloadInfo.filePath);
                    if (!file.exists()) {
                        file.createNewFile();//不存在文件则创建
                    }
                    inputStream = res.body().byteStream();//输入流
                    //输出流
                    fileOutputStream = new FileOutputStream(file, true);//true写到文件末尾,处理断电续传
                    //定义Buffer
                    byte[] buffer = new byte[1024];//每次读1kb
                    int len = -1;//读取字节数
                    while ((len = inputStream.read(buffer)) != -1) {//从输入入流读取1024字节,如果读取结束read返回-1
                        //判断是否为暂停下载状态,如果是则跳出循环
                        if (downloadInfo.downloadStatus == STATE_PAUSE) {
                            return;
                        }
                        //将buffer字节写入输出流
                        fileOutputStream.write(buffer, 0, len);
                        downloadInfo.downloadSize += len;//计算已经下载字节大小
                        //计算进度0--100
                        int progress = (int) (downloadInfo.downloadSize * 1.0f / downloadInfo.apkSize * 100);
                        //进度变大才进行刷新绘制,优化
                        if (progress > downloadInfo.progress) {
                            downloadInfo.progress = progress;
                            downloadInfo.downloadStatus = STATE_DOWNLOADING;//更新状态,正在下载的状态
//                            downloadInfo.listener.onUpdate(downloadInfo);
                            notifyDownloadInfoChange(downloadInfo);
                        }
                        //断电续传,如果进入达到100,那么不要在读数据,直接跳出循环,否则出现超时
                        if (progress == 100) {
                            break;
                        }
                    }
                    //更新状态为已经下载完成
                    downloadInfo.downloadStatus = STATE_DOWNLOADED;
//                    downloadInfo.listener.onUpdate(downloadInfo);
                    notifyDownloadInfoChange(downloadInfo);
                } else {
                    //失败
                    downloadInfo.downloadStatus = STATE_FAILED;
//                    downloadInfo.listener.onUpdate(downloadInfo);
                    notifyDownloadInfoChange(downloadInfo);
                }
            } catch (IOException e) {
                e.printStackTrace();
                //失败
                downloadInfo.downloadStatus = STATE_FAILED;
//                    downloadInfo.listener.onUpdate(downloadInfo);
                notifyDownloadInfoChange(downloadInfo);
            } finally {
                //关闭输入输出流
                closeStream(inputStream);
                closeStream(fileOutputStream);
            }
        }
    }

    private void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
