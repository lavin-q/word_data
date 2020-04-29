package com.elite.webdata.GGEnglish;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Map;

public class GGEnglish {
	
	public static String PATH = "E:/temp/ggEnglish/";
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		JSONObject obj1 = new JSONObject();
		obj1.put("clientid", "001de56b809a57cc6adb6ed5d0e665db");
        obj1.put("version_name", "3.9.0217");
        obj1.put("terminaltype", "2");
        obj1.put("token", "c148978af7b5c7ea65b762ed57e2f956");
        obj1.put("userid", 543847);
        obj1.put("classlevel", "1");
        obj1.put("version_code", "56");
        obj1.put("deviceid", "20366167923050200:90:ea:5d:e6:52com.easyen");
        obj1.put("channelid", "23");
        String URL = "http://182.92.214.9:8089/getCourseHomepage_v2";
        
        String result = HttpClientUtil.post(obj1,URL);
		
		Map maps = (Map) JSON.parse(result);
        System.out.println(maps.get("data"));
        String data = String.valueOf(maps.get("data"));
        JSONArray dataJson = JSONArray.fromObject(data);
        for(int i = 0; i < dataJson.size(); i++){
	       	JSONObject jsms = dataJson.getJSONObject(i);
	       	String path = PATH + jsms.get("name");
	       	FileUtils.downloadFile(path+"/coverpath.png",String.valueOf(jsms.get("coverpath")));
	       	FileUtils.downloadFile(path+"/coverpath2.png",String.valueOf(jsms.get("coverpath2")));
	       	TxtOutputUtil.output(path+"/home.txt", jsms.toString());
	       	System.out.println("name is -->  "+jsms.get("name")) ;
	       	
	       	JSONArray booksJson = JSONArray.fromObject(jsms.get("list").toString());
	       	for(int j = 0; j < booksJson.size(); j++){
	       		JSONObject books = booksJson.getJSONObject(j);
	       		String booksPath = PATH + jsms.get("name") +"/"+ books.get("title");
	       		FileUtils.downloadFile(booksPath+"/coverpath.png",String.valueOf(books.get("coverpath")));
	       		FileUtils.downloadFile(booksPath+"/coverpath2.png",String.valueOf(books.get("coverpath2")));
	       		FileUtils.downloadFile(booksPath+"/coverpath3.png",String.valueOf(books.get("coverpath3")));
	       		FileUtils.downloadFile(booksPath+"/coverpath4.png",String.valueOf(books.get("coverpath4")));
	       		TxtOutputUtil.output(booksPath+"/books.txt", books.toString());
	       		
	       		System.out.println("name is -->  "+jsms.get("name")+":"+books.get("title")) ;
	       		
	       		JSONObject obj2 = new JSONObject();
	    		obj2.put("clientid", "001de56b809a57cc6adb6ed5d0e665db");
	            obj2.put("version_name", "3.9.0217");
	            obj2.put("terminaltype", "2");
	            obj2.put("token", "c148978af7b5c7ea65b762ed57e2f956");
	            obj2.put("userid", 543847);
	            obj2.put("sortid", books.get("sortid"));
	            obj2.put("version_code", "56");
	            obj2.put("deviceid", "20366167923050200:90:ea:5d:e6:52com.easyen");
	            obj2.put("channelid", "23");
	            String booksURL = "http://182.92.214.9:8089/getSceneListBySortid_v3";
	            
	            String booksResult = HttpClientUtil.post(obj2,booksURL);
	            Map booksMaps = (Map)JSON.parse(booksResult);
	            System.out.println(booksMaps.get("scenelist"));
	            String booksData = String.valueOf(booksMaps.get("scenelist"));
	            JSONArray bookJson = JSONArray.fromObject(booksData);
	            for(int z = 0; z < bookJson.size(); z++){
	            	JSONObject lesson = bookJson.getJSONObject(z);
	            	String lessonPath = PATH + jsms.get("name") +"/"+ books.get("title")+"/"+ (z+1)+"_"+lesson.get("sceneid");
	            	TxtOutputUtil.output(lessonPath+"/message.txt", lesson.toString());
	            	FileUtils.downloadFile(lessonPath+"/videourl.mp4",String.valueOf(lesson.get("videourl")));
	            	FileUtils.downloadFile(lessonPath+"/coverpath.jpg",String.valueOf(lesson.get("coverpath")));
	            	FileUtils.downloadFile(lessonPath+"/audio.mp3",String.valueOf(lesson.get("exread_audiourl")));
	            	
	            	JSONObject obj3 = new JSONObject();
		    		obj3.put("clientid", "001de56b809a57cc6adb6ed5d0e665db");
		            obj3.put("version_name", "3.9.0217");
		            obj3.put("terminaltype", "2");
		            obj3.put("token", "c148978af7b5c7ea65b762ed57e2f956");
		            obj3.put("userid", 543847);
		            obj3.put("sceneid", lesson.get("sceneid"));
		            obj3.put("version_code", "56");
		            obj3.put("deviceid", "20366167923050200:90:ea:5d:e6:52com.easyen");
		            obj3.put("channelid", "23");
		            String bookURL = "http://182.92.214.9:8089/getStoryInfo_v3";
		            
		            String bookResult = HttpClientUtil.post(obj3,bookURL);
		            TxtOutputUtil.output(lessonPath+"/book.txt", bookResult.toString());
	            	
	            }
	       	}
        }
	}
}
