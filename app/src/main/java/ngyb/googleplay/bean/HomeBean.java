package ngyb.googleplay.bean;

import java.util.List;

/**
 * 作者：南宫燚滨
 * 描述:
 * 邮箱：nangongyibin@gmail.com
 * 时间: 2018/5/4 21:24
 */
public class HomeBean {

    private List<AppItemBean> list;
    private List<String> picture;

    public List<AppItemBean> getList() {
        return list;
    }

    public void setList(List<AppItemBean> list) {
        this.list = list;
    }

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

}
