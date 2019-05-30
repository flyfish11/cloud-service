package com.cloud.appmanage.feign;

import com.cloud.model.user.Dept;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 部门管理FeignClient
 *
 * @author yulj
 * @create: 2019/05/22 00:06
 */

@FeignClient("user-center")
public interface DeptClient {

    @GetMapping(value = "/dept/load/{id}")
    Dept load(@PathVariable Integer id);

    @GetMapping(value = "/dept/queryDeptNames")
    String queryDeptNames(@RequestParam String ids);
}
