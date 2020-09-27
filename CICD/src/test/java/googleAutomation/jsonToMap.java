package googleAutomation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

public class jsonToMap {
	public static void main(String []args){
		
	String json = "{\n" +
	           "   \"token\":\"\",\n" +
	           "   \"type\":\"1\",\n" +
	           "   \"message\":{\n" +
	           "      \"type\":1,\n" +
	           "      \"message\":\"message\"\n" +
	           "   }\n" +
	           "}";
	String js = json.toString();
	JstringToMap(js);
//	Map<String, String> stringMap = JstringToMap(json);
//	System.out.println(stringMap);
	}
	
	public static Map<String,String> JstringToMap(String json){
		Map<String,String> map = new HashMap<String,String>();
		JSONObject jsonObj = new JSONObject(json);
		Iterator<String> itr= jsonObj.keys();
		
		while(itr.hasNext()){
			String key = (String)itr.next();
			String value = jsonObj.getString(key);
			map.put(key, value);
		}
		System.out.println("bla");
		System.out.println(map);
		return map;
	}
	
}