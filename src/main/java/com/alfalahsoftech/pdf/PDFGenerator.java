package com.alfalahsoftech.pdf;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.faceless.pdf.PDF;
import org.faceless.pdf.PDFPage;
import org.faceless.pdf.PDFStyle;
import org.faceless.pdf.StandardFont;
import org.faceless.report.ReportParser;
import org.xml.sax.InputSource;

import com.alfalahsoftech.alframe.util.EmailUtility;
import com.alfalahsoftech.inv.entity.EOClient;
import com.alfalahsoftech.web.AFWebContextListener;
public class PDFGenerator {
	public static void main(String[] args) throws Exception
	{
	}
	//generatePDF(null);

	public static void  generatePDF(String str,EOClient client) {
		try{

			//File fileName=new File(AFWebContextListener.contextPath+"james.html");
			ReportParser parser=ReportParser.getInstance();
			PDF pdf=null;//parser.parse(fileName);
			if(str!=null) {
				System.out.println("str============ "+str);
				pdf=parser.parse(new InputSource(new StringReader(str)));	
			}

			parser.setLicenseKey("G96A6BB27E8A7A5");

			PDF p = new PDF();
			PDFPage page = pdf.newPage(4,3);

			PDFStyle mystyle = new PDFStyle();
			mystyle.setFont(new StandardFont(StandardFont.HELVETICA), 24);
			mystyle.setFillColor(Color.BLUE);

			page.setStyle(mystyle);
			page.drawText("Hello, PDF-viewing World!",100,100);


			// Set some meta-information in the document
			//pdf.setInfo("Author", "Joe Bloggs");
			// pdf.setInfo("Title", "My First Document");
			//pdf.setPassword("Sam"); ////////////////////////////////Uncomment this if you want to set password in PDF

			// Set the password

			//  StandardEncryptionHandler handler = new StandardEncryptionHandler();
			// handler.setUserPassword("password");
			//p.setEncryptionHandler(handler);
			System.out.println("AFWebContextListener.contextPath=== "+AFWebContextListener.contextPath);
			OutputStream fo = new FileOutputStream(new File(AFWebContextListener.contextPath+"Invoice.pdf")); //Arshad_Ord_0000000015_Invoice.pdf

			pdf.render(fo);
			fo.close();
			List<String> attachmentsPath=Arrays.asList(AFWebContextListener.contextPath+"Invoice.pdf");
			System.out.println("PDF created sucessfully.  "+pdf.getInfo("Title"));
			if(Objects.nonNull(client)) {
				EmailUtility.sendMailViaGmail(null, "Billing Invoice", "Please find the attachement.", Arrays.asList(client.getEmailID()), Arrays.asList("Invoice.pdf"), attachmentsPath);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
}


