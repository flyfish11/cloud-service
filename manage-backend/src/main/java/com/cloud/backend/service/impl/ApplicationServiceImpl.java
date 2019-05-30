package com.cloud.backend.service.impl;

import com.cloud.backend.dao.ApplicationDao;
import com.cloud.backend.model.Application;
import com.cloud.backend.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("applicationService")
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationDao applicationDao;
    @Override
    public List<Application> findApplication(Application application) {
        return this.applicationDao.selectApplication(application);
    }
}
