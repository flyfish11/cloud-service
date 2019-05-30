package com.cloud.model.activiti;;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.activiti.engine.form.FormProperty;

@Getter
@Setter
@ToString
public class FormPropertyVO  {
    protected String id;
    protected String name;
    protected FormTypeVO type;
    protected boolean isRequired;
    protected boolean isReadable;
    protected boolean isWritable;
    protected String value;

    public FormPropertyVO(FormProperty formProperty) {
        this.id = formProperty.getId();
        this.name = formProperty.getName();
        this.isRequired = formProperty.isRequired();
        this.isReadable = formProperty.isReadable();
        this.isWritable = formProperty.isWritable();
    }
}
