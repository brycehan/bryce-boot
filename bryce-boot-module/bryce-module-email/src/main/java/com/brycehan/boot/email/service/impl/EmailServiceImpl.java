package com.brycehan.boot.email.service.impl;

import com.brycehan.boot.api.email.EmailApi;
import com.brycehan.boot.api.email.dto.ToMail;
import com.brycehan.boot.api.email.dto.ToVerifyCodeEmailDto;
import com.brycehan.boot.common.enums.EmailType;
import com.brycehan.boot.email.entity.ToEmailConvert;
import com.brycehan.boot.email.service.EmailService;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Objects;

/**
 * 邮件服务实现
 *
 * @author Bryce Han
 * @since 2023/10/8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService, EmailApi {
    @Resource
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendSimpleEmail(ToMail toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        // 发送者
        message.setFrom(from);
        // 接收者
        message.setTo(toEmail.getTos());
        // 抄送
        message.setCc(toEmail.getCc());
        // 密送
        message.setBcc(toEmail.getBcc());
        // 邮件主题
        message.setSubject(toEmail.getSubject());
        // 邮件内容
        message.setText(toEmail.getContent());
        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            throw new RuntimeException("普通邮件发送失败");
        }
    }

    @Override
    public void sendHtmlEmail(ToMail toEmail, MultipartFile file) {
        try {
            // 创建一个MimeMessage
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = getMimeMessageHelper(toEmail, message);
            if (file != null) {
                // 添加附件
                messageHelper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
            }

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("邮件发送失败");
        }
    }

    @Override
    public void send(ToVerifyCodeEmailDto toVerifyCodeEmailDto, EmailType emailType) {
        ToMail toEmail = ToEmailConvert.INSTANCE.convert(toVerifyCodeEmailDto);

        // 参数封装
        Context context = new Context();
        context.setVariable("emailType", emailType.desc());
        context.setVariable("verifyCode", toVerifyCodeEmailDto.getVerifyCode());

        toEmail.setSubject("Bryce帐号邮件验证码");
        toEmail.setContent(this.templateEngine.process(TemplateIds.VERIFY_CODE, context));

        this.sendHtmlEmail(toEmail, null);
    }

    /**
     * 获取MimeMessageHelper
     *
     * @param toEmail ToMail
     * @param message MimeMessage
     * @return MimeMessageHelper
     * @throws MessagingException MessagingException
     */
    private @NotNull MimeMessageHelper getMimeMessageHelper(ToMail toEmail, MimeMessage message) throws MessagingException {
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

        // 发送者
        messageHelper.setFrom(from);
        // 接收者
        messageHelper.setTo(toEmail.getTos());
        // 抄送
        messageHelper.setCc(toEmail.getCc());
        // 密送
        messageHelper.setBcc(toEmail.getBcc());
        // 邮件主题
        messageHelper.setSubject(toEmail.getSubject());
        // 邮件内容  true表示带有附件或html
        messageHelper.setText(toEmail.getContent(), true);
        return messageHelper;
    }

    /**
     * 模板ID
     */
    static class TemplateIds {
        /**
         * 验证码
         */
        public static final String VERIFY_CODE = "verify-code";
    }

}
