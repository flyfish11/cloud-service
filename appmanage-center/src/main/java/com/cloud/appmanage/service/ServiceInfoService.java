package com.cloud.appmanage.service;

import com.cloud.model.appmanage.ApplicationInfo;
import com.cloud.model.appmanage.ServiceInfo;
import com.cloud.model.common.Page;

import java.util.Map;

/**
 * 服务数据接口
 *
 * @author yulj
 * @create: 2019/05/09 13:34
 */
public interface ServiceInfoService {

    /**
     * 查询服务列表数据
     *
     * @param params
     * @return
     */
    Page<ServiceInfo> list(Map<String, Object> params);

    /**
     * 新增服务信息
     *
     * @param serviceInfo
     * @return
     */
    ServiceInfo add(ServiceInfo serviceInfo);

    /**
     * 新增服务信息
     *
     * @param serviceInfo
     * @return
     */
    void update(ServiceInfo serviceInfo);


    /**
     * 应用接口层
     *
     * @author yulj
     * @create: 2019/05/07 15:26
     */
    interface ApplicationInfoService {

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
         * 根据项目id查询用户信息
         *
         * @param id
         * @return
         */
        ApplicationInfo load(String id);


    }

    /**
     * 通过id查询单条数据
     *
     * @param id
     * @return
     */
    ServiceInfo findById(String id);

    /**
     * 通过id更新jenkinlogs
     *
     * @param serviceInfo
     * @return
     */
    int updateById(ServiceInfo serviceInfo);

    /**
     * 根据服务id删除服务
     *
     * @param id
     */
    void delete(String id);
}
