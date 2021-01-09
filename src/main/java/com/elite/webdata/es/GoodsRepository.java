package com.elite.webdata.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @Description : 商品仓库  //描述
 * @Author : qhm  //作者
 * @Date: 2020-12-17 17:06  //时间
 */
@Component
public interface GoodsRepository extends ElasticsearchRepository<GoodsInfo, Long> {

}
