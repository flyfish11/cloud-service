package com.cloud.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * jquery ztree 插件的节点(Radio单选)
 *
 * @author yulj
 * @create: 2019/05/06 16:25
 */
@Data
@Builder(toBuilder = true)
public class ZtreeNodeRadio {

    /**
     * 节点id
     */
    private Long id;

    /**
     * 父节点id
     */
    @JsonProperty("pId")
    private Long pId;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 是否打开节点
     */
    private Boolean open;

    /**
     * 是否被选中
     */
    private Boolean checked;

    /**
     * 是否可选
     */
    private Boolean nocheck;
}
