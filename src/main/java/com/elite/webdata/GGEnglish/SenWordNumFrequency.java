package com.elite.webdata.GGEnglish;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SenWordNumFrequency {

	private static String inPath = "E:\\temp\\ceshi\\4_4253\\videourl.srt";
	private static String ENCODING = "UTF-8";
	
	public static void main(String[] args) {
		
		List<Integer> list = inputFile(inPath, ENCODING);
		
		if(list.size() == 0){
			System.out.println("ERROR");
		}else{
			System.out.println("SIZE:  " + list.size());
		}
		
		Integer max = list.get(0);
		Integer min = list.get(0);
		Integer avg = 0;
		Integer num = 0;
		
		for (Integer integer : list) {
			
			if(min > integer){
				min = integer;
			}
			
			if(max < integer){
				max = integer;
			}
			
			avg += integer;
		}
		
		System.out.println("min:  " + min);
		System.out.println("max:  " + max);
		
		avg = avg/list.size();
		System.out.println("avg:  " + avg);
		
		
		Integer startNum = avg - (avg-min)/2;
		Integer endNum = avg + (max-avg)/2;
		
		System.out.println("startNum:  " + startNum );
		System.out.println("endNum:  " + endNum );
		
		for (Integer integer : list) {
			if(integer >= startNum && integer <= endNum){
				num ++ ;
			}
		}
		
		DecimalFormat df = new DecimalFormat("0.00%");
		
		System.out.println("num:  " + num);
		
		System.out.println(startNum + "--" + endNum + ":" +df.format(Float.parseFloat(num.toString()) / list.size()));
	}
	
	private static List<Integer> inputFile(String filePath, String encoding) {
    	
    	if(encoding == null){
			encoding = ENCODING;
		}
    	
    	List<Integer> resultList = new ArrayList<Integer>();
    	Pattern numRegex = Pattern.compile("^[-\\+]?[\\d]*$");
    	Pattern senRegex = Pattern.compile(".*[a-zA-Z]+.*");
    	int unm = 0;
    	
    	File inFile = new File(filePath);
    	try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(inFile), encoding);
			BufferedReader bufferR = new BufferedReader(read);
			
		    String inLine;
		    if(inFile.isFile() && inFile.exists()){
		        while((inLine = bufferR.readLine()) != null){
		        	if(numRegex.matcher(inLine).matches()){
		        		unm ++;
		        	}
		        	if(senRegex.matcher(inLine).matches()){
		        		
		        		resultList.add(cleanWords(inLine, null));
		        	}
		        }
		        read.close();
		    }
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultList;
    }
	
	//分析句子  单词数
	private static Integer cleanWords(String strSen,Map<String,String> map){
		
		if(strSen == null || strSen.isEmpty()){
			return null;
		}
		
		Integer wordNum = 0;
		
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
						if(word == null || word.equals("")){
							continue;
						}
						if(word.equals("-")||word.equals("$")||word.equals("")||word.equals("￥")||word.equals("'")){
							continue;
						}
						
						wordNum ++;
					}
				}
			}
		}
		return wordNum;
	}
	
	private static String cleanForeAftChar(String word) {
		
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
	
	private static String cleanChars(String words,String character,Map<String,String> map){
		
		if(words == null || words.isEmpty()){
			return null;
		}
		if(map == null ){
			map = new HashMap<String, String>();
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
	
	private static List<Integer> appearIndex(String srcText, String findText) {
	    List<Integer> list = new ArrayList<Integer>();
	    int index = 0;
	    while ((index = srcText.indexOf(findText, index)) != -1) {
	        index = index + findText.length();
	        list.add(index);
	    }
	    return list;
	}
}
