package cdl.android.general;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import cdl.android.R;
import cdl.android.server.ApiHandler;

public class BazaarItem {
	private String name;
	private String title;
	private String type;
	private int dueDays;
	private String imageURL;
	private int price;
	private int groupId;
	private int id;
	private String description;
	
	public void parseContent(JSONObject jobj) throws JSONException{
		name = jobj.getString("name");
		title = jobj.getString("title");
		type = jobj.getString("type");
		id = jobj.getInt("id");
		dueDays = jobj.getInt("due_days");
		imageURL = "https://wouso.cs.pub.ro/2013" + jobj.getString("image_url");
		System.out.println(imageURL);
		price = jobj.getInt("price");
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
	
	public String getImageURL(){
		return this.imageURL;
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
