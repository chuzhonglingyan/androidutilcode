package com.yutian.common.net.baserx;

/**
 * @explain 服务器请求异常
 *
 * @author guangleilei
 *
 * @time 2017/2/21 9:23.
 */

public class ServerException extends Exception{

    private int code;

    public int getCode() {
        return code;
    }

    public ServerException(String msg){
        super(msg);
    }

    public ServerException(String msg,int code){
        super(msg);
        this.code=code;
    }


}
