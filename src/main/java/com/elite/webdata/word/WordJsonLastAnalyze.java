package com.elite.webdata.word;

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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordJsonLastAnalyze {
	
	private static String inpath = "E:\\data\\dict\\json";//E:\\test\\test
	
	private static String mp3path = "E:\\data\\dict\\word\\";
	
	private static String outpath = "E:\\data\\last_dict\\";
	
	private static String word = "";
	
	/*@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		List<String> list = TestFileUtil.MergeFile(inpath);
		StringBuilder sbword = new StringBuilder();
		
		for (int i = 0; i < list.size(); i++) {
			
			String input = TestFileUtil.readFile(list.get(i), "UTF-8");
			
			System.out.println("开始处理"+list.get(i));
			
			Map<Object, Object> map = JSONObject.fromObject(input);
			
			String str = analyJson(map);
			
			sbword.append("INSERT INTO bk_book_word_parse(word, parse) VALUES");
			sbword.append("(\\\\'" + word.toLowerCase() + "\\\\'" + ", " + "\\\\'" + outpath + word.toLowerCase() + "\\" + word.toLowerCase() + ".json" + "\\\\'" + ");\r\n");
			
			//TestFileUtil.outputFile(outpath + word.toLowerCase() + "\\" + word.toLowerCase() + ".json", str, "UTF-8");
			
			cacheword(word.toLowerCase(), outpath + word.toLowerCase() + "\\" + word.toLowerCase() + ".json");
		}
		
		//TestFileUtil.outputFile(outpath + "all.sql", sbword.toString(), "UTF-8");
	}*/
	
	@SuppressWarnings("unchecked")
	public static String analyJson(Map<Object, Object> map){
		
		Map<Object, Object> resultMap = new HashMap<Object, Object>();
		
		if(map.containsKey("base") && !("").equals(String.valueOf(map.get("base")))){
			Map<Object, Object> baseInfoMap = JSONObject.fromObject(map.get("base").toString());
			
			if(!baseInfoMap.containsKey("word_name") || ("").equals(String.valueOf(map.get("word_name")))){
				baseInfoMap.put("word_name", "");
			}else{
				word = String.valueOf(baseInfoMap.get("word_name"));
				baseInfoMap.put("word_name", word.toLowerCase());
			}
			
			if(!baseInfoMap.containsKey("ph_en") || ("").equals(String.valueOf(baseInfoMap.get("ph_en"))) ){
				String ph_other = getWordPH(word);
				if(ph_other != null){
					if(ph_other != null && !("").equals(ph_other)){
						baseInfoMap.put("ph_en", ph_other);
					}else{
						if(baseInfoMap.containsKey("ph_am") && !("").equals(String.valueOf(baseInfoMap.get("ph_am")))){
							baseInfoMap.put("ph_en", String.valueOf(baseInfoMap.get("ph_am")));
						}else{
							baseInfoMap.put("ph_en", "");
						}
					}
				}else{
					baseInfoMap.put("ph_en", "");
				}
			}
			
			if(baseInfoMap.containsKey("ph_en_mp3") && !("").equals(String.valueOf(baseInfoMap.get("ph_en_mp3"))) ){
				
				Boolean flag = checkFile(mp3path + "\\" + word + "\\ph_en.mp3");
				if(flag){
					baseInfoMap.put("ph_en_mp3", "https://dictjson.tope365.com/word_search/" + word + "/" + "ph_en.mp3");
					/*try {
						TestFileUtil.copyFile(mp3path + "\\" + word + "\\ph_en.mp3", outpath + word + "\\" + "ph_en.mp3");
					} catch (IOException e) {
			            e.printStackTrace();
			        }*/
				}else{
					baseInfoMap.put("ph_en_mp3", "");
				}
			}else{
				baseInfoMap.put("ph_en_mp3", "");
			}
			
			if(!baseInfoMap.containsKey("ph_am") || ("").equals(String.valueOf(baseInfoMap.get("ph_am"))) ){
				String ph_other = getWordPH(word);
				if(ph_other != null){
					if(ph_other != null && !("").equals(ph_other)){
						baseInfoMap.put("ph_am", ph_other);
					}else{
						if(baseInfoMap.containsKey("ph_en") && !("").equals(String.valueOf(baseInfoMap.get("ph_en")))){
							baseInfoMap.put("ph_am", String.valueOf(baseInfoMap.get("ph_en")));
						}else{
							baseInfoMap.put("ph_am", "");
						}
					}
				}else{
					baseInfoMap.put("ph_am", "");
				}
			}
			
			if(baseInfoMap.containsKey("ph_am_mp3") && !("").equals(String.valueOf(baseInfoMap.get("ph_am_mp3"))) ){
				Boolean flag = checkFile(mp3path + "\\" + word + "\\ph_am.mp3");
				if(flag){
					baseInfoMap.put("ph_am_mp3", "https://dictjson.tope365.com/word_search/" + word + "/" + "ph_am.mp3");
					/*try {
						TestFileUtil.copyFile(mp3path + "\\" + word + "\\ph_am.mp3", outpath + word + "\\" + "ph_am.mp3");
					} catch (IOException e) {
			            e.printStackTrace();
			        }*/
				}else{
					baseInfoMap.put("ph_am_mp3", "");
				}
			}else{
				baseInfoMap.put("ph_am_mp3", "");
			}
			
			StringBuffer buffer1 = new StringBuffer();
			if(baseInfoMap.containsKey("parts") && !("").equals(baseInfoMap.get("parts"))){
				Map<Object, Object> partsMap = JSONObject.fromObject(baseInfoMap.get("parts").toString());
				if(partsMap.size() > 0){
					int i = 0;
					for(Map.Entry<Object, Object> entry : partsMap.entrySet()){
						i++;
						buffer1.append(entry.getKey()+":");
						List<Object> lsit = (List<Object>)entry.getValue();
						for (Object object : lsit) {
							buffer1.append(String.valueOf(object));
							buffer1.append(",");
						}
						buffer1.replace(buffer1.length()-1, buffer1.length(), ".");
						if(i < partsMap.size()){
							buffer1.append("\r\n");
						}
					}
					//buffer1.replace(buffer1.length()-4, buffer1.length(), "");
				}else{
					buffer1.append("");
				}
			}else{
				buffer1.append("");
			}
			baseInfoMap.put("parts", buffer1.toString());
			
			StringBuffer buffer2 = new StringBuffer();
			if(baseInfoMap.containsKey("exchange")){
				Map<Object, Object> exchangeMap = JSONObject.fromObject(baseInfoMap.get("exchange").toString());
				if(exchangeMap.size() > 0){
					for(Map.Entry<Object, Object> entry : exchangeMap.entrySet()){
						String type = checkWordType(String.valueOf(entry.getKey()));
						buffer2.append(type + ":" + entry.getValue() + "    ");
					}
				}else{
					buffer2.append("");
				}
			}else{
				buffer2.append("");
			}
			baseInfoMap.put("exchange", buffer2.toString());
			
			resultMap.put("base", baseInfoMap);
		}else{
			Map<Object, Object> baseInfoMap = new HashMap<Object, Object>();
			baseInfoMap.put("word_name", "");
			baseInfoMap.put("ph_en", "");
			baseInfoMap.put("ph_en_mp3", "");
			baseInfoMap.put("ph_am", "");
			baseInfoMap.put("ph_am_mp3", "");
			baseInfoMap.put("parts", "");
			baseInfoMap.put("exchange", "");
			
			resultMap.put("base", "");
		}
		
		if(map.containsKey("sentence") && !("").equals(map.get("sentence")) ){
			JSONArray array = JSONArray.fromObject(map.get("sentence").toString());
			resultMap.put("sentence", array);
		}else{
			resultMap.put("sentence", new ArrayList<Object>());
		}
		
		if(map.containsKey("auth_sentence") && !("").equals(map.get("auth_sentence")) ){
			List<Map<Object, Object>> list = new ArrayList<Map<Object,Object>>();
			JSONArray array = JSONArray.fromObject(map.get("auth_sentence").toString());
			
			for (Object object : array) {
				Map<Object, Object> auth = new HashMap<Object, Object>();
				auth = JSONObject.fromObject(object.toString());
				if(!auth.containsKey("sen_chinese") || ("").equals(auth.get("sentence")) ){
					auth.put("sen_chinese", "");
				}
				if(auth.containsKey("sen_title")){
					auth.remove("sen_title");
				}
				list.add(auth);
			}
			resultMap.put("power_sentence", list);
		}else{
			resultMap.put("power_sentence", new ArrayList<Object>());
		}
		
		if(map.containsKey("phrase") && !("").equals(map.get("phrase")) ){
			Map<Object, Object> phraseMap = JSONObject.fromObject(map.get("phrase").toString());
			List<Map<Object, Object>> list = new ArrayList<Map<Object,Object>>();
			
			for(Map.Entry<Object, Object> entry : phraseMap.entrySet()){
				StringBuffer buffer3 = new StringBuffer();
				Map<Object, Object> phrasemap = new HashMap<Object, Object>();
				phrasemap.put("sen_english", entry.getKey());
				List<Object> lsit1 = (List<Object>)entry.getValue();
				for (Object object1 : lsit1) {
					Map<Object, Object> objectMap1 = JSONObject.fromObject(object1);
					if(objectMap1.get("jx_en_mean") != null && !("").equals(objectMap1.get("jx_en_mean"))){
						buffer3.append(objectMap1.get("jx_en_mean") + "\r\n");
					}
					if(objectMap1.get("jx_cn_mean") != null && !("").equals(objectMap1.get("jx_en_mean"))){
						buffer3.append(objectMap1.get("jx_cn_mean") + "\r\n");
					}
					
					List<Object> lsit2 = (List<Object>)objectMap1.get("lj");
					if(lsit2 != null && lsit2.size() > 0){
						for (Object object2 : lsit2) {
							Map<Object, Object> objectMap2 = JSONObject.fromObject(object2);
							buffer3.append(objectMap2.get("lj_sen") + "\r\n");
							buffer3.append(objectMap2.get("lj_content") + "\r\n");
						}
					}
				}
				phrasemap.put("sen_chinese", buffer3.toString());
				list.add(phrasemap);
			}
			resultMap.put("phrase", list);
		}else{
			resultMap.put("phrase", new ArrayList<Object>());
		}
		
		return JSONObject.fromObject(resultMap).toString();
	}
	
	public static Boolean checkFile(String path){
		try{
			File file = new File(path);
            if (!file.exists()) {
            	return false;
            }else{
            	return true;
            }
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public static String cacheword(String key, String value ){
		try{
			String wordKey = "test:readapp:word:explain";
			//redisUtil.hset(wordKey, key, value);
		}catch(Exception e){
			return null;
		}
		return "0";
	}
	
	public static String checkWordType(String word){
		
		String type = "";
		
		if(("word_pl").equals(word)){
			type = "复数";
		}else if(("word_third").equals(word)){
			type = "第三人称单数";
		}else if(("word_past").equals(word)){
			type = "过去式";
		}else if(("word_done").equals(word)){
			type = "过去分词";
		}else if(("word_ing").equals(word)){
			type = "现在分词";
		}else if(("word_er").equals(word)){
			type = "比较级";
		}else if(("word_est").equals(word)){
			type = "最高级";
		}else if(("word_prep").equals(word)){
			type = "介词";
		}else if(("word_adv").equals(word)){
			type = "副词";
		}else if(("word_verb").equals(word)){
			type = "动词";
		}else if(("word_noun").equals(word)){
			type = "名词";
		}else if(("word_adj").equals(word)){
			type = "形容词";
		}else if(("word_conn").equals(word)){
			type = "连词";
		}else{
			type = "其他";
		}
		
		return type;
	}
	
	@SuppressWarnings("unchecked")
	public static String getWordPH(String key){
		
		String URL = "http://www.iciba.com/index.php";
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("a", "getWordMean");
		parameter.put("list", "1%2C3%2C4%2C8%2C9%2C12%2C13%2C15");
		parameter.put("c", "search");
		parameter.put("_", "1524099728910");
		parameter.put("word", key);
		
		String result = get(parameter, "UTF-8", URL);
		Map<Object, Object> map = JSONObject.fromObject(result);
		try{
			if(map.containsKey("baesInfo") ){
				JSONObject baseMsg = JSONObject.fromObject(map.get("baesInfo").toString());
				if(baseMsg.containsKey("symbols")){
					JSONArray symbolsArray = baseMsg.getJSONArray("symbols");
					JSONObject job = symbolsArray.getJSONObject(0);
						
					String ph_other = "";
					if(job.containsKey("ph_other")){
						ph_other = job.get("ph_other").toString();
					}
					
					return ph_other;
				}else{
					return null;
				}
			}else{
				return null;
			}
		}catch (Exception e) {
			return null;
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
}
