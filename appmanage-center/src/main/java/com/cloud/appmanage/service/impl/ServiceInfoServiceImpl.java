package com.cloud.appmanage.service.impl;

import com.cloud.appmanage.dao.ApplicationInfoDao;
import com.cloud.appmanage.dao.ServiceInfoDao;
import com.cloud.appmanage.exception.AppManageException;
import com.cloud.appmanage.service.ServiceInfoService;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.utils.PageUtil;
import com.cloud.model.appmanage.ApplicationInfo;
import com.cloud.model.appmanage.ServiceInfo;
import com.cloud.model.common.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 服务数据接口
 *
 * @author yulj
 * @create: 2019/05/09 13:35
 */

@Service
public class ServiceInfoServiceImpl implements ServiceInfoService {

    @Autowired
    private ServiceInfoDao serviceInfoDao;

    @Autowired
    private ApplicationInfoDao applicationInfoDao;


    @Override
    public Page<ServiceInfo> list(Map<String, Object> params) {
        params.put("isDelete", 0);
        int total = serviceInfoDao.count(params);

        PageUtil.pageUtil(params);
        List<ServiceInfo> list = Collections.emptyList();
        if (total > 0) {
            PageUtil.pageParamConver(params, true);
            list = serviceInfoDao.findList(params);
        }
        return new Page<>(total, list);
    }

    @Override
    public ServiceInfo add(ServiceInfo serviceInfo) {
        //判断服务名称是否重复
        List<ServiceInfo> serviceInfoList = this.serviceInfoDao.selectByName(serviceInfo.getName(), 0);
        if (serviceInfoList.size() > 0) {
            throw new AppManageException(ResultEnum.PROJECT_ALREDY_EXISTS);
        }

        ApplicationInfo applicationInfo = this.applicationInfoDao.selectByPrimaryKey(serviceInfo.getBelongApplication());
        serviceInfo.setType(applicationInfo.getType());
        this.serviceInfoDao.insert(serviceInfo);
        return serviceInfo;
    }

    @Override
    public void update(ServiceInfo serviceInfo) {
        serviceInfoDao.update(serviceInfo);
    }


    @Override
    public ServiceInfo findById(String id) {
        ServiceInfo serviceInfo = this.serviceInfoDao.findById(id);
        return serviceInfo;
    }

    @Override
    public int updateById(ServiceInfo serviceInfo) {
        int index = this.serviceInfoDao.updateById(serviceInfo, serviceInfo.getId());
        return index;
    }

    @Override
    public void delete(String id) {
        ServiceInfo serviceInfo = this.findById(id);
        //逻辑删除
        serviceInfo.setIsDelete(1);
        this.updateById(serviceInfo);
    }
}
