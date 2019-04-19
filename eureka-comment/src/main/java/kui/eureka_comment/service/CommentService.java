package kui.eureka_comment.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kui.eureka_comment.dao.CommentDao;
import kui.eureka_comment.entity.Comment;
import kui.eureka_comment.entity.extro.CommentInfo;

@Service
public class CommentService {

	
	@Autowired
	private CommentDao commentDao;
	
	public boolean publishComment(int n_no, String u_id, String comment_text) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String comment_date = sdf.format(new Date());
		
		Comment comment = new Comment();
		comment.setN_no(n_no);
		comment.setU_id(u_id);
		comment.setComment_text(comment_text);
		comment.setComment_date(comment_date);
		if(commentDao.insertComment(comment)>0) return true;
		return false;
	}
	
	
	public List<Comment> findCommentByN_no(int n_no){
		return commentDao.findCommentByN_no(n_no);
	}

}
