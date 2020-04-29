package com.elite.webdata.word;

import com.elite.webdata.util.DataSourceLocalFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetWordLastJson {
	
	private static String outpath1 = "E:\\data\\word\\examWord\\";
	
	private static String outpath2 = "E:\\data\\word\\examWordBak\\";

	public static void main(String[] args) {
		
		Connection con = DataSourceLocalFactory.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			List<String> examWord = getExamWord(con, stmt, rs);
			
			for (String string : examWord) {
				if(getCacheWord(string.toLowerCase().trim()) == null){
					//checkJson(WordCleanUtil.procWord(string.toLowerCase().trim()));
				}
			}
			
			List<String> generalWord = getGeneralWord(con, stmt, rs);
			
			for (String string : generalWord) {
				if(getCacheWord(string.toLowerCase().trim()) == null){
					//checkJson(WordCleanUtil.procWord(string.toLowerCase().trim()));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null){
					rs.close();
				}
			}catch (Exception e) {
					
			}
			try {
				if(stmt!=null){
					stmt.close();
				}
			}catch (Exception e) {
					
			}
			try {
				if(con!=null){
					con.close();
				}
			}catch (Exception e) {
					
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void checkJson(String word){
		
		String URL = "http://www.iciba.com/index.php";
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "getWordMean");
		map.put("list", "1%2C3%2C4%2C8%2C9%2C12%2C13%2C15");
		map.put("c", "search");
		map.put("_", "1524099728910");
		map.put("word", word);
		
		System.out.println("开始处理："+ word + ".json");
		String result = get(map, "UTF-8", URL);
		
		if(result != null && result.length() > 0){
			String fileName = "\\"+ word + ".json";
			outputFile(outpath1 + "\\" + word + fileName, result, "UTF-8");
			
			Map<Object, Object> resultMap = JSONObject.fromObject(result);
			String output = analyJson(resultMap);
			outputFile(outpath2 + "\\" + word + fileName, output, "UTF-8");
	    }
	}
	
	@SuppressWarnings("unchecked")
	public static String analyJson(Map<Object, Object> map){
		
		Map<Object, Object> resultMap = new HashMap<Object, Object>();
		
		if(("success").equals(map.get("errmsg").toString())){
			//baseInfo
			Map<Object, Object> baseInfoMap = new HashMap<Object, Object>();
			if(map.containsKey("baesInfo") ){
				JSONObject baseMsg = JSONObject.fromObject(map.get("baesInfo").toString());
				if(baseMsg.containsKey("word_name") ){
					String word_name = baseMsg.get("word_name").toString();
					baseInfoMap.put("word_name", word_name);
					if(baseMsg.containsKey("exchange")){
						Map<Object, Object> exchangeMsg = JSONObject.fromObject(baseMsg.get("exchange"));
						Map<String, String> exchangeMap = new HashMap<String, String>();
						for (Map.Entry<Object, Object> entry : exchangeMsg.entrySet()){
							if(entry.getValue() != null || !("").equals(entry.getValue().toString().trim())){
								String value = entry.getValue().toString().trim().replace("[", "");
								value = value.replace("]", "");
								value = value.replace("\"", "");
								
								if(value != null && !("").equals(value)){
									exchangeMap.put(entry.getKey().toString(), value);
								}
							}
						}
						baseInfoMap.put("exchange", exchangeMap);
					}
					
					if(baseMsg.containsKey("symbols")){
						JSONArray symbolsArray = baseMsg.getJSONArray("symbols");
						JSONObject job = symbolsArray.getJSONObject(0);
						String ph_en = "";
						if(job.containsKey("ph_en")){
							ph_en = job.get("ph_en").toString();
						}
						String ph_other = "";
						if(job.containsKey("ph_other")){
							ph_other = job.get("ph_other").toString();
						}
						String ph_am = "";
						if(job.containsKey("ph_am")){
							ph_am = job.get("ph_am").toString();
						}	
						String ph_en_mp3 = "";
						if(job.containsKey("ph_en_mp3")){
							ph_en_mp3 = job.get("ph_en_mp3").toString();
						}	
						String ph_am_mp3 = "";
						if(job.containsKey("ph_am_mp3")){
							ph_am_mp3 = job.get("ph_am_mp3").toString();
						}
						baseInfoMap.put("ph_en", ph_en);
						baseInfoMap.put("ph_am", ph_am);
						baseInfoMap.put("ph_en_mp3", ph_en_mp3);
						baseInfoMap.put("ph_am_mp3", ph_am_mp3);
						baseInfoMap.put("ph_other", ph_other);
						if(job.containsKey("parts")){
							JSONArray partsArray = JSONArray.fromObject(job.get("parts").toString());
							Map<String, List<String>> partsMap = new HashMap<String, List<String>>();
							for(int j = 0; j < partsArray.size(); j++){ 
								JSONObject partjob = partsArray.getJSONObject(j);
								if(partjob.containsKey("part") && partjob.containsKey("means")){
									String part = partjob.get("part").toString();
									List<String> meanList = partjob.getJSONArray("means");
									partsMap.put(part, meanList);
								}
							}
							baseInfoMap.put("parts", partsMap);
						}
					}
					resultMap.put("base", baseInfoMap);
				}else{
					
				}
			}else{
				
			}

			//phraseInfo
			Map<Object, Object> phraseInfoMap = new HashMap<Object, Object>();
			if(map.get("phrase") != null){
				JSONArray phraseArray = JSONArray.fromObject(map.get("phrase"));
				for(int j = 0; j < phraseArray.size(); j++){ 
					JSONObject phrasejob = phraseArray.getJSONObject(j);
					if(phrasejob.containsKey("cizu_name")){
						String cizu_name = phrasejob.get("cizu_name").toString();
						List<Map<Object, Object>> jxlist = new ArrayList<Map<Object,Object>>();
						
						if(phrasejob.containsKey("jx")){
							JSONArray jxArray = JSONArray.fromObject(phrasejob.get("jx").toString());
							for(int m = 0; m < jxArray.size(); m++){ 
								Map<Object, Object> jxMap = new HashMap<Object, Object>();
								
								JSONObject jxjob = jxArray.getJSONObject(m);
								if(jxjob.containsKey("jx_en_mean")){
									jxMap.put("jx_en_mean", jxjob.get("jx_en_mean").toString());
								}
								if(jxjob.containsKey("jx_cn_mean")){
									jxMap.put("jx_cn_mean", jxjob.get("jx_cn_mean").toString());
								}
								List<Map<String, String>> ljList = new ArrayList<Map<String,String>>();
								JSONArray ljArray = JSONArray.fromObject(jxjob.get("lj"));
								for(int n = 0; n < ljArray.size(); n++){ 
									JSONObject ljjob = ljArray.getJSONObject(n);
									if(ljjob.containsKey("lj_ly") && ljjob.containsKey("lj_ls")){
										Map<String, String> sentenceMap1 = new HashMap<String, String>();
										sentenceMap1.put(("lj_sen"), ljjob.get("lj_ly").toString());
										sentenceMap1.put(("lj_content"), ljjob.get("lj_ls").toString());
										ljList.add(sentenceMap1);
									}
								}
								jxMap.put("lj", ljList);
								
								jxlist.add(jxMap);
							}
							
							phraseInfoMap.put(cizu_name, jxlist);
						}
					}
				}
				resultMap.put("phrase", phraseInfoMap);
			}
			
			//synonym
			if(map.get("synonym") != null){
				JSONArray synonymArray = JSONArray.fromObject(map.get("synonym"));
				Map<Object, Object> synonymMap = new HashMap<Object, Object>();
				for(int j = 0; j < synonymArray.size(); j++){ 
					JSONObject synonymjob = synonymArray.getJSONObject(j);
					if(synonymjob.containsKey("part_name")){
						String part_name = synonymjob.get("part_name").toString();
						if(synonymjob.containsKey("means")){
							JSONArray meansArray = JSONArray.fromObject(synonymjob.get("means"));
							Map<Object, Object> meansMap = new HashMap<Object, Object>();
							for(int m = 0; m < meansArray.size(); m++){ 
								JSONObject meansjob = meansArray.getJSONObject(m);
								if(meansjob.containsKey("word_mean") && meansjob.containsKey("cis")){
									String word_mean = meansjob.get("word_mean").toString();
									List<String> cisList = meansjob.getJSONArray("cis");
									meansMap.put(word_mean, cisList);
								}
							}
							synonymMap.put(part_name, meansMap);
						}
					}
				}
				resultMap.put("synonym", synonymMap);
			}
			
			//antonym
			if(map.get("antonym") != null){
				JSONArray antonymArray = JSONArray.fromObject(map.get("antonym"));
				Map<Object, Object> antonymMap = new HashMap<Object, Object>();
				for(int j = 0; j < antonymArray.size(); j++){ 
					JSONObject antonymjob = antonymArray.getJSONObject(j);
					if(antonymjob.containsKey("part_name")){
						String part_name = antonymjob.get("part_name").toString();
						if(antonymjob.containsKey("means")){
							JSONArray meansArray = JSONArray.fromObject(antonymjob.get("means"));
							Map<Object, Object> meansMap = new HashMap<Object, Object>();
							for(int m = 0; m < meansArray.size(); m++){ 
								JSONObject meansjob = meansArray.getJSONObject(m);
								if(meansjob.containsKey("word_mean") && meansjob.containsKey("cis")){
									String word_mean = meansjob.get("word_mean").toString();
									List<String> cisList = meansjob.getJSONArray("cis");
									meansMap.put(word_mean, cisList);
								}
							}
							antonymMap.put(part_name, meansMap);
						}
					}
				}
				resultMap.put("antonym", antonymMap);
			}
			
			//sentenceInfo
			if(map.get("sentence") != null){
				JSONArray jsonArray = JSONArray.fromObject(map.get("sentence"));
				List<Map<String, String>> sentenceList = new ArrayList<Map<String,String>>();
				for(int j = 0; j < jsonArray.size(); j++){ 
					JSONObject sentence = jsonArray.getJSONObject(j);
					if(sentence.containsKey("Network_en") && sentence.containsKey("Network_cn")){
						Map<String, String> sentenceMap = new HashMap<String, String>();
						sentenceMap.put("sen_english", sentence.get("Network_en").toString());
						sentenceMap.put("sen_chinese", sentence.get("Network_cn").toString());
						sentenceList.add(sentenceMap);
					}
				}
				resultMap.put("sentence", sentenceList);
			}
			
			//auth_sentence
			if(map.get("auth_sentence") != null){
				JSONArray auth_senArray = JSONArray.fromObject(map.get("auth_sentence"));
				List<Map<String, String>> auth_senList = new ArrayList<Map<String,String>>();
				for(int j = 0; j < auth_senArray.size(); j++){ 
					JSONObject sentence = auth_senArray.getJSONObject(j);
					if(sentence.containsKey("content") && sentence.containsKey("source_title")){
						Map<String, String> auth_senMap = new HashMap<String, String>();
						auth_senMap.put("sen_english", sentence.get("content").toString());
						auth_senMap.put("sen_title", sentence.get("source_title").toString());
						auth_senList.add(auth_senMap);
					}
				}
				resultMap.put("auth_sentence", auth_senList);
			}
			
			//jushi
			if(map.get("jushi") != null){
				JSONArray jushiArray = JSONArray.fromObject(map.get("jushi"));
				List<Map<String, String>> jushiList = new ArrayList<Map<String,String>>();
				for(int j = 0; j < jushiArray.size(); j++){ 
					JSONObject sentence = jushiArray.getJSONObject(j);
					if(sentence.containsKey("english") && sentence.containsKey("chinese")){
						Map<String, String> jushiMap = new HashMap<String, String>();
						jushiMap.put("sen_english", sentence.get("english").toString());
						jushiMap.put("sen_chinese", sentence.get("chinese").toString());
						jushiList.add(jushiMap);
					}
				}
				resultMap.put("jushi_sentence", jushiList);
			}

			return JSONObject.fromObject(resultMap).toString();
		}else{
			return null;
		}
	}
	
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

	public static String get(Map<String, String> map, String encoding, String URL) {
		
		try {
			CloseableHttpClient client = HttpClients.createDefault();  
	        
	        URIBuilder uri = new URIBuilder(URL);
	        for (Map.Entry<String, String> entry : map.entrySet()){  
	        	uri.addParameter(entry.getKey(),entry.getValue());
			}
	        //创建httpGet对象
	        HttpGet hg = new HttpGet(uri.build());
	        //设置请求的报文头部的编码
	        hg.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset="+encoding));
	        //设置期望服务端返回的编码
	        hg.setHeader(new BasicHeader("Accept", "text/plain;charset="+encoding));
	        //请求服务
	        CloseableHttpResponse response = client.execute(hg);
	        //获取响应码
	        int statusCode = response.getStatusLine().getStatusCode();
	        
	        String resStr = "";
	        
	        if (statusCode == 200) {
	            //获取返回实例entity
	            HttpEntity entity = response.getEntity();
	            //通过EntityUtils的一个工具方法获取返回内容
	            resStr = EntityUtils.toString(entity, encoding);
	        } else {
	        	
	        }
	        //关闭response和client
	        response.close();
	        client.close();
	        return resStr;
		} catch (Exception e) {
			return null;
        }
    }
	
	private static List<String> getExamWord(Connection con,Statement stmt, ResultSet rs) throws SQLException {
		String sql = "SELECT wordContent FROM tm_exam_word WHERE isValid = 1";

		PreparedStatement pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		List<String> list = new ArrayList<String>();
		while (rs.next()) {
 			String wordContent = rs.getString("wordContent");
 			list.add(wordContent);
		}
		
		return list;
	}
	
	private static List<String> getGeneralWord(Connection con,Statement stmt, ResultSet rs) throws SQLException {
		String sql = "SELECT wordContent FROM tm_general_word WHERE isValid = 1";

		PreparedStatement pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		List<String> list = new ArrayList<String>();
		while (rs.next()) {
 			String wordContent = rs.getString("wordContent");
 			list.add(wordContent);
		}
		
		return list;
	}
	
	public static String getCacheWord(String key){
		try{
			String wordKey = "test:readapp:word:explain";

			//return redisUtil.hget(wordKey, key);
			return null;
		}catch(Exception e){
			return null;
		}
	}
	
	
}
