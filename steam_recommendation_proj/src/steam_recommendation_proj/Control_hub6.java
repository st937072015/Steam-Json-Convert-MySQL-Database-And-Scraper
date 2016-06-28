package steam_recommendation_proj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.remote.server.handler.MaximizeWindow;

public class Control_hub6 {

	public static void main(String[] args) {

		try {

			 // 讀取評論作者之id
			FileReader json_reader = new FileReader("C:\\Users\\John-Wall\\Desktop\\Steam_user_list\\Steam_user_list.json");
			JSONParser parser = new JSONParser();
			JSONObject read_parser = (JSONObject) parser.parse(json_reader);

			JSONArray userlist_array = (JSONArray) read_parser.get("all_user_list");

			Iterator it = userlist_array.iterator();
			
			JSONArray output_array= new JSONArray();
			
			Set<String> steam_user_review_dictionary_normal_set = new HashSet<String>();

			// 取出Iterator中的遊戲資料
			while (it.hasNext()) {

				
				JSONObject collection = (JSONObject) it.next();
				Steam_review_dictionary normal =new Steam_review_dictionary();

				normal.produce_steam_review_dictionary_normal("C:\\Users\\John-Wall\\Desktop\\Steam_user_review_clean\\" + collection.get("id").toString() + ".json", "steam_user_respective_review_clean", steam_user_review_dictionary_normal_set);
				
				
				

			}
			
			
			
			for (String element:steam_user_review_dictionary_normal_set) {
				
				//System.out.println(element);
				
				// 建立刷新Json物件
				JSONObject output_obj = new JSONObject();
				output_obj.put("word", element);
				output_array.add(output_obj);
				
				
				
			}
		
			
			 // 建立第一種對映方法之字典
			 FileOutputStream fos = new FileOutputStream("C:\\Users\\John-Wall\\Desktop\\Steam_review_dictionary\\Steam_user_review_dictionary_normal.json");
			 Writer json_writer = new OutputStreamWriter(fos, "UTF8");
			 
			 // 寫入JSON物件
			 json_writer.write("{" + "\"all_normal_word\" :" + output_array.toJSONString() + "}");
			 
			 // 關閉寫入
			 json_writer.flush();
			 json_writer.close();
			
			


		} catch (

		FileNotFoundException e) {
			System.out.println(e.toString());
		} catch (IOException e) {
			System.out.println(e.toString());
		} catch (ParseException e) {
			System.out.println(e.toString());
		} catch (NullPointerException e) {
			System.out.println(e.toString());
		}
		
		
	}

}
