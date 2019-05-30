package com.cloud.backend.controller;

import com.cloud.backend.service.MailService;
import com.cloud.model.common.Page;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import com.cloud.model.mail.Mail;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/mails")
public class MailController {

    @Autowired
    private MailService mailService;

    @PreAuthorize("hasAuthority('mail:query')")
    @ApiOperation(value = "查询邮件", notes = "根据邮件id查询邮件")
    @ApiImplicitParam(name = "id", value = "邮件id", required = true, dataType = "String ", paramType = "path")
    @GetMapping("/{id}")
    public Mail findById(@PathVariable String id) {
        return mailService.findById(id);
    }

    @PreAuthorize("hasAuthority('mail:query')")
    @ApiOperation(value = "查询邮件列表", notes = "根据关键词查询邮件")
    @ApiImplicitParam(name = "params", value = "查询条件", required = false, dataType = "Map ", paramType = "query")
    @GetMapping
    public Page<Mail> findMails(@RequestParam Map<String, Object> params) {
        return mailService.findMails(params);
    }

    @LogAnnotation(module = LogModule.ADD_MAIL)
    @PreAuthorize("hasAuthority('mail:save')")
    @ApiOperation(value = "添加邮件", notes = "新增邮件")
    @ApiImplicitParam(name = "mail", value = "邮件实体", required = false, dataType = "Mail ", paramType = "query")
    @PostMapping
    public Mail save(@RequestBody Mail mail, Boolean send) {
        mailService.saveMail(mail);
        if (Boolean.TRUE == send) {
            mailService.sendMail(mail);
        }

        return mail;
    }

    @LogAnnotation(module = LogModule.UPDATE_MAIL)
    @PreAuthorize("hasAuthority('mail:update')")
    @ApiOperation(value = "修改邮件", notes = "根据实体修改邮件")
    @ApiImplicitParam(name = "mail", value = "邮件实体", required = false, dataType = "Mail ", paramType = "query")
    @PutMapping
    public Mail update(@RequestBody Mail mail, Boolean send) {
        mailService.updateMail(mail);
        if (Boolean.TRUE == send) {
            mailService.sendMail(mail);
        }

        return mail;
    }


}
