package com.cloud.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 部门数据实体
 *
 * @author yulj
 * @create: 2019/05/06 14:20
 */

@Data
public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 排序
     */
    private Integer num;

    /**
     * 父部门id
     */
    @JsonProperty("pId")
    private Integer pid;

    /**
     * 父级ids
     */
    private String pids;

    /**
     * 全称
     */
    private String fullname;

    /**
     * 提示
     */
    private String tips;

    /**
     * 版本（乐观锁保留字段）
     */
    private Integer version;

    /**
     * 部门编码
     *
     * @return
     */
    private String decode;

    /**
     * 组织机构编码
     *
     * @return
     */
    private String organid;

    /**
     * 部门简称
     *
     * @return
     */
    private String sdeptname;

    /**
     * 部门编码父级id
     */
    private String pidecode;
}
