package com.cloud.workflow.controller;

import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.ResultUtil;
import com.cloud.model.activiti.HistoryTaskVO;
import com.cloud.model.activiti.TaskOpinion;
import com.cloud.model.activiti.TaskVO;
import com.cloud.model.common.Result;
import com.cloud.model.user.LoginAppUser;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.activiti.engine.*;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping
public class TaskServiceController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FormService formService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    HistoryService historyService;

    @Autowired
    TaskService taskService;

    @Autowired
    IdentityService identityService;

    private String taskOpinionList = "taskOpinionList";

    @Autowired
    private ProcessEngine processEngine;

    /**
     * 查询当前用户任务
     * @return  返回任务列表
     */
    @GetMapping("/task")
    public Result queryTask() {
        //AppUser user = AppUserUtil.getLoginAppUser();
     //   System.out.println("userid : " + userId);
        //根据任务候选人查询任务
        //根据任务代理人查询任务
        List<Task> assigneeList1 = taskService.createTaskQuery().list();
        List<Task> assigneeList = taskService.createTaskQuery().list();
        //根据任务候选组查询任务

        List<TaskVO> taskList = new ArrayList<>();
        assigneeList.forEach(task -> {
            TaskVO t = new TaskVO(task);
            taskList.add(t);
        });
        //设置发起人调整申请
     /*   for (Task task : assigneeList1) {
            if (task.getAssignee() == null) {
                HistoricProcessInstance processInstance = ProcessEngines.getDefaultProcessEngine().
                        getHistoryService().createHistoricProcessInstanceQuery().
                        processInstanceId(task.getProcessInstanceId()).singleResult();
                if (user.getId().equals(processInstance.getStartUserId())) {
                    taskList.add(new TaskVO(task));
                }
            }
        }*/
        return ResultUtil.success(taskList);
    }

    /**
     * 查询当前用户历史任务
     * @return 历史任务列表
     */
    @GetMapping("/historyTask")
    public Result historyTaskList() {
        //AppUser user = AppUserUtil.getLoginAppUser();
        List<HistoryTaskVO> taskList=new ArrayList<>();
        List<HistoricTaskInstance> list = ProcessEngines.getDefaultProcessEngine()
                .getHistoryService() // 历史相关Service
                .createHistoricTaskInstanceQuery() // 创建历史任务实例查询
                .finished()
                //.taskAssignee(user.getId())
                // 查询已经完成的任务
                .list();
        list.forEach(task->{
            taskList.add(new HistoryTaskVO(task));
        });
        return ResultUtil.success(taskList);
    }

    /**
     * 根据任务id查询任务表单
     * @param taskId
     * @return 返回任务表单 key
     */
    @GetMapping("/getTaskForm/{taskId}")
    public Result getTaskForm(@PathVariable String taskId) {
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        String formKey = taskFormData.getFormKey();

        return ResultUtil.success(formKey);
    }

    /**
     * 完成任务
     * @param taskOpinion
     * @return
     */
    @PostMapping(value = "/completeTask")
    public Result compaleteTask(@RequestBody TaskOpinion taskOpinion) {
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        // System.out.println(taskOpinion);

        Map<String, Object> variables = taskService.getVariables(taskOpinion.getTaskId());

      /*  variables.forEach((k, v) -> {

            System.out.println("key:" + k + "      v:" + v);
        });*/
        taskOpinion.setCreateTime(new Date());
        taskOpinion.setOpId(user.getId());
        taskOpinion.setOpName(user.getUsername());

        Map<String, Object> map = new HashMap<>();
        map.put("flag", taskOpinion.isFlag());

        //判断节点是否已经拒绝过一次了
        Object needend = variables.get("needend");
        if (needend != null && (boolean) needend && (!taskOpinion.isFlag())) {
            map.put("needfinish", -1); //结束
        } else {
            if (taskOpinion.isFlag()) {
                map.put("needfinish", 1);//通过下一个节点
            } else {
                map.put("needfinish", 0);//不通过
            }
        }
        //审批信息叠加
        List<TaskOpinion> taskOpinions = new ArrayList<>();
        Object o = variables.get(taskOpinionList);
        if (o != null) {
            taskOpinions = (List<TaskOpinion>) o;
        }
        taskOpinions.add(taskOpinion);
        map.put(taskOpinionList, taskOpinions);
        taskService.complete(taskOpinion.getTaskId(), map);
        return ResultUtil.success(null);
    }

}
