package com.mredrock.cyxbs.model;


<<<<<<< HEAD
import java.io.Serializable;

import static com.umeng.analytics.social.e.t;

=======
>>>>>>> 8b8b724df6cd729704da98d6dae2581b74af048c
/**
 * Created by zhengyuxuan on 2016/10/11.
 */

public class Affair extends Course implements Serializable{

    public static final int TYPE = 2;

    public String uid;
    //提醒时间，正数
    public int time;

    public Affair() {
        courseType = Affair.TYPE;
    }

}
