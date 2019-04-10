package entity.sina;

public class Data {

	private String id;
	private String img;
	private String keywords;
	private int level;
	private String media_name;
	private int old_level;
	private String title;
	private String url;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getMedia_name() {
		return media_name;
	}
	public void setMedia_name(String media_name) {
		this.media_name = media_name;
	}
	public int getOld_level() {
		return old_level;
	}
	public void setOld_level(int old_level) {
		this.old_level = old_level;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Data [id=" + id + ", img=" + img + ", keywords=" + keywords + ", level=" + level + ", media_name="
				+ media_name + ", old_level=" + old_level + ", title=" + title + ", url=" + url + "]";
	}
	
	
}
