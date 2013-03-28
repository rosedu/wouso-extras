package cdl.android.general;

import org.json.JSONException;
import org.json.JSONObject;

public class BazaarItem {
	private String name;
	private String title;
	private String type;
	private int dueDays;
	private String image;
	private int price;
	private int groupId;
	private int id;
	private String description;
	
	public void parseContent(JSONObject jobj) throws JSONException{
		name = jobj.getString("name");
		title = jobj.getString("title");
		type = jobj.getString("type");
		dueDays = jobj.getInt("due_days");
		image = jobj.getString("image");
		price = jobj.getInt("price");
		//groupId = jobj.getInt("group_id");
		id = jobj.getInt("id");
		description = jobj.getString("description");
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getType(){
		return this.type;
	}
	
	public String getDueDays(){
		return "" + this.dueDays;
	}
	
	public String getImage(){
		return this.image;
	}
	
	public String getPrice(){
		return "" + this.price;
	}
	
	public String getId(){
		return "" + this.id;
	}
	
	public String getGroupId(){
		return "" + this.groupId;
	}
	
	public String getDescription(){
		return this.description;
	}
}
