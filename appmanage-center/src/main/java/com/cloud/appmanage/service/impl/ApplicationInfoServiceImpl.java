package com.cloud.appmanage.service.impl;

import com.cloud.appmanage.config.GitlabConfiguration;
import com.cloud.appmanage.dao.ApplicationInfoDao;
import com.cloud.appmanage.dao.ServiceInfoDao;
import com.cloud.appmanage.exception.AppManageException;
import com.cloud.appmanage.service.ApplicationInfoService;
import com.cloud.appmanage.util.HttpRequestUtil;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.utils.PageUtil;
import com.cloud.model.appmanage.ApplicationInfo;
import com.cloud.model.common.Page;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 应用接口服务层
 *
 * @author yulj
 * @create: 2019/05/07 15:29
 */
@Service
@Slf4j
public class ApplicationInfoServiceImpl implements ApplicationInfoService {

    @Autowired
    private ApplicationInfoDao applicationInfoDao;

    @Autowired
    private GitlabConfiguration gitlabConfiguration;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ServiceInfoDao serviceInfoDao;

    @Override
    public Page<ApplicationInfo> list(Map<String, Object> params) {
        params.put("isDelete", 0);
        int total = this.applicationInfoDao.count(params);

        PageUtil.pageUtil(params);
        List<ApplicationInfo> list = Collections.emptyList();
        if (total > 0) {
            PageUtil.pageParamConver(params, true);
            list = this.applicationInfoDao.findList(params);
        }
        return new Page<>(total, list);
    }

    @Override
    public ApplicationInfo add(ApplicationInfo applicationInfo) {
        //判断项目名称是否重复（英文名称）
        List<ApplicationInfo> applicationInfoList = applicationInfoDao.selectByNameAndBelongDepartment(applicationInfo.getName(), applicationInfo.getBelongDepartment(), 0);
        if (applicationInfoList.size() > 0) {
            throw new AppManageException(ResultEnum.SERVICE_ALREDY_EXISTS);
        }

        //创建项目的远程git仓库
        Map<String, Object> dataMap = Maps.newHashMap();
        if (applicationInfo.getType().equals("gitlab")) {
            dataMap.put("name", applicationInfo.getName());
            HttpEntity<String> requestEntity = HttpRequestUtil.getRequestEntity(dataMap);
            String url = this.gitlabConfiguration.getApiPrefix() + "/api/v4/projects?private_token=" + this.gitlabConfiguration.getPrivateToken();
            //发送POST请求删除企业微信应用
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                JsonElement tmpJsonElement = (new JsonParser()).parse(response.getBody());
                String projectRepoUrl = tmpJsonElement.getAsJsonObject().get("http_url_to_repo").getAsString();
                projectRepoUrl = projectRepoUrl.replace(this.gitlabConfiguration.getDomain(), this.gitlabConfiguration.getIp());
                applicationInfo.setProjectRepoUrl(projectRepoUrl);
            } else {
                throw new AppManageException(ResultEnum.CREATE_REPO_ERROR);
            }
        }

        this.applicationInfoDao.insert(applicationInfo);

        return applicationInfo;
    }

    @Override
    public ApplicationInfo load(String id) {
        return this.applicationInfoDao.selectByPrimaryKey(id);
    }

    @Override
    public void delete(String id) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("isDelete", 0);
        params.put("belongApplication", id);
        Integer count = this.serviceInfoDao.count(params);
        if (count > 0) {
            throw new AppManageException(ResultEnum.CANT_DELETE_ERROR);
        }
        ApplicationInfo applicationInfo = this.load(id);
        applicationInfo.setIsDelete(1);
        this.updateApplicationInfo(applicationInfo);
    }

    @Override
    public Integer updateApplicationInfo(ApplicationInfo applicationInfo) {
        return this.applicationInfoDao.updateById(applicationInfo);
    }

    @Override
    public void update(ApplicationInfo applicationInfo) {
        this.applicationInfoDao.updateById(applicationInfo);
    }
}
