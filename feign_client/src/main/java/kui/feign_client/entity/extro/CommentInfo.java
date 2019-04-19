package kui.feign_client.entity.extro;

import kui.feign_client.entity.Comment;
import kui.feign_client.entity.User;

public class CommentInfo {
	private User user;
	private Comment comment;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "CommentInfo [user=" + user + ", comment=" + comment + "]";
	}
	
	
	
}
