//package cn.goroute.smart.thirdpart.util;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//
//import javax.annotation.Resource;
//import javax.mail.internet.MimeMessage;
//import java.util.Map;
//
///**
// * @author Alickx
// */
//@Service
//public class SendMailUtil {
//
//    @Resource
//    JavaMailSender javaMailSender;
//
//    @Autowired
//    private TemplateEngine templateEngine;
//
//    @Value("${spring.mail.username}")
//    private String sender;
//
//
//    /**
//     * 发送模板邮件
//     * @param receiver 接收者
//     * @param subject 邮件的主题email
//     * @param emailTemplate 邮件的模板名称
//     * @param dataMap 邮件中变量 email:接收人邮箱，code:验证码 createTime:创建时间
//     */
//    public void sendTemplateMail(String receiver, String subject, String emailTemplate, Map<String, Object> dataMap) throws Exception {
//        Context context = new Context();
//        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
//            context.setVariable(entry.getKey(), entry.getValue());
//        }
//        String templateContent = templateEngine.process(emailTemplate, context);
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//        helper.setFrom(sender);
//        helper.setTo(receiver);
//        helper.setSubject(subject);
//        helper.setText(templateContent, true);
//        javaMailSender.send(message);
//    }
//
//}
