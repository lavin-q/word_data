package com.elite.webdata.util;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
  
/** 
 * Description: 数据库连接池类 
 * @filename DataSourceFactory.java  
 * @date 2013年8月21日 19:47:21 
 * @author Herman.Xiong 
 * @version 1.0 
 * Copyright (c) 2013 Company,Inc. All Rights Reserved. 
 */  
public class DataSourceLocalFactory {  
    //private static Logger log = Logger.getLogger(DataSourceFactory.class);  
    private static BasicDataSource bs = null;  
    public static String driver = "com.mysql.jdbc.Driver",
    		url="jdbc:mysql://localhost:3306/elite?useUnicode=true&characterEncoding=utf-8&noAccessToProcedureBodies=true&useSSL=false&serverTimezone=GMT%2B8",
    		//userName="admin",
    		//password="$TopE#6%@2o1**7ll"; 
    		userName="root",
    		password="root";  
      
    /** 
     * 创建数据源 
     * @return 
     */  
    public static BasicDataSource getDataSource() throws Exception{  
        if(bs==null){  
            ///log.info("数据库连接信息：[driver:"+driver+",url:"+url+",userName:"+userName+",password:"+password+"]");  
            bs = new BasicDataSource();  
            bs.setDriverClassName(driver);  
            bs.setUrl(url);  
            bs.setUsername(userName);  
            bs.setPassword(password);  
            bs.setMaxActive(10);//设置最大并发数  
            bs.setMinIdle(50);//最小空闲连接数  
            bs.setMaxIdle(200);//数据库最大连接数  
            bs.setMaxWait(1000);  
            bs.setMinEvictableIdleTimeMillis(60000);//空闲连接60秒中后释放
            bs.setTimeBetweenEvictionRunsMillis(300000);//5分钟检测一次是否有死掉的线程
            bs.setTestOnBorrow(true);  
        }  
        return bs;  
    }  
      
    /** 
     * 释放数据源 
     */  
    public static void shutDownDataSource() throws Exception{  
        if(bs!=null){  
            bs.close();  
        }  
    }  
      
    /** 
     * 获取数据库连接 
     * @return 
     */  
    public static Connection getConnection(){  
        Connection con=null;  
        try {  
            if(bs!=null){  
                con=bs.getConnection();  
            }else{  
                con=getDataSource().getConnection();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();
        }  
        return con;  
    }  
      
    /** 
     * 关闭连接 
     */  
    public static void closeCon(ResultSet rs,PreparedStatement ps,Connection con){  
        if(rs!=null){  
            try {  
                rs.close();  
            } catch (Exception e) {  
                //log.error("关闭结果集ResultSet异常！"+e.getMessage(), e);  
            }  
        }  
        if(ps!=null){  
            try {  
                ps.close();  
            } catch (Exception e) {  
               // log.error("预编译SQL语句对象PreparedStatement关闭异常！"+e.getMessage(), e);  
            }  
        }  
        if(con!=null){  
            try {  
                con.close();  
            } catch (Exception e) {  
                //log.error("关闭连接对象Connection异常！"+e.getMessage(), e);  
            }  
        }  
    }  
}  

