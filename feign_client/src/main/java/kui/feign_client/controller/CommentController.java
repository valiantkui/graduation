package kui.feign_client.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kui.feign_client.entity.Comment;
import kui.feign_client.entity.User;
import kui.feign_client.entity.extro.CommentInfo;
import kui.feign_client.service.CommentService;
import kui.feign_client.service.UserService;

@Controller
@RequestMapping("comment")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/publishComment")
	@ResponseBody
	public boolean publishComment(@RequestParam("n_no") int n_no,@RequestParam("u_id") String u_id,@RequestParam("comment_text") String comment_text) {
		return commentService.publishComment(n_no, u_id, comment_text);
	}
	
	@RequestMapping("/findCommentInfoByN_no")
	@ResponseBody
	public List<CommentInfo> findCommentInfoByN_no(@RequestParam("n_no") int n_no){
		System.out.println("commentList之前");
		List<Comment> commentList = commentService.findCommentByN_no(n_no);
		System.out.println("commentList:"+commentList);
		
		if(commentList == null || commentList.size() == 0) return null;
		
		HashSet<String> u_idSet = new HashSet<>();
		for(Comment c: commentList) {
			u_idSet.add(c.getU_id());
		}
		
		List<User> userList = userService.findUserByU_idList(new ArrayList<>(u_idSet));
		
		HashMap<String,User> userMap = new HashMap<>();
		
		for(User u: userList) {
			userMap.put(u.getU_id(), u);
		}
		
		List<CommentInfo> commentInfoList = new ArrayList<>();
		
		for(int i = 0;i<commentList.size();i++) {
			Comment comment = commentList.get(i);
			
			CommentInfo commentInfo = new CommentInfo();
			
			commentInfo.setComment(comment);
			commentInfo.setUser(userMap.get(comment.getU_id()));
			
			
			commentInfoList.add(commentInfo);
		}
		
		//TODO
		return commentInfoList;
	}
	
}
