package com.alfalahsoftech.alframe.util;


import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



//Sending Email through Gmail Server with SSL
public class EmailUtility {  

	private static final String SMTP_HOST_NAME = "smtp.gmail.com";  
	private static final String SMTP_PORT = "465";  
	private static  String emailFromAddress ="svnreleasetracker@gmail.com";
	private static  String emailPassword = "altametrics"; 
	private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";  
	private List<String> attachmentsPath;
	private List<String> attachmentsName;
	public static boolean isSendMailEnabled=true;

	public  void sendEmail(String emailFrom, String emailSubjectTxt,String emailMsgTxt,List<String> sendTo,List<String> attachmentsName, List<String> attachmentsPath ) throws Exception {  

		//////////////////////////// issues /////////////////////////////
		//----------Could not connect to SMTP host: smtp.gmail.com, port: 465, response: -1-------------------
		//then stop anti virusv or do firewall off
		//do Allow less secure apps: ON- settings->Accounts and Imports->other google account setting or change password recovery options-> search 'Allow less secure apps'
		this.attachmentsName = attachmentsName;
		this.attachmentsPath = attachmentsPath;
		if(emailFrom != null)
			emailFromAddress = emailFrom;
		//			emailFromAddress = ninjaProperties.get("emailFromAddress");
		//			emailPassword = ninjaProperties.get("emailPassword");
		//		System.out.println("emailSubjectTxt= "+emailSubjectTxt+" DateTime= "+LocalUtil.getDateTimeStr(new Date()));
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());  
		sendSSLMessage(sendTo, emailSubjectTxt,  
				emailMsgTxt, emailFromAddress);  
		System.out.println("Sucessfully sent mail to all Users\n "+sendTo);  
	}  

	/** 
	 * @param recipients 
	 * @param subject 
	 * @param message 
	 * @param from 
	 * @throws MessagingException 
	 */  
	public void sendSSLMessage(List<String> recipients, String subject,  
			String message, String from) throws MessagingException {  
		//boolean debug = true;  

		Properties props = new Properties();  
		props.put("mail.smtp.host", SMTP_HOST_NAME);  
		props.put("mail.smtp.auth", "true");  
		props.put("mail.debug", "false");  
		props.put("mail.smtp.port", SMTP_PORT);  
		props.put("mail.smtp.socketFactory.port", SMTP_PORT);  
		props.put("mail.smtp.socketFactory.class", SSL_FACTORY);  
		props.put("mail.smtp.socketFactory.fallback", "false");  

		Session session = Session.getDefaultInstance(props,  
				new Authenticator() {  
			protected PasswordAuthentication getPasswordAuthentication() {  
				return new PasswordAuthentication(emailFromAddress,  
						emailPassword);  
			}  
		});  
		//session.setDebug(debug);  
		Message msg = new MimeMessage(session);  
		InternetAddress addressFrom = new InternetAddress(from);  
		msg.setFrom(addressFrom);  

		InternetAddress[] addressTo = new InternetAddress[recipients.size()];  
		for (int i = 0; i < recipients.size(); i++) {  
			addressTo[i] = new InternetAddress(recipients.get(i));  
		}  
		msg.setRecipients(Message.RecipientType.TO, addressTo);  
		msg.setSubject(subject);  

		try {

			MimeBodyPart messagePart = new MimeBodyPart();
			messagePart.setText(message);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messagePart);
			messagePart.setContent(message,"text/html");

			if(attachmentsPath != null && attachmentsPath.size()>0){
				AtomicInteger atomicInteger = new AtomicInteger(0);
				attachmentsPath.forEach(e->{

					try {
						MimeBodyPart attachmentPart = new MimeBodyPart();
						FileDataSource fileDataSource = new FileDataSource(e);
						attachmentPart.setDataHandler(new DataHandler(fileDataSource));
						attachmentPart.setFileName(attachmentsName.get(atomicInteger.getAndIncrement()));
						multipart.addBodyPart(attachmentPart);
					}catch (MessagingException ex) {
						System.out.println("Sending Mail via Gmail >>>");
						ex.printStackTrace();
					}
				});

			}

			msg.setContent(multipart);
			if(EmailUtility.isSendMailEnabled){
				Transport.send(msg);
			}
		} catch (MessagingException e) {
			System.out.println("Sending Mail via Gmail >>>");
			e.printStackTrace();
		}
	}  

	public static void main(String[] args) {

		try {
			String[] sendTo= new String[2];
			sendTo [0] = "malam9716@gmail.com";
			sendTo [1] = "smd.techie@gmail.com";
			List<String> attachmentsPath=Arrays.asList(
					"/media/malam/workarea/pj/MyWorkSpace/SP_WorkSpace/DnDApp/WebContent/WEB-INF/classes/META-INF/glbDir/info.txt"
					,"/media/malam/dataarea/da/currentnotes/collections.doc"
					);

			String[]attachmentsName={"about SendMail","collections_doc"};
			EmailUtility.sendMailViaGmail(emailFromAddress, "Test Email", "Sending mail from java", Arrays.asList("malam9716@gmail.com","smd.techie@gmail.com"), Arrays.asList("about SendMail","collections_doc"), attachmentsPath);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error in sending mail");
		}

	}


	public static void sendMailViaGmail(String emailFrom, String emailSubjectTxt,String emailMsgTxt,List<String> sendTo,List<String> attachmentsName, List<String> attachmentsPath ) throws Exception{
		/*	for(String sendto :sendTo)
				System.out.println("sendTo= "+sendto);
			System.out.println("emailSubjectTxt = "+emailSubjectTxt);
			System.out.println("emailMsgTxt= "+emailMsgTxt);
			for(String nameOfFile :attachmentsName)
				System.out.println("attachmentFileName= "+nameOfFile);
			for(String path :attachmentsPath)
				System.out.println("attachmentPaht = "+path);
		 */

		new EmailUtility().sendEmail((emailFrom ==null?emailFromAddress:emailFrom),emailSubjectTxt, emailMsgTxt,sendTo,attachmentsName, attachmentsPath);

	}
}  	
