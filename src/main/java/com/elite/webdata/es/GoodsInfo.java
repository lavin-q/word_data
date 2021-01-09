package com.elite.webdata.es;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * @Description : 商品文档 //描述
 * @Author : qhm  //作者
 * @Date: 2020-12-17 16:58  //时间
 */

//indexName索引名称 可以理解为数据库名 必须为小写 不然会报org.elasticsearch.indices.InvalidIndexNameException异常
//type类型 可以理解为表名
@Document(indexName = "store", type = "goods")
@Getter
@Setter
@AllArgsConstructor
public class GoodsInfo implements Serializable {
    private Long id;
    private String name;
    private String description;

}
