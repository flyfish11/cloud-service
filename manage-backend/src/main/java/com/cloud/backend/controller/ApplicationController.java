package com.cloud.backend.controller;

import com.cloud.backend.model.Application;
import com.cloud.backend.service.ApplicationService;
import com.cloud.common.utils.ResultUtil;
import com.cloud.model.common.Page;
import com.cloud.model.common.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/application")
public class ApplicationController {

	@Autowired
	private ApplicationService applicationService;
	/**
	 * 当前登录用户的菜单
	 * @return
	 */
	@ApiOperation(value = "用户的菜单", notes = "当前登录用户的菜单")
	@ApiImplicitParam(name = "application", value = "查询参数", required = false, dataType = "Application ", paramType = "query")
	@GetMapping("/all")
	@ResponseBody
	public Result findMyMenu(Application application) {

		List<Application> application1 = this.applicationService.findApplication(application);
		Page<Application> result =new Page<>();
		result.setCount(10);
		result.setData(application1);

		return ResultUtil.success(application1);
	}



}
