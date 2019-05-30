package com.cloud.file.controller;

import com.cloud.common.utils.ResultUtil;
import com.cloud.file.config.FileServiceFactory;
import com.cloud.file.dao.FileDao;
import com.cloud.file.model.FileInfo;
import com.cloud.file.service.FileService;
import com.cloud.file.utils.FileUtil;
import com.cloud.model.common.Result;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileServiceFactory fileServiceFactory;

    /**
     * 文件上传<br>
     * 根据fileSource选择上传方式，目前仅实现了上传到本地<br>
     * 如有需要可上传到第三方，如阿里云、七牛等
     *
     * @param file
     * @param fileSource FileSource
     * @return
     * @throws Exception
     */
    @LogAnnotation(module = LogModule.FILE_UPLOAD, recordParam = false)
    @PostMapping
    public FileInfo upload(@RequestParam("file") MultipartFile file, String fileSource) throws Exception {
        FileService fileService = fileServiceFactory.getFileService(fileSource);
        return fileService.upload(file);
    }

    /**
     * layui富文本文件自定义上传
     *
     * @param file
     * @param fileSource
     * @return
     * @throws Exception
     */
    @LogAnnotation(module = LogModule.FILE_UPLOAD, recordParam = false)
    @PostMapping("/layui")
    public Map<String, Object> uploadLayui(@RequestParam("file") MultipartFile file, String fileSource)
            throws Exception {
        FileInfo fileInfo = upload(file, fileSource);

        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        Map<String, Object> data = new HashMap<>();
        data.put("id", fileInfo.getId());
        data.put("path", fileInfo.getPath());
        data.put("src", fileInfo.getUrl());
        map.put("data", data);

        return map;
    }

    /**
     * 文件删除
     *
     * @param id
     */
    @LogAnnotation(module = LogModule.FILE_DELETE)
    @PreAuthorize("hasAuthority('file:del')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        FileInfo fileInfo = fileDao.getById(id);
        if (fileInfo != null) {
            FileService fileService = fileServiceFactory.getFileService(fileInfo.getSource());
            fileService.delete(fileInfo);
        }
    }

    @Autowired
    private FileDao fileDao;

    /**
     * 文件查询
     *
     * @param params
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Result findFiles(@RequestParam Map<String, Object> params) {

        List<FileInfo> data = fileDao.findData(params);
        int count = fileDao.count(params);
        return ResultUtil.success(count, data);
    }

    /**
     * 返回图片
     *
     * @author stylefeng
     * @Date 2017/5/24 23:00
     */
    @RequestMapping("/{pictureId}")
    public void renderPicture(@PathVariable("pictureId") String pictureId, HttpServletResponse response) {
        FileInfo fileInfo = fileDao.getById(pictureId);
        try {
            byte[] bytes = FileUtil.toByteArray(fileInfo.getPath());
            response.getOutputStream().write(bytes);
        } catch (Exception e) {
            //如果找不到图片就返回一个默认图片
            try {
                response.sendRedirect("current/images/nopic.png");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
