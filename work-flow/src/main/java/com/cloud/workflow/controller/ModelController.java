package com.cloud.workflow.controller;

import com.cloud.common.utils.ResultUtil;
import com.cloud.model.common.Result;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;


@RequestMapping(value = "/act")
@RestController
public class ModelController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/addModel")
    public Result newModel() throws UnsupportedEncodingException {

        Model model = repositoryService.newModel();

        String name = "新建流程";
        String description = "";
        int revision = 1;
        String key = "processKey";

        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());

        repositoryService.saveModel(model);
        String id = model.getId();

        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace",
                "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
        return ResultUtil.success(id);

    }

    /**
     * 模型列表
     */
    @GetMapping(value = "/queryActModel")
    public Result showModel(Map<String, String> param) {
        List<Model> models = repositoryService.createModelQuery()
                .listPage(Integer.valueOf(10) * (Integer.valueOf(1) - 1), Integer.valueOf(10));
        long count = repositoryService.createModelQuery().count();
        return ResultUtil.success(models);
    }

    @DeleteMapping("removeActModel/{id}")
    public Result delModel(@PathVariable String id) {
        repositoryService.deleteModel(id);
        return ResultUtil.success(null);

    }

    @PostMapping(value = "/deployActModel/{id}")
    public Result open(@PathVariable String id) {
        try {
            Model modelData = repositoryService.getModel(id);
            byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
            if (bytes == null) {
                return ResultUtil.error(0, "模型为空");
            }
            JsonNode modelNode = null;
            modelNode = new ObjectMapper().readTree(bytes);

            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            if (model.getProcesses().size() == 0) {
                return ResultUtil.error(0, "数据不符合要求");
            }
            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
            //发布流程
            String processName = modelData.getName() + ".bpmn20.xml";
            String convertToXML = new String(bpmnBytes);

            System.out.println(convertToXML);
            Deployment deployment = null;

            deployment = repositoryService.createDeployment()
                    .name(modelData.getName())
                    .addString(processName, new String(bpmnBytes, "UTF-8"))
                    .deploy();
            modelData.setDeploymentId(deployment.getId());
            repositoryService.saveModel(modelData);
            return ResultUtil.success(null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ResultUtil.error(0,"部署失败");
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.error(0,"部署失败");
        }


    }
}
