package cdl.android.general;

import org.json.JSONArray;
import org.json.JSONObject;

public class ServerResponse {
	private JSONObject objData;
	private JSONArray arrData;
	private boolean success;
	private String error;

	public ServerResponse() {
	}

	public ServerResponse(boolean result, String error) {
		this.success = result;
		this.error = error;
	}

	public void setStatus(boolean success) {
		this.success = success;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setData(JSONObject obj) {
		this.objData = obj;
	}

	public void setArrayData(JSONArray arr) {
		this.arrData = arr;
	}

	public JSONObject getData() {
		return objData;
	}

	public JSONArray getArrayData() {
		return arrData;
	}

	public boolean getStatus() {
		return success;
	}

	public String getError() {
		return error;
	}
}