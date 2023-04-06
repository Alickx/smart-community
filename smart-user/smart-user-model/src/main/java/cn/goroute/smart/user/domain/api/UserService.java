package cn.goroute.smart.user.domain.api;

import cn.goroute.smart.common.config.FeignConfig;
import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.user.domain.entity.UserProfileEntity;
import cn.goroute.smart.user.domain.form.UserProfileQueryForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2023/03/25/15:33
 * @Description:
 */
@FeignClient(name = "smart-user", configuration = FeignConfig.class)
public interface UserService {


	@GetMapping("/user/admin/page")
	R<PageResult<UserProfileEntity>> page(@RequestParam("pageParam") PageParam pageParam, @RequestParam("form") UserProfileQueryForm form);

	@PostMapping("/user/admin/save")
	R<Boolean> save(@RequestBody UserProfileEntity userProfileEntity);

	@PostMapping("/user/admin/update")
	R<Boolean> update(@RequestBody UserProfileEntity userProfileEntity);

	@PostMapping("/user/admin/delete")
	R<Boolean> delete(@RequestBody UserProfileEntity userProfileEntity);

	@PostMapping("/user/admin/batch/delete")
	R<Boolean> batchDelete(@RequestBody List<Long> ids);




}
