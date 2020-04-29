package com.elite.webdata.GGEnglish;

import com.alibaba.fastjson.JSON;
import com.elite.webdata.util.DataSourceLocalFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class intoStorage {
	
	public static String PATH = "ggEnglish/";
	
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
		
		Map maps = (Map)JSON.parse(result);
        System.out.println(maps.get("data"));
        String data = String.valueOf(maps.get("data"));
        JSONArray dataJson = JSONArray.fromObject(data);
        for(int i = 0; i < dataJson.size(); i++){
        	
	       	JSONObject jsms = dataJson.getJSONObject(i);
	       	System.out.println("name is -->  "+jsms.get("name")) ;
	       	JSONArray booksJson = JSONArray.fromObject(jsms.get("list").toString());
	       	
	       	for(int j = 0; j < booksJson.size(); j++){
	       		JSONObject books = booksJson.getJSONObject(j);
	       		String booksPath = PATH + jsms.get("name") +"/"+ books.get("title");

	       		System.out.println("name is -->  "+jsms.get("name")+":"+books.get("title")) ;
	       		try {
					putSort(books, booksPath, String.valueOf(jsms.get("name")));
				} catch (SQLException e) {
					e.printStackTrace();
				}
	       		
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

	            String booksData = String.valueOf(booksMaps.get("scenelist"));
	            JSONArray bookJson = JSONArray.fromObject(booksData);
	            for(int z = 0; z < bookJson.size(); z++){
	            	JSONObject lesson = bookJson.getJSONObject(z);
	            	String lessonPath = PATH + jsms.get("name") +"/"+ books.get("title")+"/"+ (z+1)+"_"+lesson.get("sceneid");
	            	
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
		            Map bookMap = (Map)JSON.parse(bookResult);
		            
		            String senData = String.valueOf(bookMap.get("subtitlelist"));
		            JSONArray senJson = JSONArray.fromObject(senData);
		            JSONObject bookmsg = JSONObject.fromObject(bookMap.get("storyinfo"));
		            System.out.println("sen size is -->  "+ senJson.size()) ;
		            
		            System.out.println("sceneid is -->  "+ lesson.get("sceneid")) ;
	            	try {
						putBook(bookmsg, lessonPath, Integer.parseInt(String.valueOf(books.get("sortid"))),Integer.parseInt(String.valueOf(senJson.size())));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		            
		            for(int m = 0; m < senJson.size(); m++){
		            	JSONObject sentence = senJson.getJSONObject(m);
		            	try {
							putBookSentence(sentence, Integer.parseInt(String.valueOf(lesson.get("sceneid"))));
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						}
		            }
	            	
	            }
	       	}
        }
	}
	
	
	private static void putSort(JSONObject json,String booksPath,String tags) throws SQLException{
		
		Connection con = DataSourceLocalFactory.getConnection();
		PreparedStatement pstmt;
		
		String sql = "INSERT INTO GG_sort (`sortID`, `title`, `tags`, `content`, `lexileLevel`, `sourceID`, `coverpath1`, `coverpath2`, `coverpath3`, `coverpath4`) VALUES (?,?,?,?,?,?,?,?,?,?)";
		String lexileLevel = getLexile(String.valueOf(json.get("content")));
		
		try {
			
	        pstmt = (PreparedStatement) con.prepareStatement(sql);
	        pstmt.setInt(1, Integer.parseInt(String.valueOf(json.get("sortid"))));
	        pstmt.setString(2, String.valueOf(json.get("title")));
	        pstmt.setString(3, tags);
	        pstmt.setString(4, String.valueOf(json.get("content")));
	        pstmt.setString(5, lexileLevel);
	        pstmt.setInt(6, Integer.parseInt(String.valueOf(json.get("sortid"))));
	        pstmt.setString(7, booksPath+"/coverpath.png");
	        pstmt.setString(8, booksPath+"/coverpath2.png");
	        pstmt.setString(9, booksPath+"/coverpath3.png");
	        pstmt.setString(10, booksPath+"/coverpath4.png");
	        
	        pstmt.executeUpdate();
	        pstmt.close();
	        con.close();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	private static String getLexile(String lexileLevel){
		
		if(lexileLevel != null && !lexileLevel.equals(" ") && lexileLevel.contains("蓝思等级：")){
			String s = lexileLevel.substring(lexileLevel.indexOf("蓝思等级："), lexileLevel.length());
			return s.substring(0, s.indexOf("；"));
		}else{
			return "";
		}
	}
	
	private static void putBook(JSONObject json,String lessonPath,Integer sortid,Integer sentenceNum) throws SQLException{
		
		Connection con = DataSourceLocalFactory.getConnection();
		
		PreparedStatement pstmt;
		String sql = "INSERT INTO GG_book(`bookID`, `sortID`, `sourceID`, `title`, `video_duration`, `medal`, `hotnum`, `audio_duration`, `video`, `audio`, `wordNum`, `sentenceNum`, `coverpath`, `level`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
	        pstmt = (PreparedStatement) con.prepareStatement(sql);
	        pstmt.setInt(1, Integer.parseInt(String.valueOf(json.get("sceneid"))));
	        pstmt.setInt(2, sortid);
	        pstmt.setInt(3, Integer.parseInt(String.valueOf(json.get("sceneid"))));
	        pstmt.setString(4, String.valueOf(json.get("title")));
	        pstmt.setInt(5, Integer.parseInt(String.valueOf(json.get("duration")== null ? "0" : json.get("duration"))));
	        pstmt.setInt(6, Integer.parseInt(String.valueOf(json.get("medal") == null ? "0" : json.get("medal"))));
	        pstmt.setInt(7, Integer.parseInt(String.valueOf(json.get("hotnum")== null ? "0" : json.get("hotnum"))));
	        pstmt.setInt(8, Integer.parseInt(String.valueOf(json.get("audio_duration") == null ? "0" : json.get("audio_duration"))));
	        pstmt.setString(9, lessonPath+"/videourl.mp4");
	        pstmt.setString(10, lessonPath+"/audio.mp3");
	        pstmt.setInt(11, 0);
	        pstmt.setInt(12, sentenceNum);
	        pstmt.setString(13, lessonPath+"/coverpath.jpg");
	        pstmt.setInt(14, Integer.parseInt(String.valueOf(json.get("level") == null ? "0" : json.get("level"))));

	        pstmt.executeUpdate();
	        pstmt.close();
	        con.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	private static void putBookSentence(JSONObject json,Integer sceneid) throws SQLException{
		
		Connection con = DataSourceLocalFactory.getConnection();
		
		PreparedStatement pstmt;
		String sql = "INSERT INTO GG_book_sentence(`bookID`, `etitle`, `ctitle`, `linenum`, `starttime`, `endtime`) VALUES (?,?,?,?,?,?)";
		try {
	        pstmt = (PreparedStatement) con.prepareStatement(sql);
	        pstmt.setInt(1, sceneid);
	        pstmt.setString(2, String.valueOf(json.get("etitle")));
	        pstmt.setString(3, String.valueOf(json.get("ctitle")));
	        pstmt.setInt(4, Integer.parseInt(String.valueOf(json.get("linenum"))));
	        pstmt.setString(5, String.valueOf(json.get("starttime")));
	        pstmt.setString(6, String.valueOf(json.get("endtime")));
	        
	        pstmt.executeUpdate();
	        pstmt.close();
	        con.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}
