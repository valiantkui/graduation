package kui.eureka_comment.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kui.eureka_comment.entity.Comment;
import kui.eureka_comment.entity.extro.CommentInfo;

@Repository("commentDao")
public interface CommentDao {

	public int  insertComment(Comment comment);

	public List<Comment> findCommentByN_no(int n_no);
}
