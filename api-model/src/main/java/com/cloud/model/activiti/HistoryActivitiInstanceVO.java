package com.cloud.model.activiti;

import lombok.Data;
import org.activiti.engine.history.HistoricActivityInstance;

import java.util.List;
import java.util.Map;

@Data
public class HistoryActivitiInstanceVO {

    private String activityId;
    private String activityName;
    private String activityType;
    private String executionId;
    private String assignee;
    private String taskId;
    private String calledProcessInstanceId;
    private String tenantId = "";
    private List<Map<String,Object>> variableInstances;

    public HistoryActivitiInstanceVO(HistoricActivityInstance historicActivityInstance) {
        this.activityId = historicActivityInstance.getActivityId();
        this.activityName = historicActivityInstance.getActivityName();
        this.activityType = historicActivityInstance.getActivityType();
        this.executionId = historicActivityInstance.getTenantId();
        this.assignee = historicActivityInstance.getAssignee();
        this.taskId = historicActivityInstance.getTaskId();
        this.calledProcessInstanceId = historicActivityInstance.getCalledProcessInstanceId();
        this.tenantId = historicActivityInstance.getTenantId();
    }
}
