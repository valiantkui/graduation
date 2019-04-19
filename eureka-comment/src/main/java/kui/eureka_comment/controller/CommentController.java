package kui.eureka_comment.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kui.eureka_comment.entity.Comment;
import kui.eureka_comment.service.CommentService;

@Controller
@RequestMapping("comment")
public class CommentController {

	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping("/publishComment")
	@ResponseBody
	public boolean publishComment(int n_no,String u_id,String comment_text) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String comment_date = sdf.format(new Date());
		return commentService.publishComment(n_no, u_id, comment_text);
	}
	
	
	@RequestMapping(value="/findCommentByN_no",produces="application/json")
	@ResponseBody
	public List<Comment> findCommentByN_no(@RequestParam("n_no") int n_no){
		List<Comment> commentList = commentService.findCommentByN_no(n_no);
		
		System.out.println(commentList);
		
		return commentList;
	}
}
