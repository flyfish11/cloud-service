package com.cloud.backend.service.impl;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.cloud.backend.service.SendMailService;

import lombok.extern.slf4j.Slf4j;

/**
 * 邮件发送
 * 
 * @author hlxd
 *
 */
@Slf4j
@Service
public class SendMailServiceImpl implements SendMailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String serverMail;

	@Override
	public boolean sendMail(String toUser, String subject, String text) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(serverMail);
			helper.setTo(toUser);
			helper.setSubject(subject);
			helper.setText(text, true);

			javaMailSender.send(message);

			log.info("发送邮件to:{},主题：{},内容：{}", toUser, subject, text);
		} catch (Exception e) {
			log.error("", e);

			return false;
		}

		return true;

	}
}
