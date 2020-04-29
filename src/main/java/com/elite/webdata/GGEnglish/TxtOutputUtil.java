package com.elite.webdata.GGEnglish;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

public class TxtOutputUtil {
	
	public static void output(String msgName,String json) {
		try{
			File file = new File(msgName);
            if (!file.exists()) {
            	file.getParentFile().mkdirs();
            	file.createNewFile();
            }
		    BufferedWriter out = new BufferedWriter(new FileWriter(file));  
		    out.write(json);
		    out.flush(); 
		    out.close();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void outputSrt(String fileName,List<Map<String,Object>> senList) {
		try{
			File file = new File(fileName);
            if (!file.exists()) {
            	file.getParentFile().mkdirs();
            	file.createNewFile();
            }else{
            	file.delete();
            	file.createNewFile();
            }

            //FileWriter  out = new FileWriter (file);
            //BufferedWriter bw = new BufferedWriter(out); 
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");      
            BufferedWriter bw = new BufferedWriter(write);
            
            for (Map<String, Object> map : senList) {
            	bw.write(String.valueOf(map.get("linenum"))); 
                bw.newLine();
                bw.write(String.valueOf(map.get("time"))); 
                bw.newLine(); 
                bw.write(String.valueOf(map.get("etitle"))); 
                bw.newLine();
                if(map.get("ctitle") != null){
                	bw.write(String.valueOf(map.get("ctitle"))); 
                    bw.newLine();
                    bw.write("");
                	bw.newLine();
                }else{
                	bw.write("");
                	bw.newLine(); 
                }
			}
                 
            bw.flush();
            bw.close();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
