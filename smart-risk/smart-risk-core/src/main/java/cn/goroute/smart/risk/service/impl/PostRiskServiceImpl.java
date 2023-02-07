package cn.goroute.smart.risk.service.impl;

import cn.goroute.smart.post.constant.PostStateEnum;
import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.risk.feign.FeignPostService;
import cn.goroute.smart.risk.feign.FeignUserService;
import cn.goroute.smart.risk.mq.PostRiskMessageTemplate;
import cn.goroute.smart.risk.service.PostRiskService;
import cn.goroute.smart.user.domain.UserProfile;
import com.hccake.ballcat.common.model.result.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: Alickx
 * @Date: 2023/02/04 22:47:43
 * @Description: 文章风控服务
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class PostRiskServiceImpl implements PostRiskService {

	private final FeignPostService feignPostService;

	private final FeignUserService feignUserService;

	private final PostRiskMessageTemplate postRiskMessageTemplate;

	@Override
	public void checkPostRisk(Post post) {

		log.info("开始处理文章风控检查，文章id：[{}]", post.getId());

		R<Post> postR = feignPostService.getById(post.getId());

		R<UserProfile> userProfileR = feignUserService.queryUserProfile(post.getAuthorId());

		// TODO 检查用户信息

		// TODO 检查文章内容

		Post postData = postR.getData();

		postData.setState(PostStateEnum.NORMAL.getCode());

		postRiskMessageTemplate.sendPostMessage(postData);

	}
}
