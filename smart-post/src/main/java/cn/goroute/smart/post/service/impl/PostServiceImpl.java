package cn.goroute.smart.post.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.constant.CommonConstant;
import cn.goroute.smart.common.constant.ErrorCodeEnum;
import cn.goroute.smart.post.converter.PostConverter;
import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.post.entity.dto.PostAbbreviationDTO;
import cn.goroute.smart.post.entity.dto.PostBaseDTO;
import cn.goroute.smart.post.entity.dto.PostInfoDTO;
import cn.goroute.smart.post.entity.qo.PostQO;
import cn.goroute.smart.post.entity.vo.PostVO;
import cn.goroute.smart.post.manager.PostManagerService;
import cn.goroute.smart.post.mapper.PostMapper;
import cn.goroute.smart.post.service.PostService;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.ballcat.common.util.IpUtils;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import lombok.RequiredArgsConstructor;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import net.dreamlu.mica.ip2region.core.IpInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author Alickx
* @description 针对表【post(文章表)】的数据库操作Service实现
* @createDate 2022-09-25 16:53:24
*/
@Service
@RequiredArgsConstructor
public class PostServiceImpl extends ExtendServiceImpl<PostMapper, Post>
    implements PostService{

	private final PostManagerService postManagerService;

	private final Ip2regionSearcher ip2regionSearcher;


	/**
	 * 文章详情 - 分页查询
	 * @param pageParam 分页参数
	 * @param postQO 查询参数对象
	 * @return 查询结果
	 */
	@Override
	public R<PageResult<PostAbbreviationDTO>> infoPage(PageParam pageParam, PostQO postQO) {
		PageResult<PostAbbreviationDTO> postPage = baseMapper.queryPage(pageParam, postQO);
		List<PostAbbreviationDTO> records = postPage.getRecords();

		if (CollUtil.isEmpty(records)) {
			return R.ok(postPage);
		}

		// 补充文章作者，板块等信息
		List<? extends PostBaseDTO> postDTOList =postManagerService.fillInfo(records);
		postPage.setRecords((List<PostAbbreviationDTO>) postDTOList);

		return R.ok(postPage);

	}

	/**
	 * 根据文章Id查询文章详情
	 *
	 * @param postId 文章Id
	 * @return 文章详情
	 */
	@Override
	public R<PostInfoDTO> info(Long postId) {


		LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Post::getId, postId);
		wrapper.notIn(Post::getDeleted, CommonConstant.DELETE_STATE);
		Post post = baseMapper.selectOne(wrapper);

		if (post == null) {
			return R.failed(ErrorCodeEnum.POST_NOT_EXIST);
		}

		PostInfoDTO postInfoDTO = PostConverter.INSTANCE.poToDto(post);
		IpInfo ipInfo = ip2regionSearcher.memorySearch(post.getIp());

		if (ipInfo != null) {
			postInfoDTO.setRegion(ipInfo.getProvince());
		}

		List<? extends PostBaseDTO> postBaseDTOS =
				postManagerService.fillInfo(Lists.asList(postInfoDTO, new PostInfoDTO[0]));

		return R.ok((PostInfoDTO) postBaseDTOS.get(0));
	}

	/**
	 * 保存文章
	 *
	 * @param postVO 文章视图对象
	 * @return 文章Id
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public R<Long> save(PostVO postVO, HttpServletRequest request) {

		Post post = PostConverter.INSTANCE.voToPo(postVO);
		post.setAuthorId(StpUtil.getLoginIdAsLong());
		String ipAddr = IpUtils.getIpAddr(request);
		post.setIp(ipAddr);

		//TODO 待完善 积分增加，文章数增加，风控检查处理等
		postManagerService.savePost2Db(post);
		return R.ok(post.getId());
	}
}




