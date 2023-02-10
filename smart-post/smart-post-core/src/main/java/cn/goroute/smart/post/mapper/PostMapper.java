package cn.goroute.smart.post.mapper;

import cn.goroute.smart.common.constant.CommonConstant;
import cn.goroute.smart.post.converter.PostConverter;
import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.post.model.dto.PostAbbreviationDTO;
import cn.goroute.smart.post.model.qo.PostQO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.extend.mybatis.plus.conditions.query.LambdaQueryWrapperX;
import com.hccake.extend.mybatis.plus.mapper.ExtendMapper;
import com.hccake.extend.mybatis.plus.toolkit.WrappersX;
import org.apache.ibatis.annotations.Param;

/**
 * @author Alickx
 * @description 针对表【post(文章表)】的数据库操作Mapper
 * @createDate 2022-09-25 16:53:24
 * @Entity cn.goroute.smart.post.domain.Post
 */
public interface PostMapper extends ExtendMapper<Post> {

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param postQO 查询参数对象
	 * @return PageResult<PostInfoDTO> 分页数据
	 */
	default PageResult<PostAbbreviationDTO> queryPage(PageParam pageParam, PostQO postQO) {
		IPage<Post> page = this.prodPage(pageParam);
		LambdaQueryWrapperX<Post> wrapperX = WrappersX.lambdaQueryX(Post.class)
				.eqIfPresent(Post::getCategoryId, postQO.getCategoryId())
				.eqIfPresent(Post::getAuthorId, postQO.getUserId())
				.eq(Post::getState, CommonConstant.NORMAL_STATE);
		IPage<Post> postIPage = this.selectPage(page, wrapperX);
		IPage<PostAbbreviationDTO> dtoPage = postIPage.convert(PostConverter.INSTANCE::poToAbbreviationDto);
		return new PageResult<>(dtoPage.getRecords(), dtoPage.getTotal());

	}

	/**
	 * 增加文章点赞数
	 * @param toId 文章id
	 * @param thumbNum 点赞数
	 */
    void incrThumbNum(@Param("toId") long toId,@Param("thumbNum") int thumbNum);

	/**
	 * 递减文章点赞数
	 * @param toId 文章id
	 * @param thumbNum 点赞数
	 */
	void descThumbNum(@Param("toId") long toId,@Param("thumbNum") int thumbNum);

    void incrCommentCount(@Param("postId") Long postId);
}




