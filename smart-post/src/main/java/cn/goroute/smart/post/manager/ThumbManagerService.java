package cn.goroute.smart.post.manager;

import cn.goroute.smart.post.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/10/22/10:14
 * @Description: 文章点赞
 */
@Service
@RequiredArgsConstructor
public class ThumbManagerService {

	private final PostMapper postMapper;

}
