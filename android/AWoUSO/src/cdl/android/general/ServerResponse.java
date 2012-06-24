package cdl.android.general;

public class ServerResponse {
	private Boolean success;
	private String error;

	public ServerResponse(boolean result, String error) {
		this.success = result;
		this.error = error;
	}

	public Boolean getResponse() {
		return success;
	}

	public String getError() {
		return error;
	}
}