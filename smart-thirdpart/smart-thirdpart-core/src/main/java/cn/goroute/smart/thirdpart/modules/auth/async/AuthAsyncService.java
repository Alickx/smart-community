package cn.goroute.smart.thirdpart.modules.auth.async;

import cn.goroute.smart.mail.model.MailDetails;
import cn.goroute.smart.mail.sender.MailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author: Alickx
 * @Date: 2023/02/21 14:54:00
 * @Description:
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class AuthAsyncService {


    private final MailSender mailSender;

    @Async
    public void sendRegisterActiveEmail(String userName, String userEmail, String token) {

        log.info("异步发送邮件，username:[{}],email:[{}],token:[{}]", userName, userEmail, token);

        MailDetails mailDetails = new MailDetails();
        mailDetails.setTo(new String[]{userEmail});
        mailDetails.setSubject("智慧社区注册激活邮件");
        mailDetails.setShowHtml(true);
        mailDetails.setContent("尊敬的用户：" + userName + "，您好！<br/>" + "欢迎您注册智慧社区，您的激活链接为：<br/>"
                + "https://localhost:3000/auth/activate?token=" + token + "<br/>" + "请点击链接激活您的账号，如非本人操作，请忽略此邮件！");

        mailSender.sendMail(mailDetails);

    }

}
