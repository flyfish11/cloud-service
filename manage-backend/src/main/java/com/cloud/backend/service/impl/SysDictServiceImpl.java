package com.cloud.backend.service.impl;

import com.cloud.backend.dao.SysDictDao;
import com.cloud.backend.model.SysDict;
import com.cloud.backend.service.SysDictService;
import com.cloud.common.utils.PageUtil;
import com.cloud.model.common.Page;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SysDictServiceImpl implements SysDictService {

    @Autowired
    private SysDictDao sysDictDao;

    @Override
    public void saveEntity(SysDict sysDict) {
        sysDictDao.saveEntity(sysDict);
        log.info("添字典Dictionaries:{}", sysDict);
    }

    @Override
    public SysDict selectEntity(String name, String code) {
        return sysDictDao.selectEntity(name, code);
    }

    @Override
    public void deleteEntity(int id) {
        //删除字典
        int num = sysDictDao.deleteEntity(id);
        int n = sysDictDao.byPidDeleteEntity(id);
        if (num > 0) {
            log.info("删除典Dictionaries:{}", id);
        }
    }


    @Override
    public void updateEntity(SysDict dictionarie) {
        SysDict dictionarie1 = sysDictDao.dictionarieEntity(dictionarie.getId());
        if (dictionarie1 == null) {
            throw new IllegalArgumentException("该字典不存在");
        }
        int num = sysDictDao.updateEntity(dictionarie);
        if (num > 0) {
            log.info("修改字典Dictionaries:{}", dictionarie);
        }

    }

    @Override
    public SysDict dictionarieEntity(int id) {
        return sysDictDao.dictionarieEntity(id);
    }

    @Override
    public List<SysDict> byPidDictionarieEntity(int id) {
        return sysDictDao.byPidDictionarieEntity(id);
    }

    @Override
    public Page<SysDict> list(Map<String, Object> params) {
        int total = sysDictDao.count(params);
        List<SysDict> list = Collections.emptyList();
        if (total > 0) {
            PageUtil.pageParamConver(params, false);
            list = sysDictDao.list(params);
        }
        return new Page<>(total, list);
    }

    @Override
    public Map<String, Object> queryDictData(String code) {
        SysDict sysDict = this.sysDictDao.selectByCode(code);
        Map<String, Object> map = Maps.newHashMap();
        if (sysDict != null) {
            List<SysDict> sysDicts = this.sysDictDao.queryLists(sysDict.getId());
            for (SysDict dict : sysDicts) {
                map.put(String.valueOf(dict.getId()), dict.getName());
            }
        }
        return map;
    }
}
