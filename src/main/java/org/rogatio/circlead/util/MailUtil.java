package org.rogatio.circlead.util;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class MailUtil allows to send a mail with or without attachment
 */
public class MailUtil {

	/** The Constant LOGGER. */
	final static Logger LOGGER = LogManager.getLogger(MailUtil.class);
	
	/** The host. */
	private String host = "";
	
	/** The port. */
	private int port = 0;
	
	/** The username. */
	private String username = "";
	
	/** The password. */
	private String password = "";

	/**
	 * Instantiates a new mail util.
	 *
	 * @param host the host
	 * @param port the port
	 * @param username the username
	 * @param password the password
	 */
	public MailUtil(String host, int port, String username, String password) {
		
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	/**
	 * Send mail.
	 *
	 * @param from the from
	 * @param to the to
	 * @param subject the subject
	 * @param msg the msg
	 * @param attachment the attachment
	 */
	public void sendMail(String from, String to, String subject, String msg, File attachment) {

		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", host);
		prop.put("mail.smtp.port", port);
		prop.put("mail.smtp.ssl.trust", host);

		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setHeader("X-Mailer", "Circlead");
		    message.setSentDate(new Date());
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msg, "text/html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			
			if (attachment != null) {
				if (attachment.exists()) {
					MimeBodyPart attachmentBodyPart = new MimeBodyPart();
					attachmentBodyPart.attachFile(attachment);
					multipart.addBodyPart(attachmentBodyPart);
				}
			}

			message.setContent(multipart);

			Transport.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
