package vn.com.nsmv.common;

import java.io.*;

import org.apache.velocity.*;
import org.apache.velocity.app.*;
import org.springframework.mail.*;

public class Mail
{
	private MailSender mailSender;
	private VelocityEngine velocityEngine;

	/******************************************
	 * Set value mailSender
	 *
	 * @param MailSender
	 * @return
	 ******************************************/
	public void setMailSender(MailSender mailSender)
	{
		this.mailSender = mailSender;
	}

	public VelocityEngine getVelocityEngine()
	{
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine)
	{
		this.velocityEngine = velocityEngine;
	}

	/******************************************
	 * Send mail
	 *
	 * @param mailFrom,
	 *            mailTo, mailSubject, mailContent
	 * mode = 0(register), 1(reset)
	 * @return
	 ******************************************/
	public void sendMail(MailForm from, int mode)
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from.getFrom());
		message.setTo(from.getTo());
		message.setSubject(from.getSubject());
		Template template = null;

		if (mode == 0)
		{
			template = velocityEngine.getTemplate("./templates/register_success.vm", "UTF-8");
		}
		else if (mode == 1)
		{
			template = velocityEngine.getTemplate("./templates/reset_account.vm", "UTF-8");
		}
		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("userName", from.getUserName());
		velocityContext.put("userCd", from.getUserCd());
		velocityContext.put("password", from.getPassword());
		velocityContext.put("url", from.getUrl());

		StringWriter stringWriter = new StringWriter();
		//TODO check template null before using
		template.merge(velocityContext, stringWriter);

		message.setText(stringWriter.toString());
		mailSender.send(message);
	}
}
