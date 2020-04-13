package co.edu.poligran.service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface SendMail {
	
	public void sendMail(String to,String subject,String body);
	
	public void sendMailOld(String to, String subject, String body) throws AddressException, MessagingException;

}
