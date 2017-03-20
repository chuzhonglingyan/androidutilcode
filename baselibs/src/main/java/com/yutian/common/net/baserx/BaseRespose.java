package com.yutian.common.net.baserx;

import java.io.Serializable;

/**
 * <pre>
 *     author : guangleilei
 *     e-mail : chuzhonglingyan@163.com
 *     time   : 2017/3/9 15:06
 *     desc   : 封装后端业务数据
 *     version: 1.0
 * </pre>
 */

public class BaseRespose<T> implements Serializable {

    public int code;
    public String info;
    public T data;

    public boolean success() {
        return 99 == code;
    }

    @Override
    public String toString() {
        return "BaseRespose{" +
                "code=" + code +
                ", info='" + info + '\'' +
                ", data=" + data +
                '}';
    }
}
