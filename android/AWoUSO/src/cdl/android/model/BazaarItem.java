package cdl.android.model;

import org.json.JSONException;
import org.json.JSONObject;

public class BazaarItem {
	String title;
	String description;
	String price, due_days;

	public BazaarItem(JSONObject obj) {
		try {
			title = obj.getString("title");
			//description = obj.getString("description");
			due_days = obj.getString("due_days") + " days";
			price = obj.getString("price");
		} catch (JSONException ex) {
			ex.printStackTrace();
		}

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDue_days() {
		return due_days;
	}

	public void setDue_days(String due_days) {
		this.due_days = due_days;
	}
}
