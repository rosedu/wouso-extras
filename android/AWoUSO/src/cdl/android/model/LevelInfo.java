package cdl.android.model;

/**
 * Game level info container class
 */
public class LevelInfo {
	String name;
	String title;
	String image;
	String _state;
	int percents;
	int groupID;
	int id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getPercents() {
		return percents;
	}

	public void setPercents(int percents) {
		this.percents = percents;
	}

	public String get_state() {
		return _state;
	}

	public void set_state(String _state) {
		this._state = _state;
	}

	public int getGroup_id() {
		return groupID;
	}

	public void setGroup_id(int group_id) {
		this.groupID = group_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
