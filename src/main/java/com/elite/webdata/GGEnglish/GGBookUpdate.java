package com.elite.webdata.GGEnglish;

import com.elite.webdata.util.DataSourceLocalFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GGBookUpdate {
	
	public static String PATH = "E:/GGEnglish/srt/";
	
	public static void main(String[] args) throws IOException {
		Connection con = DataSourceLocalFactory.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();

			List<Map<String,Object>> bookList = getBook(con,stmt,rs);
			for (Map<String, Object> bookMap : bookList) {
				Integer bookID = Integer.parseInt(String.valueOf(bookMap.get("bookID")));
				String video = String.valueOf(bookMap.get("video"));
				
				String srtPath = PATH + video.substring(0,video.lastIndexOf("/")+1);
				String fileName = srtPath + "videourl.srt";
				System.out.println(fileName);
				
				List<Map<String,Object>> senList = getBookSentence(con, stmt, rs, bookID);
				TxtOutputUtil.outputSrt(fileName, senList);
				
				/*Integer SenWordNum = 0;
				for (Map<String, Object> map : senList) {
					
					Integer wordNum = 0;
					if(map.get("etitle") != null){
						wordNum =  getWordNum(String.valueOf(map.get("etitle")));
						SenWordNum += wordNum;
						updateBookSentence(con, stmt, wordNum, Integer.parseInt(String.valueOf(map.get("bookSentenceID"))));
					}
				}*/
				
				//updateBook(con, stmt, SenWordNum, bookID);
				
				System.out.println("update bookID:  " + bookID);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}finally {
			try {
			if(rs!=null){
				rs.close();
			}}catch (Exception e) {
				
			}
			try {
				if(stmt!=null){
					stmt.close();
				}}catch (Exception e) {
					
				}
			
			try {
				if(con!=null){
					con.close();
				}}catch (Exception e) {
					
				}
		}
	}
	
	
	private static List<Map<String,Object>> getBook(Connection con,Statement stmt,ResultSet rs) throws SQLException{
		
		List<Map<String,Object>> bookList = new ArrayList<Map<String,Object>>();
		
		String sql = "SELECT * FROM gg_book";
		rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
 			Map<String,Object> book = new HashMap<String, Object>();
 			String video = rs.getString("video");
 			int bookID = Integer.parseInt(rs.getString("bookID"));
 			book.put("bookID", bookID);
 			book.put("video", video);
 			bookList.add(book);
		}
		
		return bookList;
	}
	
	private static void updateBook(Connection con,Statement stmt,Integer wordNum,Integer bookID) throws SQLException{
		
		String sql = "UPDATE gg_book SET wordNum = "+wordNum+" WHERE bookID = " + bookID;
		stmt.executeUpdate(sql);
	}
	
	private static void updateBookSentence(Connection con,Statement stmt,Integer wordNum,Integer bookSentenceID) throws SQLException{
		
		String sql = "UPDATE gg_book_sentence SET wordNum = "+wordNum+" WHERE bookSentenceID = " + bookSentenceID;
		stmt.executeUpdate(sql);
	}
	
	private static List<Map<String,Object>> getBookSentence(Connection con,Statement stmt,ResultSet rs,Integer bookID) throws SQLException{
		
		List<Map<String,Object>> SenList = new ArrayList<Map<String,Object>>();
		
		String sql = "SELECT * FROM gg_book_sentence WHERE bookID =" + bookID +" ORDER BY linenum";
		rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
 			Map<String,Object> sen = new HashMap<String, Object>();
 			String etitle = rs.getString("etitle");
 			String ctitle = rs.getString("ctitle");
 			String linenum = rs.getString("linenum");
 			String starttime = rs.getString("starttime");
 			String endtime = rs.getString("endtime");
 			String bookSentenceID = rs.getString("bookSentenceID");
 			
 			sen.put("linenum", linenum);
 			sen.put("etitle", etitle);
 			sen.put("ctitle", ctitle);
 			sen.put("time", starttime + " --> " + endtime);
 			sen.put("bookSentenceID", bookSentenceID);
 			
 			SenList.add(sen);
		}
		
		return SenList;
	}
	
	
	private static int getWordNum(String strSen){
		if(strSen.isEmpty()){
			return 0;
		}
		strSen = strSen.replaceAll("\"|\\.|!|\\?|-$|:|;|,", "");
		strSen = strSen.toLowerCase();
		String stx[] = strSen.split(" ");
		Integer index = 0;
		if(stx!=null && stx.length>0){
			for(String word:stx){
				word = word.replace("“", "");
				word = word.replace("’", "'");
				word = word.replace("”", "");
				word = word.replace("…", "");
				if(word.endsWith("'")){
					word = word.replaceAll("'", "");
				}
				if(word.startsWith("'")){
					word = word.replaceFirst("'", "");
				}
				
				if(word.equals("-")){
					continue;
				}
				
				if(word.equals("")){
					continue;
				}
				index ++;
			}
		}
		return index;
	}
}
