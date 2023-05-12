package cn.goroute.smart.post.modules.article.service.impl;

import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.post.domain.vo.PostAbbreviationVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author: Alickx
 * @Date: 2023/04/16/0:37
 * @Description:
 */
@SpringBootTest
class PostServiceImplTest {


	@Resource
	private PostServiceImpl postService;

	@Test
	void infoPage() {

		PageParam pageParam = new PageParam();
		pageParam.setPage(1);
		pageParam.setSize(20);
		PageResult<PostAbbreviationVO> postAbbreviationVOPageResult =
			postService.infoPage(pageParam, null);
		Assertions.assertNotNull(postAbbreviationVOPageResult);
		System.out.println(postAbbreviationVOPageResult);


	}
}