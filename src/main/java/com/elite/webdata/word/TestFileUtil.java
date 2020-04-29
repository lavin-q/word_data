package com.elite.webdata.word;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TestFileUtil {

    /**
     *
     * @param filePath 文件路径
     * @param content  内容
     * @param encoding 编码格式
     */
    public static void outputFile(String filePath, String content, String encoding) {

        try{
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }else{
                file.delete();
                file.createNewFile();
            }
            //打开文件
            FileOutputStream fos = new FileOutputStream(file);
            //设置编码集
            OutputStreamWriter osw = new OutputStreamWriter(fos,encoding);
            //将字符输出流包装为字符缓冲输出流
            BufferedWriter out = new BufferedWriter(osw);
            out.write(content);
            out.flush();
            out.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 读取文件
     * @param filePath
     * @param encoding
     * @return
     */
    public static List<String> readFile(String filePath, String encoding){

        List<String> resultList = new ArrayList<String>();

        try {
            File file = new File(filePath);

            String cread;
            StringBuffer content = new StringBuffer();

            InputStreamReader r = new InputStreamReader(new FileInputStream(file), encoding);
            BufferedReader   in = new BufferedReader(r);
            while ((cread = in.readLine()) != null) {
                resultList.add(cread);
            }

            return resultList;
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     *
     * @param filePath 文件路径
     * @param encoding 编码
     * @return 文件内容(String)
     */
    public static String readTxt(String filePath, String encoding){
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            String cread;
            StringBuffer content = new StringBuffer();

            InputStreamReader r = new InputStreamReader(new FileInputStream(file), encoding);
            BufferedReader   in = new BufferedReader(r);
            while ((cread = in.readLine()) != null) {
                content.append(cread);
            }

            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

}
