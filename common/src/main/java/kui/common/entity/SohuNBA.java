package kui.common.entity;

import java.util.List;

public class SohuNBA {

	private String id;
	private List<String> images;//集合的第一个元素对应数据库的img_url
	private String title;
	
	/**
	 * 对应
	 */
	private String url;//对应数据的origin_url
	private String authorName;
	private String publicTime;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
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
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getPublicTime() {
		return publicTime;
	}
	public void setPublicTime(String publicTime) {
		this.publicTime = publicTime;
	}
	@Override
	public String toString() {
		return "SohuNBA [id=" + id + ", images=" + images + ", title=" + title + ", url=" + url + ", authorName="
				+ authorName + ", publicTime=" + publicTime + "]";
	}
	
	
}
