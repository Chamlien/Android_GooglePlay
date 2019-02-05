package ngyb.googleplay;

import java.util.Observable;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/2/5 22:22
 */
public class Teacher extends Observable {
    public void publishMessage(){
        setChanged();
        notifyObservers("放假了");
    }
}
