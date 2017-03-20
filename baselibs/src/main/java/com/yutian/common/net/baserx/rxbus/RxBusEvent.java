package com.yutian.common.net.baserx.rxbus;

/**
 * @author guangleilei
 * @version 1.0 2017-02-28
 */
public class RxBusEvent {

    private  String id;

    private String content;

    public RxBusEvent(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
