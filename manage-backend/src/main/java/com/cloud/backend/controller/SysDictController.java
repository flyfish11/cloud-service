package com.cloud.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cloud.backend.model.SysDict;
import com.cloud.backend.service.SysDictService;
import com.cloud.common.utils.ResultUtil;
import com.cloud.model.common.Page;
import com.cloud.model.common.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dictionaries")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    @ApiOperation(value = "添加字典", notes = "新增字典")
    @ApiImplicitParam(name = "allDate", value = "字典实体json字符串", required = false, dataType = "String ", paramType = "query")
    @PostMapping("/saveEntity")
    public void saveEntity(@RequestBody String allDate) {
        SysDict dictionarie = new SysDict();
        int o = allDate.indexOf("{");
        allDate = allDate.substring(o);
        JSONObject jsonObject = new JSONObject(allDate);
        String name = jsonObject.getString("dictName");
        String code = jsonObject.getString("dictCode");
        dictionarie.setName(jsonObject.getString("dictTips"));
        dictionarie.setName(name);
        ;
        dictionarie.setCode(code);
        dictionarie.setNum(0);
        dictionarie.setPid(0);
        sysDictService.saveEntity(dictionarie);
        dictionarie = sysDictService.selectEntity(name, code);
        if (dictionarie != null) {

            String jsonList = jsonObject.getString("list");
            JSONArray list = JSON.parseArray(jsonList);
            dictionarie.setPid(dictionarie.getId());
            for (int i = 0; i < list.size(); i++) {
                dictionarie.setCode(list.getJSONObject(i).getString("code"));
                dictionarie.setName(list.getJSONObject(i).getString("name"));
                dictionarie.setNum(list.getJSONObject(i).getInteger("num"));
                sysDictService.saveEntity(dictionarie);
            }
        }
    }
    @ApiOperation(value = "删除字典", notes = "根据字典id删除字典")
    @ApiImplicitParam(name = "id", value = "字典id", required = true, dataType = "int ", paramType = "path")
    @DeleteMapping("/deleteEntity/{id}")
    public void deleteEntity(@PathVariable int id) {
        sysDictService.deleteEntity(id);
    }
    @ApiOperation(value = "查询字典", notes = "根据字典id查询字典")
    @ApiImplicitParam(name = "id", value = "字典id", required = true, dataType = "int ", paramType = "path")
    @GetMapping("/dictionarieEntity/{id}")
    public SysDict dictionarieEntity(@PathVariable int id) {
        return sysDictService.dictionarieEntity(id);
    }
    @ApiOperation(value = "查询字典", notes = "根据字典id查询子字典列表")
    @ApiImplicitParam(name = "id", value = "字典id", required = true, dataType = "int ", paramType = "path")
    @GetMapping("/byPidDictionarieEntity/{id}")
    public List<SysDict> byPidDictionarieEntity(@PathVariable int id) {
        return sysDictService.byPidDictionarieEntity(id);
    }
    @ApiOperation(value = "更新字典", notes = "根据字典实体json字符串更新字典信息")
    @ApiImplicitParam(name = "addDate", value = "字典实体json字符串", required = true, dataType = "String ", paramType = "query")
    @PostMapping("/updateEntity")
    public void updateEntity(@RequestBody String allDate) {
        SysDict dictionarie = new SysDict();
        int o = allDate.indexOf("{");
        allDate = allDate.substring(o);
        JSONObject jsonObject = new JSONObject(allDate);
        int id = jsonObject.getInt("id");
        sysDictService.deleteEntity(id);
        String name = jsonObject.getString("dictName");
        String code = jsonObject.getString("dictCode");
        dictionarie.setName(jsonObject.getString("dictTips"));
        dictionarie.setName(name);
        ;
        dictionarie.setCode(code);
        dictionarie.setNum(0);
        dictionarie.setPid(0);
        sysDictService.saveEntity(dictionarie);
        dictionarie = sysDictService.selectEntity(name, code);
        if (dictionarie != null) {

            String jsonList = jsonObject.getString("list");
            JSONArray list = JSON.parseArray(jsonList);
            dictionarie.setPid(dictionarie.getId());
            for (int i = 0; i < list.size(); i++) {
                dictionarie.setCode(list.getJSONObject(i).getString("code"));
                dictionarie.setName(list.getJSONObject(i).getString("name"));
                dictionarie.setNum(list.getJSONObject(i).getInteger("num"));
                sysDictService.saveEntity(dictionarie);
            }
        }
    }
    @ApiOperation(value = "查询字典", notes = "根据字典id查询子字典列表")
    @ApiImplicitParam(name = "id", value = "字典id", required = false, dataType = "int ", paramType = "query")
    @GetMapping("/list")
    public Result list(@RequestParam Map<String, Object> params) {
        Page<SysDict> list = sysDictService.list(params);
        return ResultUtil.success(list.getData());
    }

    @GetMapping("/queryDictData/{code}")
    public Map<String, Object> queryDictData(@PathVariable String code) {
        return this.sysDictService.queryDictData(code);
    }
}
