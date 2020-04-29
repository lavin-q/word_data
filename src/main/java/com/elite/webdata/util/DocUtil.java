package com.elite.webdata.util;

import com.spire.doc.Document;
import com.spire.doc.documents.DocumentObjectType;
import com.spire.doc.fields.DocPicture;
import com.spire.doc.interfaces.ICompositeObject;
import com.spire.doc.interfaces.IDocumentObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Doc 文档操作工具类
 */
@Slf4j
public class DocUtil {
    /**
     * 创建Document
     * @param path 文件路径
     * @return Document
     */
     private static Document creatDocument(String path) {
         Document document = new Document();
         if (StringUtils.isNotBlank(path)) {
             String substring = path.substring(path.indexOf("."), path.length());
             if (".doc".equals(substring)) {
                 try {
                     document.loadFromFile(path);
                 } catch (Exception e) {
                     log.error("文件不存在:{}", e.getMessage());
                 }
                 return document;
             }else{
                 log.error("文件类型错误:{}",path);
                 return null;
             }
         }
         return null;
     }
    /**
     * 获取文本属性
     * @param path 文件路径
     * @return String
     */
    public static String getDocProperties(String path){
        Document document = creatDocument(path);
        if(document != null){
            //System.out.println("标题： " + document.getBuiltinDocumentProperties().getTitle());
            return document.getBuiltinDocumentProperties().getTitle();
        }
        return null;
    }

    /**
     * 获取文本内容
     * @param path 文件路径
     * @return String
     */
    public static String getDocTextContent(String path){
        Document document = creatDocument(path);
        if(document != null){
            return document.getText();
        }
        return null;
    }


    /**
     * 获取Doc文件中图片
     * @param path 文件路径
     * @return String
     */
    public static List getDocImage(String path){
        Document document = creatDocument(path);
        if(document != null){
            Queue<ICompositeObject> nodes = new LinkedList<>();
            nodes.add(document);
            //创建List对象
            List<BufferedImage> images = new ArrayList<>();
            //遍历文档中的子对象
            while (nodes.size() > 0) {
                ICompositeObject node = nodes.poll();
                for (int i = 0; i < node.getChildObjects().getCount(); i++) {
                    IDocumentObject child = node.getChildObjects().get(i);
                    if (child instanceof ICompositeObject) {
                        nodes.add((ICompositeObject) child);

                        //获取图片并添加到List
                        if (child.getDocumentObjectType() == DocumentObjectType.Picture) {
                            DocPicture picture = (DocPicture) child;
                            images.add(picture.getImage());
                        }
                    }
                }
            }
            return images;
        }
        return null;
    }

}
