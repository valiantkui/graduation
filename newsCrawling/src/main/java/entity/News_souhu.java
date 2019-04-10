package entity;

public class News_souhu {

	private String authorId;
	private String authorName;
	private String id;
	private String title;
	private String picUrl;
	private String publicTime;
	private String originalSource;
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getPublicTime() {
		return publicTime;
	}
	public void setPublicTime(String publicTime) {
		this.publicTime = publicTime;
	}
	public String getOriginalSource() {
		return originalSource;
	}
	public void setOriginalSource(String originalSource) {
		this.originalSource = originalSource;
	}
	@Override
	public String toString() {
		return "News_souhu [authorId=" + authorId + ", authorName=" + authorName + ", id=" + id + ", title=" + title
				+ ", picUrl=" + picUrl + ", publicTime=" + publicTime + ", originalSource=" + originalSource + "]";
	}
	
	
	

	
	
}
