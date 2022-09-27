package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.post.entity.dto.PostDTO;
import cn.goroute.smart.post.entity.qo.PostQO;
import cn.goroute.smart.post.entity.vo.PostVO;
import cn.goroute.smart.post.service.PostService;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.common.model.result.R;
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
		R<PageResult<PostDTO>> pageResultR =
				postService.infoPage(pageParam, new PostQO());
		System.out.println(pageResultR);
	}

	@Test
	void info(){
		Long postId = 112L;
		R<PostDTO> info = postService.info(postId);
		System.out.println(info);
	}

	@Test
	void save(){
		PostVO postVO = new PostVO();
		postVO.setCategoryId(112L);
		postVO.setTagId(113L);
		postVO.setAuthorId(110L);
		postVO.setTitle("测试保存文章");
		postVO.setContent("测试保存文章");
		postVO.setIsPublish(1);
		postVO.setSummary("测试保存文章");
		R<Long> save = postService.save(postVO);
		System.out.println(save.getData());

	}

}