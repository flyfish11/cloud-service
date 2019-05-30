package com.cloud.backend.service;

import com.cloud.backend.model.SysDict;
import com.cloud.model.common.Page;

import java.util.List;
import java.util.Map;

public interface SysDictService {
    void saveEntity(SysDict sysDict);

    //删除字典
    void deleteEntity(int id);

    void updateEntity(SysDict sysDict);

    //查询字典
    SysDict dictionarieEntity(int id);

    //查询字典记录
    List<SysDict> byPidDictionarieEntity(int id);

    Page<SysDict> list(Map<String, Object> params);

    SysDict selectEntity(String name, String code);

    /**
     * 根据code查询字典数据
     *
     * @param code
     * @return
     */
    Map<String, Object> queryDictData(String code);
}
