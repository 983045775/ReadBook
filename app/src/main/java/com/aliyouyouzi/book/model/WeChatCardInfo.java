package com.aliyouyouzi.book.model;


public class WeChatCardInfo {

    private String desc;
    private String Url;

    public WeChatCardInfo(String Url, String desc) {
        this.Url = Url;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getUrl() {
        return Url;
    }
}
