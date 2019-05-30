package com.cloud.appmanage.vo;

import com.cloud.model.appmanage.ApplicationInfo;
import lombok.Data;

/**
 * 应用信息vo
 *
 * @author yulj
 * @create: 2019/05/22 00:50
 */

@Data
public class ApplicationInfoVo extends ApplicationInfo {

    /**
     * 所属部门名称
     */
    private String belongDepartmentName;

    /**
     * 使用范围名称
     */
    private String authorityAreaName;
}
