package com.elite.webdata.word;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordAnalyze {
	
	private static String inPath = "E:\\123.txt";
	private static String outPath = "E:\\write.txt";
	private static String encoding = "utf-8";
	
	
	
	public static void main(String[] args) {
		
		//String strSen = "Nintendo,$39.99 Target.com:\"You'll , 10:00 I'll sing.\" , Bird’s Bird’s.1,00.0";
		File inFile = new File(inPath);
		StringBuffer buffer = new StringBuffer();
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(inFile), encoding);
			BufferedReader bufferR = new BufferedReader(read);
			
		    String inLine;
		    if(inFile.isFile() && inFile.exists()){
		        while((inLine = bufferR.readLine()) != null){
		        	Map<String,String> map = new HashMap<String, String>();
		        	map.put("p.m.", "p.e.");
		        	map.put("p.m", "p.e");
		        	map.put("a.m.", "a.d");
		        	map.put("a.m", "a.d");
		        	
		        	Map<String,Integer> resultMap = cleanWords(inLine,map);
		        	buffer.append("\r\n");
		        	buffer.append("句子："+inLine+"\r\n");
		        	buffer.append("单词数："+resultMap.size()+"\r\n");
		        	for (Map.Entry<String, Integer> entry : resultMap.entrySet()) {
		        		buffer.append(entry.getKey() + "-->" + entry.getValue() + " ;");
					}
		        	buffer.append("\r\n");
		        }
		        read.close();
		        //TxtOutputUtil.output(outPath, buffer.toString());
		        System.out.println(buffer.toString());
		    }
		}catch (Exception e) {
			e.printStackTrace();
		}
	    
		
	}
	
	public static Map<String,Integer> cleanWords(String strSen,Map<String,String> map){
		
		if(strSen == null || strSen.isEmpty()){
			return null;
		}
		
		Map<String,Integer> senWordMap =  new HashMap<String,Integer>();
		strSen = strSen.replaceAll("[\"|‘|�|!|\\?|;|？|【|】|“|”|…|（|）|{|}|—|\\(|\\)|\\[|\\]|\\*|&|^|#|＃|＼|％|×|＠|｜|……|＆|！|｛|｝|——|－|＋|＝|+|=|_|《|》|、|\\/|<|>|•|　|¿|ʌ|]{1,}", " ");
		strSen = strSen.toLowerCase().trim();
		String stx[] = strSen.split(" ");
		
		if(stx!=null && stx.length>0){
			for(String words:stx){
				words = words.trim();
				words = words.replaceAll("'{1,}", "'");
				words = words.replaceAll("[-|–]{1,}", "-");
				words = words.replaceAll("\\${1,}", "\\$");
				words = words.replaceAll("[￥|￥]{1,}", "￥");
				words = words.replaceAll("[,|，]{1,}", ",");
				words = words.replaceAll("[:|：]{1,}", ":");
				words = words.replaceAll("[\\.|。|]{1,}", "\\.");
				words = words.replaceAll("ó{1,}", "o");
				words = words.replaceAll("é{1,}", "e");
				words = words.replaceAll("á{1,}", "a");
				words = words.replaceAll("í{1,}", "i");
				words = words.replaceAll("ñ{1,}", "n");
				
				words = cleanChars(words, ":", map);
				words = cleanChars(words, ",", map);
				words = cleanChars(words, ".", map);
				
				if(words != null){
					String[] wordAry = words.split(" ");
					for (String word : wordAry) {

						word = cleanForeAftChar(word);
						
						if(word.equals("-")||word.equals("$")||word.equals("")||word.equals("￥")||word.equals("'")){
							continue;
						}
						
						if(senWordMap.containsKey(word)){
							senWordMap.put(word,senWordMap.get(word)+1);
						}else{
							senWordMap.put(word,1);
						}
					}
				}
			}
		}
		return senWordMap;
	}
	
	/**
	 * clean Words strange char
	 * @param srcText
	 * @param findText
	 * @return
	 */
	public static String cleanChars(String words,String character,Map<String,String> map){
		
		if(words == null || words.isEmpty()){
			return null;
		}
		
		List<Integer> indexList = appearIndex(words, character);
		for (Integer integer : indexList) {

			int index = integer - 1;
			String str = words.substring(index-1<0 ? 0: index-1, index+2 > words.length() ? words.length() : index+2);
			
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(str.replace(character, ""));
			if( isNum.matches() ){
				
			}else{
				if((".").equals(character)){
					words = cleanForeAftChar(words);
					if(map.containsKey(words)){
						return words;
					}
				}
				StringBuffer buffer = new StringBuffer(words);
				words = buffer.replace(index,index == words.length() ? index : index + 1 ," ").toString();
			}
		}
		
		return words;
	}
	
	/**
	 * get char or string number by String 
	 * @param srcText
	 * @param findText
	 * @return
	 */
	public static int appearNumber(String srcText, String findText) {// put \.
	    int count = 0;
	    Pattern p = Pattern.compile(findText);
	    Matcher m = p.matcher(srcText);
	    while (m.find()) {
	        count++;
	    }
	    return count;
	}
	
	/**
	 * get char or string index by String 
	 * @param srcText
	 * @param findText
	 * @return
	 */
	public static List<Integer> appearIndex(String srcText, String findText) {
	    List<Integer> list = new ArrayList<Integer>();
	    int index = 0;
	    while ((index = srcText.indexOf(findText, index)) != -1) {
	        index = index + findText.length();
	        list.add(index);
	    }
	    return list;
	}
	
	/**
	 * clean Fore Aft Char
	 * @param word
	 * @return
	 */
	public static String cleanForeAftChar(String word) {
		
		if(word == null || word.isEmpty()){
			return null;
		}
		
		if(word.endsWith("'")){
			word = word.substring(0,word.length()-1);//may be a bug with this code
			word = word.trim();
		}
		if(word.startsWith("'")){
			word = word.replaceFirst("'", "");
			word = word.trim();
		}
		
		if(word.endsWith("-")){
			word =  word.substring(0,word.length()-1);//may be a bug with this code
			word = word.trim();
		}
		if(word.startsWith("-")){
			word = word.replaceFirst("-", "");
			word = word.trim();
		}
		
		if(word.endsWith("$")){
			word =  word.substring(0,word.length()-1);//may be a bug with this code
			word = word.trim();
		}
		
		if(word.startsWith(".")){
			word = word.replaceFirst(".", "");
			word = word.trim();
		}
		if(word.endsWith(".")){
			word = word.substring(0,word.length()-1);
			word = word.trim();
		}
		
		if(word.startsWith(",")){
			word = word.replaceFirst(",", "");
			word = word.trim();
		}
		if(word.endsWith(",")){
			word = word.substring(0,word.length()-1);
			word = word.trim();
		}
		
		if(word.startsWith(":")){
			word = word.replaceFirst(":", "");
			word = word.trim();
		}
		if(word.endsWith(":")){
			word = word.substring(0,word.length()-1);
			word = word.trim();
		}
		
		return word;
	}
	
}
