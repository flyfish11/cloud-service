package com.cloud.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * jquery ztree 插件的节点
 *
 * @author yulj
 * @create: 2019/05/06 14:46
 */

@Data
public class ZTreeNode {
    /**
     * 节点id
     */
    private String id;

    /**
     * 父节点id
     */
    @JsonProperty("pId")
    private String pId;

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
}
