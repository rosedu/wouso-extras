package cdl.android.ui.message;

import org.json.JSONException;
import org.json.JSONObject;

/**
* Message item
*/
public class MessageItem {
	private String author;
	private String subject;
	private String content;
	private String date;

	public void parseContent(JSONObject obj) throws JSONException {
		author = obj.getString("from");
		subject = obj.getString("subject");
		content = obj.getString("text");
		date = obj.getString("date");
	}

	public String getAuthor() {
		return author;
	}

	public String getSubject() {
		return subject;
	}

	public String getContent() {
		return content;
	}	

	public String getDate() {
		return date;
	}

}


