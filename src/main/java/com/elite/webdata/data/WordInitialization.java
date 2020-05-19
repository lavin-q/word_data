package com.elite.webdata.data;


import com.elite.webdata.entity.EliteWord;
import com.elite.webdata.service.EliteWordService;
import com.elite.webdata.util.DocUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * 高考3500单词初始化类
 */
//@Controller
public class WordInitialization {

    //@Autowired
    private EliteWordService eliteWordService;
    /**
     * 单词文档处理
     */
   //@PostConstruct
   public void wordDocrocess(){
        String docTextContent = DocUtil.getDocTextContent("D:\\下载\\高考英语3500词汇表-带音标.doc");
        String[] split = docTextContent.split("\r\n");
        List<String> list = Arrays.asList(split);
        List<String> titleList = list.subList(0, 6);
        List<String> wordList = list.subList(6, list.size());
        //遍历
        for (String word : wordList){
            String[] wordArray = word.split("\\[|\\]");
            List<String> strings = Arrays.asList(wordArray);
            if(strings== null || strings.size()!=3){
                continue;
            }
            EliteWord eliteWord = new EliteWord();
            //去除空格字符串中
            String wd = strings.get(0).replaceAll(" +", "");
            //去除字符串中不间断空格(编码为160),转义字符为\u00A0+
            wd = wd.replaceAll("\\u00A0+", "");
            eliteWord.setWord(wd);
            eliteWord.setPhoneticSymbol("["+strings.get(1)+"]");
            eliteWord.setComment(strings.get(2));
            eliteWordService.insert(eliteWord);

        }
        System.out.print(wordList);
    }

}
