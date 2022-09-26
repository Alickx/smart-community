package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.post.entity.dto.PostDTO;
import cn.goroute.smart.post.entity.qo.PostQO;
import cn.goroute.smart.post.service.PostService;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/09/26/15:58
 * @Description:
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PostServiceImplTest {

	@Autowired
	private PostServiceImpl postService;

	@Test
	void infoPage() {
		PageParam pageParam = new PageParam();
		pageParam.setPage(1);
		pageParam.setSize(2);
		PageResult<PostDTO> postDTOPageResult =
				postService.infoPage(pageParam, new PostQO());
		System.out.println(postDTOPageResult);
	}

	@Test
	void info(){
		Long postId = 112L;
		PostDTO postDTO = postService.info(postId);
		System.out.println(postDTO);
	}
}