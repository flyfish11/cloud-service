package com.cloud.appmanage.controller;

import com.cloud.appmanage.exception.AppManageException;
import com.cloud.appmanage.feign.DeptClient;
import com.cloud.appmanage.feign.SysDictClient;
import com.cloud.appmanage.service.ApplicationInfoService;
import com.cloud.appmanage.vo.ApplicationInfoVo;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.utils.*;
import com.cloud.common.vo.ResultVO;
import com.cloud.model.appmanage.ApplicationInfo;
import com.cloud.model.common.Page;
import com.cloud.model.common.Result;
import com.cloud.model.user.LoginAppUser;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * 应用控制器
 *
 * @author yulj
 * @create: 2019/05/07 15:22
 */

@RestController
@Slf4j
@RequestMapping("/application")
public class ApplicationInfoController {

    @Autowired
    private ApplicationInfoService applicationInfoService;

    @Autowired
    private SysDictClient sysDictClient;

    @Autowired
    private DeptClient deptClient;

    @ApiOperation(value = "查询运用列表数据", notes = "根据关键词查询运用列表数据")
    @ApiImplicitParam(name = "params", value = "查询条件params", required = false, dataType = "Map<String, Object>", paramType = "query")
    @GetMapping("/list")
    public Result list(@RequestParam Map<String, Object> params) {

        Page<ApplicationInfo> applicationInfoPage = this.applicationInfoService.list(params);
        return ResultUtil.success(applicationInfoPage.getTotal(), applicationInfoPage.getData());
    }

    /**
     * 应用创建
     *
     * @param applicationInfo
     * @return
     */
    @ApiOperation(value = "创建运用", notes = "创建运用实体")
    @ApiImplicitParam(name = "applicationInfo", value = "运用实体ApplicationInfo", required = true, dataType = "ApplicationInfo", paramType = "query")
    @RequestMapping("/add")
    public ResultVO<Map<String, String>> add(ApplicationInfo applicationInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【应用创建】参数不正确, applicationInfo={}", applicationInfo);
            throw new AppManageException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        applicationInfo.setId(UUIDUtils.getUUID());
        applicationInfo.setRunState(0);
        applicationInfo.setCreateState(1);
        applicationInfo.setCreateBy(loginAppUser.getFullName());
        applicationInfo.setCreateTime(new Date());
        applicationInfo.setIsDelete(0);
        Map<String, String> map = Maps.newHashMap();
        map.put("applicationId", applicationInfo.getId());
        this.applicationInfoService.add(applicationInfo);
        return ResultVOUtil.success(map);
    }

    /**
     * 根据项目id加载项目信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "查询项目信息", notes = "根据项目id加载项目信息")
    @ApiImplicitParam(name = "id", value = "项目id", required = true, dataType = "String", paramType = "query")
    @PostMapping("/load")
    public Result load(@RequestParam String id) {
        ApplicationInfo applicationInfo = this.applicationInfoService.load(id);
        if (applicationInfo == null) {
            return ResultUtil.error(1, "未查询到项目信息！");
        }
        ApplicationInfoVo applicationInfoVo = new ApplicationInfoVo();
        BeanUtils.copyProperties(applicationInfo, applicationInfoVo);
        applicationInfoVo.setBelongDepartmentName(this.deptClient.load(Integer.valueOf(applicationInfo.getBelongDepartment())).getSdeptname());
        applicationInfoVo.setAuthorityAreaName(this.deptClient.queryDeptNames(applicationInfo.getAuthorityArea()));
        return ResultUtil.success(applicationInfoVo);
    }

    /**
     * 项目修改
     *
     * @param applicationInfo
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "修改项目信息", notes = "根据项目实体修改项目信息")
    @ApiImplicitParam(name = "applicationInfo", value = "项目实体ApplicationInfo", required = true, dataType = "ApplicationInfo", paramType = "query")
    @PostMapping("/edit")
    public ResultVO<Map<String, String>> edit(ApplicationInfo applicationInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【项目修改】参数不正确, applicationInfo={}", applicationInfo);
            throw new AppManageException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        applicationInfo.setUpdateBy(loginAppUser.getFullName());
        applicationInfo.setUpdateTime(new Date());
        this.applicationInfoService.update(applicationInfo);
        Map<String, String> map = Maps.newHashMap();
        map.put("applicationInfoId", applicationInfo.getId());
        return ResultVOUtil.success(map);
    }

    /**
     * 根据应用id删除应用信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除应用信息", notes = "根据应用id删除应用信息")
    @ApiImplicitParam(name = "id", value = "应用id", required = true, dataType = "String", paramType = "path")
    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable String id) {
        if (ValidateUtil.isEmpty(id)) {
            return ResultUtil.error(1, ResultEnum.PARAM_ERROR.getMessage());
        }
        this.applicationInfoService.delete(id);
        return ResultUtil.success();
    }

    /**
     * 加载字典数据信息
     *
     * @param code
     * @return
     */
    @ApiOperation(value = "加载字典数据信息", notes = "根据code加载字典数据信息")
    @ApiImplicitParam(name = "code", value = "应用id", required = true, dataType = "String", paramType = "path")
    @GetMapping("/loadDictData/{code}")
    public Result loadApplicationGroup(@PathVariable String code) {
        Map<String, Object> stringObjectMap = this.sysDictClient.queryDictData(code);
        if (stringObjectMap == null) {
            return ResultUtil.error(1, "未查询到字典信息！");
        }
        return ResultUtil.success(stringObjectMap);
    }
}
