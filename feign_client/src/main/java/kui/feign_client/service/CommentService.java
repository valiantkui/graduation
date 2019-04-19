package kui.feign_client.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kui.feign_client.entity.Comment;

@Service
@FeignClient("service-comment")
public interface CommentService {

	@RequestMapping("comment/publishComment")
	public boolean publishComment(
			@RequestParam("n_no") int n_no,
			@RequestParam("u_id") String u_id,
			@RequestParam("comment_text") String comment_text);

	@RequestMapping("comment/findCommentByN_no")
	public List<Comment> findCommentByN_no(@RequestParam("n_no") int n_no);
}
