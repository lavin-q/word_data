package com.elite.webdata.es;

import io.searchbox.client.JestClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @Description : 测试连接  //描述
 * @Author : qhm  //作者
 * @Date: 2020-12-18 11:14  //时间
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplication {

    @Autowired
    private JestClient jestClient;

    // 给ES索引（保存）一个文档
    @Test
    public void contextLoad() throws IOException {

    }
}
