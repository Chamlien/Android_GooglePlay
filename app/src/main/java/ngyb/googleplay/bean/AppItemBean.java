package ngyb.googleplay.bean;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/4 21:23
 */
public class AppItemBean {

    /**
     * id : 1580615
     * name : 人人
     * packageName : com.renren.mobile.android
     * iconUrl : app/com.renren.mobile.android/icon.jpg
     * stars : 2
     * size : 21803987
     * downloadUrl : app/com.renren.mobile.android/com.renren.mobile.android.apk
     * des : 2005-2014你的校园一直在这儿。中国最大的实名制SNS网络平台，大学生
     */

    private int id;
    private String name;
    private String packageName;
    private String iconUrl;
    private float stars;
    private int size;
    private String downloadUrl;
    private String des;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
