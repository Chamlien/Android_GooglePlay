package ngyb.googleplay.network;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/3 22:33
 */
public interface UpdateDownloadInfoListener {
    void OnUpdate(DownloadInfo downloadInfo);//具有根据downloadInfo来更新的能力
}
