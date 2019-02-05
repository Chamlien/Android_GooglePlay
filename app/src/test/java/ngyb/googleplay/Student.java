package ngyb.googleplay;

import java.util.Observable;
import java.util.Observer;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/2/5 22:22
 */
public class Student  implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("收到:"+arg);
    }
}
