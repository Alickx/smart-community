package cn.goroute.smart.mail.event;

import cn.goroute.smart.mail.model.MailSendInfo;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;


@ToString
public class MailSendEvent extends ApplicationEvent {

	public MailSendEvent(MailSendInfo mailSendInfo) {
		super(mailSendInfo);
	}

}
