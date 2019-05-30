package com.cloud.appmanage.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * 字典管理FeignClient
 *
 * @author yulj
 * @create: 2019/05/21 21:01
 */

@FeignClient("manage-backend")
public interface SysDictClient {

    @GetMapping("/dictionaries/queryDictData/{code}")
    Map<String, Object> queryDictData(@PathVariable String code);
}
