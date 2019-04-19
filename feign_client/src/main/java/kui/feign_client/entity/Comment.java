package kui.feign_client.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Comment {

	private int c_no;
	private int n_no;
	private String u_id;
	private String comment_text;
	
	private String comment_date;
	public int getC_no() {
		return c_no;
	}
	public void setC_no(int c_no) {
		this.c_no = c_no;
	}
	public int getN_no() {
		return n_no;
	}
	public void setN_no(int n_no) {
		this.n_no = n_no;
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getComment_text() {
		return comment_text;
	}
	public void setComment_text(String comment_text) {
		this.comment_text = comment_text;
	}
	public String getComment_date() {
		return comment_date;
	}
	public void setComment_date(String comment_date) {
		this.comment_date = comment_date;
	}
	@Override
	public String toString() {
		return "Comment [c_no=" + c_no + ", n_no=" + n_no + ", u_id=" + u_id + ", comment_text=" + comment_text
				+ ", comment_date=" + comment_date + "]";
	}
	
	
	
}
