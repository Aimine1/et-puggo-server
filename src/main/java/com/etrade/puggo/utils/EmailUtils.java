package com.etrade.puggo.utils;

import com.etrade.puggo.config.EmailStampConfig;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * @author niuzhenyu
 * @description : 发送电子邮箱
 * @date 2023/5/22 16:49
 **/
@Slf4j
@Service
public class EmailUtils {

    @javax.annotation.Resource
    private EmailStampConfig emailStampConfig;

    public boolean SendEmail(String sender, String password, String host, String port, String receiver,
        String subject, String context) {

        try {
            Properties props = new Properties();
            // 开启debug调试
            props.setProperty("mail.debug", "false");  //false
            // 发送服务器需要身份验证
            props.setProperty("mail.smtp.auth", "true");
            // 设置邮件服务器主机名
            props.setProperty("mail.host", host);
            // 发送邮件协议名称 这里使用的是smtp协议
            props.setProperty("mail.transport.protocol", "smtp");
            // 服务端口号
            props.setProperty("mail.smtp.port", port);
            props.put("mail.smtp.starttls.enable", "true");

            // 设置环境信息
            Session session = Session.getInstance(props);

            // 创建邮件对象
            MimeMessage msg = new MimeMessage(session);

            // 设置发件人
            msg.setFrom(new InternetAddress(sender));

            // 设置收件人
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));

            // 设置邮件主题
            msg.setSubject(subject);

            // 设置邮件内容
            msg.setContent(context, "text/html;charset=UTF-8");

            // 设置邮件内容
            /*Multipart multipart = new MimeMultipart();
            MimeBodyPart textPart = new MimeBodyPart();
            //发送邮件的文本内容
            textPart.setText(context);
            multipart.addBodyPart(textPart);*/

            // 添加附件
            //MimeBodyPart attachPart = new MimeBodyPart();
            //可以选择发送文件...
            //DataSource source = new FileDataSource("C:\\Users\\36268\\Desktop\\WorkSpace\\MyApp\\Program.cs");
            //attachPart.setDataHandler(new DataHandler(source));
            //设置文件名
            //attachPart.setFileName("Program.cs");
            //multipart.addBodyPart(attachPart);

            //msg.setContent(multipart);

            Transport transport = session.getTransport();
            // 连接邮件服务器
            transport.connect(sender, password);
            // 发送邮件
            transport.sendMessage(msg, new Address[]{new InternetAddress(receiver)});
            // 关闭连接
            transport.close();

            return true;
        } catch (Exception e) {
            log.error("[EmailUtils] 邮件发送失败", e);
            e.printStackTrace();
            return false;
        }
    }


    public boolean SendEmailVerifyCodeForRegister(String receiver, String code) {
        // 发件人设置
        String sender = emailStampConfig.getSender();
        String password = emailStampConfig.getPassword();
        String host = emailStampConfig.getHost();
        String port = emailStampConfig.getPort();
        // 主题
        String subject = "" + code;
        // 邮件内容生成
        String context = buildContent(code, "static/VerificationCode.html");
        return SendEmail(sender, password, host, port, receiver, subject, context);
    }

    public boolean SendEmailVerifyCodeForRetrievePassword(String receiver, String code) {
        // 发件人设置
        String sender = emailStampConfig.getSender();
        String password = emailStampConfig.getPassword();
        String host = emailStampConfig.getHost();
        String port = emailStampConfig.getPort();
        // 主题
        String subject = "" + code;
        // 邮件内容生成
        String context = buildContent(code, "static/RetrievePassword.html");
        return SendEmail(sender, password, host, port, receiver, subject, context);
    }


    private String buildContent(String title, String templateHtmlPath) {
        //加载邮件html模板
        Resource resource = new ClassPathResource(templateHtmlPath);
        InputStream inputStream = null;
        BufferedReader fileReader = null;
        StringBuilder buffer = new StringBuilder();
        String line;
        try {
            inputStream = resource.getInputStream();
            fileReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            log.error("模板读取失败", e);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //替换html模板中的参数
        return MessageFormat.format(buffer.toString(), title);
    }


}
