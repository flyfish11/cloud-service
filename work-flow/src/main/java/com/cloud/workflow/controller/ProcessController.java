package com.cloud.workflow.controller;

import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.ResultUtil;
import com.cloud.model.activiti.HistoryActivitiInstanceVO;
import com.cloud.model.activiti.ProcessDefinitionVO;
import com.cloud.model.activiti.ProcessInstanceVO;
import com.cloud.model.common.Result;

import com.cloud.model.user.AppUser;
import com.cloud.workflow.util.Base64Utils;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
public class ProcessController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    IdentityService identityService;

    /**
     * 部署列表
     */
    @GetMapping(value = "/processDefinition")
    public Result showAct(Map<String, String> param) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService
                .createProcessDefinitionQuery();
        List<org.activiti.engine.repository.ProcessDefinition> processDefinitionList = null;

        processDefinitionList = processDefinitionQuery.listPage(Integer.valueOf(10) * (Integer.valueOf(1) - 1), Integer.valueOf(10));
        long count = repositoryService.createDeploymentQuery().count();

        System.out.println(processDefinitionList.size());
        List<ProcessDefinitionVO> list = new ArrayList<>();
        processDefinitionList.forEach(process -> {
            list.add(new ProcessDefinitionVO(process));
        });
        return ResultUtil.success(list);
    }

    //删除已部署的流程
    @DeleteMapping("/processDefinition/{id}")
    public Result delDeploy(@PathVariable String id) {
        //得到repositoryService
        RepositoryService repositoryService = processEngine
                .getRepositoryService();
        //根据流程定义id查询部署id

        org.activiti.engine.repository.ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionId(id)
                .singleResult();
        //流程定义所属部署id
        String deploymentId = processDefinition.getDeploymentId();

        //1.根据流程部署id删除这一次部署的所有流程定义
        //建议一次部署只部署一个流程，根据流程部署id删除一个流程的定义
        //约束：如果该流程定义没有启动流程实例可以删除，如果该流程定义以及启动流程实例，不允许删除，如果删除就抛出异常
        // repositoryService.deleteDeployment(deploymentId);
        //级联删除，如果 boolean值为true时，会删除所有和当前部署的规则相关的信息，包括历史的信息。
        repositoryService.deleteDeployment(deploymentId, true);

        //2.级联删除：不管该流程定义是否启动流程实例（是否使用），通过级联删除将该流程定义及相关的信息全部删除
        //一般情况下不适用级联删除，一般情况下对流程定义执行暂停操作
        //特殊情况下需要删除流程定义及相关的信息，就要使用级联删除，删除的功能给超级管理员使用
        //repositoryService.deleteDeployment(deploymentId,true);

        return ResultUtil.success(null);
    }

    /**
     * 查询当前用户历史流程实例
     * @return
     */
    @GetMapping("/historicProcessInstance")
    public Result queryHistoricProcessInstance() {
        AppUser user = AppUserUtil.getLoginAppUser();
        List<HistoricProcessInstance> list = processEngine.getHistoryService()
                .createHistoricProcessInstanceQuery()
                .startedBy(user.getId())
                .finished()
                .orderByProcessInstanceStartTime().asc()//排序
                .list();
        return ResultUtil.success(list);


    }
    /**
     * 开启一个流程实例
     * @param processDefinitionId 流程定义id
     * @return
     */
    @PostMapping("/startProcess/{processDefinitionId}")
    public Result startProcessInstance(@PathVariable String processDefinitionId, @RequestBody Map<String, Object> params) {
        AppUser user = AppUserUtil.getLoginAppUser();
        identityService.setAuthenticatedUserId(user.getId());
        //params.put("type", request.getParameter("type").toString());
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId).singleResult();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, params);
        processEngine.getRuntimeService().setProcessInstanceName(processInstance.getProcessInstanceId(), processDefinition.getName());
        return ResultUtil.success(null);
    }


    /**
     * 查询当前用户正在进行的流程实例
     * @return
     */
    @GetMapping("/processInstance")
    public Result queryProcessInstance() {
       // AppUser user = AppUserUtil.getLoginAppUser();
        List<HistoricProcessInstance> historicProcessInstanceList = processEngine.getHistoryService()
                .createHistoricProcessInstanceQuery()
                //.startedBy(user.getId())
                .unfinished()
                .list();
        return ResultUtil.success(historicProcessInstanceList);

    }

    /**
     * 查看流程详情
     * @param processInstanceId
     * @return
     */
    @GetMapping("/processDetails/{processInstanceId}")
    public Result processDetails(@PathVariable String processInstanceId ){
        Map map = new HashMap<String, Object>();
        List<HistoricVariableInstance> list = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()                                      //创建一个历史的流程变量查询对象
                .processInstanceId(processInstanceId)
                .list();
        //    istoricVariableInstance agentAccessRequestId = list.stream().filter(aa -> aa.getVariableName().equals("agentAccessRequestId")).collect(Collectors.toList()).get(0);
        if (list != null && list.size() > 0) {
            for (HistoricVariableInstance hvi : list) {

                if (hvi.getVariableName().equals("taskOpinionList")) {
                    map.put("taskOpinionList", hvi.getValue());
                    return ResultUtil.success(hvi.getValue());
                }

            }
        }
        return ResultUtil.success(null);

       /* ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();

        if (processInstance == null) {
            if (list != null && list.size() > 0) {
                for (HistoricVariableInstance hvi : list) {
                    System.out.println(hvi.getVariableName() + "    " + hvi.getValue());
                    if (hvi.getVariableName().equals("flag")) {
                        map.put("processState", hvi.getValue());
                    }
                }
            }
            ;

        } else {
            map.put("processState", "running ");
        }*/

    }

    @GetMapping("/processTracking/{processInstanceId}")
    public Result processTracking(@PathVariable String processInstanceId) throws IOException {
        System.out.println(processInstanceId);
        //获取历史流程实例
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());
        List<HistoricActivityInstance> highLightedActivitList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
        //高亮环节id集合
        List<String> highLightedActivitis = new ArrayList<String>();
        //高亮线路id集合
        List<String> highLightedFlows = getHighLightedFlows(definitionEntity, highLightedActivitList);

        for (HistoricActivityInstance tempActivity : highLightedActivitList) {
            String activityId = tempActivity.getActivityId();
            highLightedActivitis.add(activityId);
        }
        //中文显示的是口口口，设置字体就好了
        InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis, highLightedFlows, "宋体", "宋体", "宋体", processEngineConfiguration.getClassLoader(), 1.0);
        Map<String, Object> shineProImages = new HashMap<>();
        String imageCurrentNode = null;
        if (imageStream != null) {
            imageCurrentNode = Base64Utils.ioToBase64(imageStream);

        }

        return ResultUtil.success(imageCurrentNode);
    }

    /**
     * 获取需要高亮的线
     *
     * @param processDefinitionEntity
     * @param historicActivityInstances
     * @return
     */
    private List<String> getHighLightedFlows(
            ProcessDefinitionEntity processDefinitionEntity,
            List<HistoricActivityInstance> historicActivityInstances) {
        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// 对历史流程节点进行遍历
            ActivityImpl activityImpl = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i)
                            .getActivityId());// 得到节点定义的详细信息
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
            ActivityImpl sameActivityImpl1 = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i + 1)
                            .getActivityId());
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances
                        .get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances
                        .get(j + 1);// 后续第二个节点
                if (activityImpl1.getStartTime().equals(
                        activityImpl2.getStartTime())) {
                    // 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity
                            .findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl
                    .getOutgoingTransitions();// 取出节点的所有出去的线
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
                        .getDestination();
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }

}
