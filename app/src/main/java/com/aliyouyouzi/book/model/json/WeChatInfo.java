package com.aliyouyouzi.book.model.json;

import java.util.List;

/**
 * Created by admin on 2017/1/11.
 */

public class WeChatInfo {

    public String reason;
    public ResultBean result;
    public int error_code;

    public static class ResultBean {

        public int totalPage;
        public int ps;
        public int pno;
        public List<ListBean> list;

        public static class ListBean {

            public String id;
            public String title;
            public String source;
            public String firstImg;
            public String mark;
            public String url;
        }
    }
}
