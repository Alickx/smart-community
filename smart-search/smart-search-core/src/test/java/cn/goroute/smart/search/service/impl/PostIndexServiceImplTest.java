package cn.goroute.smart.search.service.impl;

import cn.goroute.smart.post.domain.Post;
import com.hccake.ballcat.common.model.result.R;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * @Author: Alickx
 * @Date: 2023/02/01/14:40
 * @Description:
 */
@SpringBootTest
class PostIndexServiceImplTest {

	@Autowired
	private PostIndexServiceImpl postService;

	@Test
	void save() {

		Post post = new Post();
		post.setId(1610538523299721218L);
		post.setCategoryId(122L);
		post.setTagId(1110L);
		post.setAuthorId(1571562360907292673L);
		post.setTitle("这是测试文章");
		post.setContent("这是测试内容");
		post.setState(0);
		post.setCollectCount(0);
		post.setThumbCount(0);
		post.setCommentCount(0);
		post.setIsPublish(true);
		post.setIp("110.12.232.123");
		post.setUpdateTime(LocalDateTime.now());
		post.setCreateTime(LocalDateTime.now());
		post.setDeleted(0);

		R<Boolean> save = postService.save(post);
		System.out.println(save);

	}
}