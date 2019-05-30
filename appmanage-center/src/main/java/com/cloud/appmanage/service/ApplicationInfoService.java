package com.cloud.appmanage.service;

import com.cloud.model.appmanage.ApplicationInfo;
import com.cloud.model.common.Page;

import java.util.Map;

/**
 * 应用接口层
 *
 * @author yulj
 * @create: 2019/05/07 15:26
 */
public interface ApplicationInfoService {

    /**
     * 查询应用列表数据
     *
     * @param params
     * @return
     */
    Page<ApplicationInfo> list(Map<String, Object> params);

    /**
     * 新增应用信息
     *
     * @param applicationInfo
     * @return
     */
    ApplicationInfo add(ApplicationInfo applicationInfo);

    /**
     * 根据项目id查询项目信息
     *
     * @param id
     * @return
     */
    ApplicationInfo load(String id);

    /**
     * 根据项目id删除项目信息（逻辑删除）
     *
     * @param id
     */
    void delete(String id);

    /**
     * 更新项目信息
     *
     * @param applicationInfo
     * @return
     */
    Integer updateApplicationInfo(ApplicationInfo applicationInfo);

    /**
     * 修改项目信息
     *
     * @param applicationInfo
     */
    void update(ApplicationInfo applicationInfo);
}

