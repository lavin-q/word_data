package com.elite.webdata.data;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;


/**
 * 网页数据抓取
 */
@Slf4j
public class WebDataCrawl {


    //文件相对路径
    private static String path = "achieve3000_SourceCode.txt";

    private static String url = "http://www.achieve3000.com";

    public static void main(String[] args) {
        String webSourceCode = getWebSourceCode(url);
        if(StringUtils.isNotBlank(webSourceCode)){

        }
    }

    /**
     * 获取网页源码
     * @param url 网页地址
     * @return    网页源码
     */
    public static String getWebSourceCode(String url){
        String html="";
        try {
            //获取网页源码
            html = Jsoup.connect(url).get().html();
            //System.out.print(html);
        } catch (Exception e) {
            log.debug("获取网页内容错误:{}",e.getMessage());
        }
        if(StringUtils.isNotBlank(html)){
            return html;
        } else{
            return null;
        }
    }

    /**
     * 数据写入文件
     * @param data 数据内容
     * @param filePath 文件路径(含文件名)
     * @return
     */
    public static Boolean writeDataToFile(Object data ,String filePath){
        return null;
    }

}
