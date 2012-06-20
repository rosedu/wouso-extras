package cdl.android.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Bazaar item (spell) container class
 */
public class BazaarItem {
	String title;
	String description;
	String price, due_days;

	
	/**
	 * Creates a new Bazaar Item from a JSONObject probably taken from the website's API.
	 * @param obj The JSONObject to parse for bazaar info.
	 */
	public BazaarItem(JSONObject obj) {
		try {
			title = obj.getString("title");
			due_days = obj.getString("due_days") + " days";
			price = obj.getString("price");
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Get the item's title.
	 * @return The title of the item.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set a new title.
	 * @param title The new title to be set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get the item's description.
	 * @return A String with the item's description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the item's description.
	 * @param description The new description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the item's price.
	 * @return A string containing the price. 
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * Set a new price for the item.
	 * @param price The new price.
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * Get the due days before the item expires.
	 * @return The number of remaining days.
	 */
	public String getDueDays() {
		return due_days;
	}

	/**
	 * Set the remaining days until the item expires.
	 * @param dueDays The new number of days.
	 */
	public void setDueDays(String dueDays) {
		this.due_days = dueDays;
	}
}
