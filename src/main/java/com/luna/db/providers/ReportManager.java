
package com.luna.db.providers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.activation.DataSource;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang3.CharEncoding;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.luna.core.data.Report;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ReportManager
{
    private final ConnectionProvider connectionProvider;
    private static ReportManager instance;

    private ReportManager() {
        connectionProvider = DAOFactory.getInstance().getConnectionProvider();
    }

    public static ReportManager getInstance() {

        if ( instance == null ) {

            instance = new ReportManager();
        }
        return instance;
    }

    public void createReport( Report report ) throws SQLException {

        try
        {
            Connection connection = connectionProvider.getConnection();

            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream input = loader.getResourceAsStream( report.getPath() );

            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, report.getParameters(), connection );
            
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, baos);

            DataSource aAttachment =  new ByteArrayDataSource(baos.toByteArray(), "application/pdf");

            System.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

            JavaMailSender mailSender = getJavaMailSender();

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
        
            helper.setTo( new String[] { 
                "gabriel.schmogel@universo.univates.br",  
                "cezar.zanotelli@universo.univates.br",
                "erik.vergani@universo.univates.br",
                "fernando.filter@universo.univates.br",
                "gabriel.minetto@universo.univates.br",
            });
        
            helper.setFrom("luna.projects18@gmail.com");
            helper.setSubject("USERS REPORT");
        
            String text = "All users report";
        
            helper.setText(text, false);
        
            helper.addAttachment("report.pdf",aAttachment);
        
            mailSender.send(message);

            //JasperViewer.viewReport(print,false);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.out.println( "failed to generate report: " + ex.getMessage() );
        }
        finally {
            connectionProvider.closeConnection();
        }
    }

    @Bean
    private JavaMailSender getJavaMailSender() 
    {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");     
        props.put("mail.smtp.ssl.protocols", "smtp");
        
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication( "luna.project18@gmail.com", "lxpcirzmeomklvcg" );
            }
        });

        mailSender.setSession( session );

        return mailSender;
    }
}
