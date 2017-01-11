package com.aliyouyouzi.book.utils;

import com.aliyouyouzi.book.model.ChickenContentInfo;
import com.aliyouyouzi.book.model.RecommendContentInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by admin on 2016/12/29.
 */

public class JsoupUtils {

    private static JsoupUtils instance;

    public static JsoupUtils getInstance() {
        if (instance == null) {
            instance = new JsoupUtils();
        }
        return instance;
    }

    private JsoupUtils() {
    }

    public RecommendContentInfo praseRecommendContent(String content) {

        RecommendContentInfo contentInfo = new RecommendContentInfo();
        Document document = Jsoup.parse(content);
        Element body = document.body();

        //class等于article_show的div标签
        String author = body.select("span.post-meta-author > a").first().text();
        String title = body.select("div.entry > p").first().text();
        String contentText = body.select("div.article_text").first().text();
        contentText = contentText.replace(" ", "\n        ");
        //添加数据
        contentInfo.author = author;
        contentInfo.content = contentText;
        contentInfo.title = title;

        return contentInfo;
    }

    /**
     * 查看指定网页下的viewpager图片
     */
    public ArrayList<String> praseChickImg(String content) {

        ArrayList<String> imgList = new ArrayList<>();
        Document document = Jsoup.parse(content);
        Element body = document.body();
        Element imgElement = body.select("div.flexslider").first();
        //class等于article_show的div标签
        Elements imgs = imgElement.select("img[src$=.jpg]");

        for (Element img : imgs) {
            imgList.add(img.attributes().get("src"));
        }
        return imgList;
    }

    /**
     * 查看指定鸡汤网页下的内容和图片
     */
    public ArrayList<ChickenContentInfo> praseChickContent(String content) {

        ArrayList<ChickenContentInfo> contentlist = new ArrayList<>();
        Document document = Jsoup.parse(content);
        Element body = document.body();
        Element mainElement = body.select("div.main").first();
        //查看每个标签
        Elements contentElement = mainElement.select("div.pic_text1");

        for (Element contents : contentElement) {
            ChickenContentInfo info = new ChickenContentInfo();
            if (contents.select("img[src$=.jpg]").size() == 0) {
                continue;
            }
            info.url = "http://www.59xihuan.cn" + contents.select("img[src  $=.jpg]").get(0)
                    .attributes().get("bigImageSrc");
            info.text = contents.text();
            contentlist.add(info);
        }
        return contentlist;
    }
}
