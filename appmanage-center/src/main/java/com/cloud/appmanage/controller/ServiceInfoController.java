package com.cloud.appmanage.controller;

import ch.ethz.ssh2.Connection;
import com.cloud.appmanage.config.JenkinsConfig;
import com.cloud.appmanage.exception.AppManageException;
import com.cloud.appmanage.model.MultipartFileParam;
import com.cloud.appmanage.service.ServiceInfoService;
import com.cloud.appmanage.util.FreemakerUtil;
import com.cloud.appmanage.util.HttpClietUtil;
import com.cloud.appmanage.util.RemoteCommandUtil;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.utils.*;
import com.cloud.common.vo.ResultVO;
import com.cloud.model.appmanage.ServiceInfo;
import com.cloud.model.common.Page;
import com.cloud.model.common.Result;
import com.cloud.model.user.LoginAppUser;
import com.google.common.collect.Maps;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.JobWithDetails;
import io.swagger.annotations.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.readers.parameter.ModelAttributeParameterExpander;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 服务数据控制器
 *
 * @author yulj
 * @create: 2019/05/09 13:39
 */
@Api("service接口")
@RestController
@Slf4j
@RequestMapping("/service")
public class ServiceInfoController {

    @Autowired
    private FreemakerUtil freemakerUtil;

    @Value("${web.upload-path}")
    private String uploadPath;

    @Value("${serverIP}")
    private String serviceIP;

    private static AtomicLong counter = new AtomicLong(0L);

    @Autowired
    private ServiceInfoService serviceInfoService;

    @Autowired
    private JenkinsConfig jenkinsConfig;

    /**
     * 查询服务列表数据
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "查询服务列表数据", notes = "根据关键词查询服务列表数据")
    @ApiImplicitParam(name = "params", value = "查询条件params", required = false, dataType = "Map<ServiceInfo>", paramType = "query")
    @GetMapping("/list")
    public Result list(@RequestParam Map<String, Object> params) {
        Page<ServiceInfo> serviceInfoPage = this.serviceInfoService.list(params);
        return ResultUtil.success(serviceInfoPage.getTotal(), serviceInfoPage.getData());
    }

    /**
     * 服务创建
     *
     * @param serviceInfo
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "添加服务", notes = "新建服务")
    @ApiImplicitParam(name = "serviceInfo", value = "服务信息实体", required = true, dataType = "ServiceInfo", paramType = "query")
    @PostMapping("/add")
    public ResultVO<Map<String, String>> add(ServiceInfo serviceInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【服务创建】参数不正确, serviceInfo={}", serviceInfo);
            throw new AppManageException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        serviceInfo.setId(UUIDUtils.getUUID());
        serviceInfo.setCreateBy(loginAppUser.getFullName());
        serviceInfo.setCreateTime(new Date());
        serviceInfo.setIsDelete(0);
        this.serviceInfoService.add(serviceInfo);
        Map<String, String> map = Maps.newHashMap();
        map.put("serviceId", serviceInfo.getId());
        return ResultVOUtil.success(map);
    }

    /**
     * 服务创建
     *
     * @param serviceInfo
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "修改服务", notes = "根据服务id修改服务信息")
    @ApiImplicitParam(name = "serviceInfo", value = "服务信息实体", required = true, dataType = "ServiceInfo", paramType = "query")
    @PutMapping("/update")
    public Result update(@RequestBody ServiceInfo serviceInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【服务创建】参数不正确, serviceInfo={}", serviceInfo);
            throw new AppManageException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        serviceInfoService.update(serviceInfo);
        return ResultUtil.success(null);
    }

/*
    @ApiOperation(value = "分片上传文件", notes = "分片上传大文件")
    @ApiImplicitParam(name = "param", value = "分片文件信息", required = false, dataType = "MultipartFileParam", paramType = "query")
    @PostMapping("/uploadfile")
    public Object uploadv2(MultipartFileParam param) throws Exception {


        String finalDirPath = null;

        try {

            String prefix = "req_count:" + counter.incrementAndGet() + ":";
            System.out.println(prefix + "start !!!");
            //使用 工具类解析相关参数，工具类代码见下面

            log.info(prefix + "chunks= " + param.getChunks());
            log.info(prefix + "chunk= " + param.getChunk());
            log.info(prefix + "chunkSize= " + param.getSize());

            //这个必须与前端设定的值一致
            long chunkSize = 1024 * 1024;

            finalDirPath = uploadPath + "/" + param.getName().substring(0, param.getName().lastIndexOf("."));


            // String tempDirPath = finalDirPath + param.getId();

            String tempFileName = param.getName() + "_tmp";

            File confFile = new File(finalDirPath, param.getName() + ".conf");

            File tmpDir = new File(finalDirPath);

            File tmpFile = new File(finalDirPath, tempFileName);

            if (!tmpDir.exists()) {
                tmpDir.mkdirs();
            }

            RandomAccessFile accessTmpFile = new RandomAccessFile(tmpFile, "rw");

            RandomAccessFile accessConfFile = new RandomAccessFile(confFile, "rw");

            long offset = chunkSize * param.getChunk();
            //定位到该分片的偏移量
            accessTmpFile.seek(offset);
            //写入该分片数据
            accessTmpFile.write(param.getFile().getBytes());

            //把该分段标记为 true 表示完成
            System.out.println(prefix + "set part " + param.getChunk() + " complete");

            accessConfFile.setLength(param.getChunks());
            accessConfFile.seek(param.getChunk());
            accessConfFile.write(Byte.MAX_VALUE);

            //completeList - +-+++++++++++++++++++++++++++++++++
            byte[] completeList = FileUtils.readFileToByteArray(confFile);
            byte isComplete = Byte.MAX_VALUE;
            for (int i = 0; i < completeList.length && isComplete == Byte.MAX_VALUE; i++) {
                //与运算, 如果有部分没有完成则 isComplete 不是 Byte.MAX_VALUE
                isComplete = (byte) (isComplete & completeList[i]);
                System.out.println(prefix + "check part " + i + " complete?:" + completeList[i]);
            }

            if (isComplete == Byte.MAX_VALUE) {
                System.out.println(prefix + "upload complete !!" +
                        "---------------------------------------------------------");
                renameFile(finalDirPath + "/" + tempFileName, finalDirPath + "/" + param.getName());
                finalDirPath += "/" + param.getName();

            }

            accessTmpFile.close();
            accessConfFile.close();

            System.out.println(prefix + "end !!!");

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (finalDirPath != null) {
            //  String returnPath ="http://"+serviceIP+":/file"+path;
            return finalDirPath;
        }

        return "还在上传中";
    }

    private void renameFile(String file, String toFile) {
        File toBeRenamed = new File(file);
        //检查要重命名的文件是否存在，是否是文件
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {

            System.out.println("File does not exist: " + file);
            return;
        }

        File newFile = new File(toFile);

        //修改文件名
        if (toBeRenamed.renameTo(newFile)) {
            System.out.println("File has been renamed.");
        } else {
            System.out.println("Error renmaing file");
        }
    }*/


    /**
     * 服务修改
     *
     * @param serviceInfo
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "服务修改", notes = "修改服务信息")
    @ApiImplicitParam(name = "serviceInfo", value = "服务修改", required = true, dataType = "ServiceInfo", paramType = "query")
    @PostMapping("/edit")
    public ResultVO<Map<String, String>> edit(ServiceInfo serviceInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【服务修改】参数不正确, serviceInfo={}", serviceInfo);
            throw new AppManageException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        serviceInfo.setUpdateBy(loginAppUser.getFullName());
        serviceInfo.setUpdateTimre(new Date());
        this.serviceInfoService.updateById(serviceInfo);
        Map<String, String> map = Maps.newHashMap();
        map.put("serviceId", serviceInfo.getId());
        return ResultVOUtil.success(map);
    }

    /**
     * 根据服务id加载服务信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "查询服务信息", notes = "根据服务id查询服务信息")
    @ApiImplicitParam(name = "id", value = "服务id", required = true, dataType = "String", paramType = "query")
    @PostMapping("/load")
    public Result load(@RequestParam String id) {
        ServiceInfo serviceInfo = this.serviceInfoService.findById(id);
        if (serviceInfo == null) {
            return ResultUtil.error(1, "未查询到服务信息！");
        }
        return ResultUtil.success(serviceInfo);
    }

    /**
     * 根据服务id删除服务信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据服务信息", notes = "根据服务id删除服务信息")
    @ApiImplicitParam(name = "id", value = "服务id", required = true, dataType = "String", paramType = "path")
    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable String id) {
        if (ValidateUtil.isEmpty(id)) {
            return ResultUtil.error(1, ResultEnum.PARAM_ERROR.getMessage());
        }
        this.serviceInfoService.delete(id);
        return ResultUtil.success();
    }

    /**
     * 服务构建
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "构建服务", notes = "构建服务")
    @ApiImplicitParam(name = "id", value = "服务构建id", required = true, dataType = "String", paramType = "path")
    @PostMapping(value = "/buildJenkins/{id}")
    public Result buildJenkins(@PathVariable String id) throws Exception {
        ServiceInfo serviceInfo = serviceInfoService.findById(id);

        if (serviceInfo.getType().equals("jar")) {
            String addr = serviceInfo.getJarAddr().substring(0, serviceInfo.getJarAddr().lastIndexOf("/"));
            /*  Connection login = RemoteCommandUtil.login("10.130.86.36", "root", "hyhh2018");
            String ls = RemoteCommandUtil.execute(login, " cd "+addr+" ;sh dev-app.sh");*//*
            String cmd="sh build.sh";
            Map map = RemoteCommandUtil.excLinuxCommand(cmd,new File(addr));
            System.out.println(map.get("result").toString());*/

            Map<String, String> map = new HashMap<>();
            map.put("addr", addr);
            String send = null;
            try {
                send = HttpClietUtil.send("http://10.130.86.36:8091/build", map, "UTF-8");
                log.info("返回结果为："+send);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                return ResultUtil.error(1, e.getMessage());
            }
            return ResultUtil.success(send);


        } else {
            String jobName = serviceInfo.getName();
            String result = "";
            JenkinsServer jenkins = new JenkinsServer(new URI(jenkinsConfig.getJenUrl()), jenkinsConfig.getUsername(), jenkinsConfig.getPasswordorToken());
            if (jenkins.isRunning()) {
                JobWithDetails job = jenkins.getJob(jobName);
                if (job != null) {
                    job.build();
                    String consoleOutputHtml = job.getLastBuild().details().getConsoleOutputHtml();
                    serviceInfo.setJenkinslog(consoleOutputHtml);
                    serviceInfoService.updateById(serviceInfo);
                    // 获取text格式日志
                    //String consoleOutputText = job.getLastBuild().details().getConsoleOutputText();
                    // 获取执行结果（是否成功）
                    BuildResult buildResult = job.getLastBuild().details().getResult();
                    result = buildResult.toString();
                } else {
                    throw new AppManageException(ResultEnum.JENKINS_NOJOB_ERROR);
                }
            } else {
                throw new AppManageException(ResultEnum.JENKINS_RUN_ERROR);
            }
            return ResultUtil.success(result);
        }
    }

    /**
     * 查看日志
     *
     * @return
     */
    @ApiOperation(value = "日志查看", notes = "根据id查看日志")
    @ApiImplicitParam(name = "id", value = "日志id", required = true, dataType = "String", paramType = "path")
    @PostMapping(value = "/buildLod/{id}")
    public String ShowLog(@PathVariable String id) {
        ServiceInfo serviceInfo = serviceInfoService.findById(id);
        String jenkinslog = serviceInfo.getJenkinslog();
        return jenkinslog;
    }

    /**
     * 创建服务构建文件
     * @param serviceInfo
     */
    @PostMapping(value = "/createFile")
    public void createFile(@RequestBody ServiceInfo serviceInfo) {

        serviceInfo = serviceInfoService.findById(serviceInfo.getId());
       /* serviceInfo.setId(serviceInfo.getId());
        serviceInfo.setJarAddr(serviceInfo.getJarAddr());
        serviceInfoService.update(serviceInfo);
        serviceInfo = serviceInfoService.findById(serviceInfo.getId());
        Map<String, String> map = new HashMap<>();
        map.put("service_name", serviceInfo.getName());
        map.put("port", serviceInfo.getServicePort());
        String jarAddr = serviceInfo.getJarAddr();
        String fileDir = jarAddr.substring(0, jarAddr.lastIndexOf("/"));
        freemakerUtil.createFile(map, fileDir);
        System.out.println(fileDir);*/


        Map<String, String> map = new HashMap<>();
        map.put("service_name", serviceInfo.getName());
        map.put("port" ,serviceInfo.getServicePort());
        map.put("addr",serviceInfo.getJarAddr());
        String send = null;
        try {
            send = HttpClietUtil.send("http://10.130.86.36:8091/createFile", map, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

}
