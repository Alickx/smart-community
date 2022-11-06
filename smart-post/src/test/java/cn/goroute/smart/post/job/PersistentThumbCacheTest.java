package cn.goroute.smart.post.job;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: Alickx
 * @Date: 2022/11/06/17:53
 * @Description:
 */
@SpringBootTest
class PersistentThumbCacheTest {

	@Autowired
	private PersistentThumbCache persistentThumbCache;


	@Test
	void persistentPostThumbCache() {

		persistentThumbCache.persistentPostThumbCache();
	}


	@Test
	void persistentCommentThumbCache() {
		persistentThumbCache.persistentCommentThumbCache();
	}

	@Test
	void persistentReplyThumbCache() {
		persistentThumbCache.persistentReplyThumbCache();
	}
}