package ngyb.googleplay.network;


/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/3 21:05
 * 抽取某个APK下载过程中相关的数据,下载进度 下载状态.....
 */
public class DownloadInfo {
    public int progress; //0---100
    public int downloadStatus;//下载状态
    public String packageName;//包名
    public long apkSize;//apk大小
    public long downloadSize;//已经下载了多少
    public String filePath;//apk文件路径
    public String downloadUrl;//apk下载的url
    public UpdateDownloadInfoListener listener; //监听器
    public DownloadManager.DownloadTask downloadTask; //下载任务
}
