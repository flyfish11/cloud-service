/**
 * Copyright 2018 lenos
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cloud.model.activiti;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.activiti.engine.history.HistoricTaskInstance;

import java.util.Date;

/**
 * @author zhuxiaomeng
 * @date 2018/1/21.
 * @email 154040976@qq.com
 *
 * 流程任务
 */

@Getter
@Setter
@ToString
public class HistoryTaskVO {
    protected String executionId;
    protected String name;
    protected String localizedName;
    protected String parentTaskId;
    protected String description;
    protected String localizedDescription;
    protected String owner;
    protected String assignee;
    protected String taskDefinitionKey;
    protected String formKey;
    protected int priority;
    protected Date dueDate;
    protected Date claimTime;
    protected String category;
    protected String tenantId ;
    public HistoryTaskVO() {
    }

    public HistoryTaskVO(HistoricTaskInstance historicTaskInstance) {
        this.executionId = historicTaskInstance.getExecutionId();
        this.name = historicTaskInstance.getName();
        this.parentTaskId = historicTaskInstance.getParentTaskId();
        this.description = historicTaskInstance.getDescription();
        this.owner = historicTaskInstance.getOwner();
        this.assignee = historicTaskInstance.getAssignee();
        this.taskDefinitionKey = historicTaskInstance.getTaskDefinitionKey();
        this.formKey = historicTaskInstance.getFormKey();
        this.priority = historicTaskInstance.getPriority();
        this.dueDate = historicTaskInstance.getDueDate();
        this.claimTime = historicTaskInstance.getClaimTime();
        this.category = historicTaskInstance.getCategory();
        this.tenantId = historicTaskInstance.getTenantId();
    }
}
