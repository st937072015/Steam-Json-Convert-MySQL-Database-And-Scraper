package steam_recommendation_proj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.remote.server.handler.MaximizeWindow;

public class Control_hub10 {

	public static void main(String[] args) {

		try {

			 // 讀取評論作者清單
			FileReader list_user_json_reader = new FileReader("C:\\Users\\John-Wall\\Desktop\\Steam_user_list\\Steam_user_list.json");
			JSONParser list_user_parser = new JSONParser();
			JSONObject list_user_read_parser = (JSONObject) list_user_parser.parse(list_user_json_reader);

			JSONArray list_user_array = (JSONArray) list_user_read_parser.get("all_user_list");

			Iterator list_user_it = list_user_array.iterator();
			

            JSONArray review_content_idf_array = new JSONArray();
            

            
        	// 讀取normal字典檔
        	FileReader dictionary_read_json_reader = new FileReader("C:\\Users\\John-Wall\\Desktop\\Steam_review_dictionary\\Steam_user_review_dictionary_normal.json");
        	JSONParser dictionary_read_parser = new JSONParser();
        	JSONObject dictionary_read_object = (JSONObject) dictionary_read_parser.parse(dictionary_read_json_reader);

        	
        	JSONArray dictionary_normal_array = (JSONArray) dictionary_read_object.get("all_normal_word");
        	
        	
            

			// 儲存idf結果
			LinkedHashMap<Integer, Double> review_content_idf_hashmap = new LinkedHashMap<Integer, Double>();
			
			
			
			// 計算全部review數量
			int review_all_count = 0;
			
			Steam_review_tfidf go_tfidf =new Steam_review_tfidf();

			// 取出Iterator中的評論作者的遊戲評論資料
			while (list_user_it.hasNext()) {
           
				
				JSONObject collection = (JSONObject) list_user_it.next();
				
			
			    
				
				
				
				
				review_all_count = review_all_count + go_tfidf.tf_idf(dictionary_normal_array, "C:\\Users\\John-Wall\\Desktop\\Steam_user_review_clean\\" + collection.get("id").toString() + ".json", "steam_user_respective_review_clean", collection.get("id").toString(), "C:\\Users\\John-Wall\\Desktop\\Steam_user_review_tfidf\\", "steam_review_tfidf", review_content_idf_hashmap);

				

			}
			
			// debug
			System.out.println(review_all_count);
		       
			for (int key : review_content_idf_hashmap.keySet()) {
				
				
				
				review_content_idf_hashmap.put(key, Math.log(review_all_count / review_content_idf_hashmap.get(key)));
				
				
				
				
			}
			
			review_content_idf_array.add(review_content_idf_hashmap);
			
			
			 // 建立idf分數參考字典
			 FileOutputStream fos = new FileOutputStream("C:\\Users\\John-Wall\\Desktop\\Steam_review_dictionary\\steam_user_review_idf.json");
			 Writer json_writer = new OutputStreamWriter(fos, "UTF8");

			 // 寫入JSON物件
			 json_writer.write("{" + "\"steam_user_review_idf\" :" + review_content_idf_array.toJSONString() + "}");
			 
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
