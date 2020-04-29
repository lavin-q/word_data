package com.elite.webdata.word;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
//import util.TestFileUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordJsonAnalyze {
	
	private static String path = "E:\\word";
	
	private static String outpath = "E:\\data\\wordData";
	
	private static String logpath = "E:\\err.txt";
	
	private static String word = "";
	
	private static StringBuffer buffer = new StringBuffer();
	
	/*@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		List<String> list = TestFileUtil.MergeFile(path);
		
		for (int i = 0; i < list.size(); i++) {

			String input = TestFileUtil.readFile(list.get(i), "UTF-8");
			
			System.out.println("开始处理"+list.get(i));
			
			String filePath = list.get(i).substring(list.get(i).indexOf("\\"), list.get(i).length());
			filePath = filePath.substring(0, filePath.lastIndexOf("\\"));
			
		    String fileName = list.get(i).substring(list.get(i).lastIndexOf("\\"), list.get(i).length());
		    fileName = fileName.replaceAll("-", "").replace(".json", "").trim();
		    
		    if(input != null && input.length() > 0){
		    	
		    	Map<Object, Object> map = JSONObject.fromObject(input);
				
				String output = analyJson(map,list.get(i));
				
				if(!("").equals(word)){
					fileName = "\\"+ word + ".json";
				}else{
					fileName = fileName + ".json";
				}
				
				TestFileUtil.outputFile(outpath + filePath + fileName, output, "UTF-8");
				
				TestFileUtil.outputFile(logpath, buffer.toString(), "UTF-8");
				
				word = "";
		    }else{
		    	buffer.append(list.get(i)+"\r\n");
		    	
		    	word = "";
		    }
		}
	}
	*/
	
	@SuppressWarnings("unchecked")
	public static String analyJson(Map<Object, Object> map,String name){
		
		Map<Object, Object> resultMap = new HashMap<Object, Object>();
		
		if(map != null && ("success").equals(map.get("errmsg").toString())){
			//baseInfo
			Map<Object, Object> baseInfoMap = new HashMap<Object, Object>();
			if(map.containsKey("baesInfo") ){
				JSONObject baseMsg = JSONObject.fromObject(map.get("baesInfo").toString());
				if(baseMsg.containsKey("word_name") ){
					String word_name = baseMsg.get("word_name").toString();
					baseInfoMap.put("word_name", word_name);
					word = word_name;
					
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
					buffer.append(name+"\r\n");
					word = "";
				}
			}else{
				buffer.append(name+"\r\n");
				word = "";
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
	
}