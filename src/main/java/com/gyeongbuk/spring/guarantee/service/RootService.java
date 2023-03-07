package com.gyeongbuk.spring.guarantee.service;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RootService {
	
	public HashMap<String,Object> formatJmMapRequest(HttpServletRequest request) {
	    HashMap<String, Object> jmMap = new HashMap<String, Object>();
	    
	    Enumeration<String> enumber = request.getParameterNames();
	    
	    while (enumber.hasMoreElements()) {
	        String key = enumber.nextElement().toString();
	        String value = request.getParameter(key);

	        jmMap.put(key, value);  
	    }
	    
	    return jmMap;
	}
	
	/**
	 * @param JSONObject
	 * @apiNote JSONObject를 Map<String, String> 형식으로 변환처리.
	 * @return Map<String,String>
	 * **/
	public static Map<String, Object> getMapFromJsonObject(HttpServletRequest request){
	    Map<String, Object> map = null;
	    
	    try {
	       map = new ObjectMapper().readValue(request.toString(), Map.class);
	    } catch (JsonParseException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (JsonMappingException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return map;
	}
	
	public HashMap<String, String> stringToHashMap(String params){
		
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, String> result = new HashMap<String, String>();
		
		try {
			result = mapper.readValue(params, new TypeReference<HashMap<String, String>>() {});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * @param String
	 * @apiNote Object로 만들어진 array가 String으로 왔을 때 String[] 변환 처리
	 * @return String[]
	 * **/
	public String[] convertObjectArrayStringToStringArray(String string) {

		String targetValue = (string).trim().replace("'", "\"");
		String targetValueTrim = targetValue.substring(1, targetValue.length() - 1);
		String[] targetValueList = targetValueTrim.split("},");
		
		int i = 0;
		while(i < targetValueList.length) {
			if(i != targetValueList.length - 1)
				targetValueList[i] = targetValueList[i] + "}";
			i++;
		}
			    
	    return targetValueList;
	}
	
	/**
	 * @param Map<String, Object>
	 * @apiNote Map<String, Object>를 JSONObject로 변환처리.
	 * @return JSONObject
	 * **/
	public JSONObject convertMapToJson(Map<String, Object> map) {
	    
	    JSONObject json = new JSONObject();
	    String key = "";
	    Object value = null;
	    for(Map.Entry<String, Object> entry : map.entrySet()) {
	        key = entry.getKey();
	        value = entry.getValue();
	        json.put(key, value);
	    }
	    return json;
	}
	
	/**
     * 전달된 파라미터에 맞게 난수를 생성한다
     * @param len : 생성할 난수의 길이
     * @param dupCd : 중복 허용 여부 (1: 중복허용, 2:중복제거)
     * 
     * Created by 닢향
     * http://niphyang.tistory.com
     */
    public String numberGen(int len, int dupCd ) {
        
        Random rand = new Random();
        String numStr = ""; //난수가 저장될 변수
        
        for(int i=0;i<len;i++) {
            
            //0~9 까지 난수 생성
            String ran = Integer.toString(rand.nextInt(10));
            
            if(dupCd==1) {
                //중복 허용시 numStr에 append
                numStr += ran;
            }else if(dupCd==2) {
                //중복을 허용하지 않을시 중복된 값이 있는지 검사한다
                if(!numStr.contains(ran)) {
                    //중복된 값이 없으면 numStr에 append
                    numStr += ran;
                }else {
                    //생성된 난수가 중복되면 루틴을 다시 실행한다
                    i-=1;
                }
            }
        }
        return numStr;
    }
    
    /**
     * form submit 이후의 alertMsg를 생성.
     * @param response : 웹브라우저에게 응답을 돌려줄 빈 객체
     * @param msg : 출력할 메세지 내용
     * @param dupCd : 중복 허용 여부 (1: 중복허용, 2:중복제거)
     * 
     */
	public void makeSubmitAlertMsg(HttpServletResponse response, String msg, String targetLink){
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.println("<script>alert('" + msg + "'); location.href='" + targetLink + "';</script>");
		
		out.flush();
	}

}
