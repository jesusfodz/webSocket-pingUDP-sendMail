package co.edu.poligran.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMailImpl implements SendMail {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendMail(String to, String subject, String body) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();

		//mailMessage.setFrom(from);
		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setText(body);
		
		javaMailSender.send(mailMessage);
	}
	
	
	public void sendMailOld(String to, String subject, String body) throws AddressException, MessagingException {
		Properties props=new Properties();
		props.put("mail.smtp.host", env.getProperty("spring.mail.host"));
		props.put("mail.smtp.port", env.getProperty("spring.mail.port"));
		props.put("mail.smtp.auth", env.getProperty("spring.mail.properties.mail.smtp.auth"));
		props.put("mail.smtp.starttls.enable", env.getProperty("spring.mail.properties.mail.smtp.starttls.enable"));
		props.put("mail.smtp.user", env.getProperty("spring.mail.username"));
		props.put("mail.smtp.clave", env.getProperty("spring.mail.password"));
		
		Session session=Session.getDefaultInstance(props);
		MimeMessage mensaje=new MimeMessage(session);
		
		mensaje.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
		mensaje.setSubject(subject);
		mensaje.setText(body);
		
		Transport transport=session.getTransport("smtp");
		transport.connect(env.getProperty("spring.mail.host"), env.getProperty("spring.mail.username"), env.getProperty("spring.mail.password"));
		transport.sendMessage(mensaje, mensaje.getAllRecipients());
		transport.close();
		
	}
}
