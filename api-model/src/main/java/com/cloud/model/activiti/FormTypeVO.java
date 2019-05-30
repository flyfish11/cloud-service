package com.cloud.model.activiti;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;
@Getter
@Setter
@ToString
public class FormTypeVO implements Serializable {

    private  String type;
    private Map<String ,String> values;
}
