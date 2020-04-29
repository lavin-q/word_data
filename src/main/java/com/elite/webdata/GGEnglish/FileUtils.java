package com.elite.webdata.GGEnglish;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FileUtils {
	
	/**
	 * down load File
	 * @param imageName url 
	 * @param urlList
	 */
	public static void downloadFile(String imageName,String urlList) {
        URL url = null;
        
        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            File file = new File(imageName);
            if (!file.exists()) {
            	file.getParentFile().mkdirs();
            	file.createNewFile();
            	
            	FileOutputStream fileOutputStream = new FileOutputStream(file);
                ByteArrayOutputStream output = new ByteArrayOutputStream();

                byte[] buffer = new byte[102400];
                int length;

                while ((length = dataInputStream.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
                @SuppressWarnings("unused")
    			byte[] context=output.toByteArray();
                fileOutputStream.write(output.toByteArray());
                dataInputStream.close();
                fileOutputStream.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static void main(String[] args) {
		String imageName = "E:/data/ceshi/ceshi/ceshi/ceshi/0a6.mp4";
		String urlList = "http://cdnstory.glorymobi.com/video7b32f76c8d4c41a7bf6740bcf954e0a6.mp4";
		
		downloadFile(imageName, urlList);
	}
}
