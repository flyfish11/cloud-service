package com.cloud.model.activiti;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProcessInstanceVO {

    private String Id;
    private String name;
    private String deploymentId;
    private String processDefinitionId;
    private String tenantId;
    private String activityId;
    private String processDefinitionKey;
    private String processDefinitionName;
    private String description;
    private String localizedName;
    private String localizedDescription;
    private String businessKey;

    public ProcessInstanceVO(org.activiti.engine.runtime.ProcessInstance processInstance) {
        this.tenantId=processInstance.getTenantId();
        this.name=processInstance.getName();
        this.description=processInstance.getDescription();
        this.localizedName=processInstance.getLocalizedName();
        this.localizedDescription=processInstance.getLocalizedDescription();
        this.processDefinitionId=processInstance.getProcessDefinitionId();
        this.processDefinitionKey=processInstance.getProcessDefinitionKey();
        this.processDefinitionName=processInstance.getProcessDefinitionName();
        this.deploymentId=processInstance.getDeploymentId();
        this.activityId=processInstance.getActivityId();
        this.Id=processInstance.getProcessInstanceId();
        this.businessKey=processInstance.getBusinessKey();

    }


}
