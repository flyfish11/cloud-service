package com.cloud.model.dictionaryTable;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ApplicationClassify implements Serializable {

    private  String classifyId;
    private  String classifyName;
    private String description;
    private String parentId;
    private String creator;
    private Date createTime;


}
