package com.example.trx_tracking.helper;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class JsonHelper {

public static String convertMapToJson(Map<String,Object> map) {
		
		Gson gsonObj = new Gson();
		
		return gsonObj.toJson(map);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> mapJsonString(String json){
		
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, Object> map;
		try {
			map = mapper.readValue(json, Map.class);
			return map;
		} catch (JsonMappingException e) {
			
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String convertJsonToAssociatedArray(Map<String,Object> data) {
		
		String outResult="";
		
		for(Map.Entry<String,Object> e : data.entrySet()) {
			
			outResult=outResult+String.format("&data[%s]=%s", e.getKey(),e.getValue().toString());
		}
		
		outResult=outResult.replace("[", "%5B");
		outResult=outResult.replace("]", "%5D");
		outResult=outResult.replace(" ", "+");
		outResult=outResult.replace(":", "%3A");
		
		
		return outResult;
	}
	
	public static String checkStringObject(Object o) {
		
		if(o==null)
			return "";
		else
			return (String) o;
	}
	
	public static String checkBooleanObject(Object o) {
		
		if(o==null)
			return "false";
		else
			return (String) o;
	}
	
	public static String checkAmountObject(Object o) {
		
		if(o==null)
			return "0";
		else
			return (String) o;
	}
	
	public static String stringBuilder(Object input) {
		
		if(input==null)
			return "";
		
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < input.toString().length(); i++) {
		    char ch = input.toString().charAt(i);
		    if (ch >= 32 && ch < 127)
		        buf.append(ch);
		    else
		        buf.append(String.format("\\u%04x", (int) ch));
		}
		return buf.toString();
	}
}
