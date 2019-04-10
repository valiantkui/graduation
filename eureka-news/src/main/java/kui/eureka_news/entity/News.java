package kui.eureka_news.entity;

public class News {

	private int n_no;
	private String title;
	private String author;
	private String news_text;
	private String img_url;
	private String type;
	private String origin;
	private String origin_url;
	private String publish_date;
	private int page_view;
	public int getN_no() {
		return n_no;
	}
	public void setN_no(int n_no) {
		this.n_no = n_no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getNews_text() {
		return news_text;
	}
	public void setNews_text(String news_text) {
		this.news_text = news_text;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getOrigin_url() {
		return origin_url;
	}
	public void setOrigin_url(String origin_url) {
		this.origin_url = origin_url;
	}
	public String getPublish_date() {
		return publish_date;
	}
	public void setPublish_date(String publish_date) {
		this.publish_date = publish_date;
	}
	public int getPage_view() {
		return page_view;
	}
	public void setPage_view(int page_view) {
		this.page_view = page_view;
	}
	@Override
	public String toString() {
		return "News [n_no=" + n_no + ", title=" + title + ", author=" + author + ", news_text=" + news_text
				+ ", img_url=" + img_url + ", type=" + type + ", origin=" + origin + ", origin_url=" + origin_url
				+ ", publish_date=" + publish_date + ", page_view=" + page_view + "]";
	}
	
	
	
}
