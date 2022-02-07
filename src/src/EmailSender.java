package src;

/*
@autor: Henri Dannenhöfer
Class to send out (reminder) e-mails

 */

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Properties;

public class EmailSender {

    private Properties properties = new Properties();
    private Session session;
    private Message message;

    /**
     *  Constructor of emailSender class sets the properties to connect to Googles´ GMail
     *  Some default parameters must be set as properties to access the google mail api
     *
     *  Shared Email: timescheduler.frauas@gmail.com,                              pw: _tsfrankfurt
     * @throws MessagingException
     */
    EmailSender() throws MessagingException {
      //  Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // smtp server to connect to
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.transport.protocol", "smtp");

        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(/* here is sender email / password ...  */"timescheduler.frauas@gmail.com", "_tsfrankfurt");
            }
        });

        message = new MimeMessage(session);
    }


    /**
     * This method sends out the mail that was configured beforehand while giving some status information
     *
     */
    public void sendMail() {
        System.out.println("Sending email...");
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            System.out.println("email not sent successfully!");
            e.printStackTrace();
        }
        System.out.println("Email sent successfully!");
    }

    /**
     * (OLD) setContent, just for testing now
     *
     * @param date
     * @param username
     * @param eventName
     * @param beginTime
     * @param duration
     * @param eventDescr
     * @param location
     * @param emails
     * @throws MessagingException
     */
    public void setContent(String date, String username, String eventName, String beginTime, int duration, JTextArea eventDescr, String location, ArrayList<String> emails) throws MessagingException {

        String content =
                "    \n  Scheduled Event from: "+ username+  "\n\n" +
                "Day:             " + date + "\n"+
                "Name:            " + eventName + "\n" +
                "Description:     ";

        // for layout of email we split the description into lines and format the description
        String [] sa = eventDescr.getText().split("\n");
        for(String str : sa) {
            content += str + "\n" + "                 ";
        }

        content +=
                "\nBegin time:      " + beginTime + "\n" +
                "Duration:        " + duration + " day(s)\n" +
                "Location:        " + location + "\n" +
                "Participant(s):  ";

        // adding email addresses to email layout
        int start = 0;
        Address addressTo_temp = null;

        for (String receiver : emails) {
            content += receiver;
            start++;
            // no ',' after last email, so position is compared with total number of participants
            if(!(start == emails.size())) {
                content += ", ";
            }
            //
            try {
                addressTo_temp = new InternetAddress(receiver);
                message.addRecipient(MimeMessage.RecipientType.TO, addressTo_temp);
            } catch (AddressException e) {
                System.out.println("Problem setting recipient emails.");
                e.printStackTrace();
            }
        }
        message.setSubject("Time Scheduler Event"); // setting content of email
        message.setText(content);
    }


    /**
     * Sets content, recipients and subject of the reminder email. Data is for this function taken from the
     * event database and added to the mail
     *
     *
     * @param date
     * @param username
     * @param eventName
     * @param beginTime
     * @param duration
     * @param description
     * @param location
     * @param emails
     * @throws MessagingException
     */
    public void setContentLate(String date, String username, String eventName, String beginTime, int duration, String description, String location, ArrayList<String> emails) throws MessagingException {
        if(description == null) {
            description = " ";
        }
        String content =
                "    \n  Scheduled Event from: "+ username+  "\n\n" +
                        "Day:               " + date + "\n"+
                        "Name:             " + eventName + "\n" +
                        "Description:     ";

        // for layout of email we split the description into lines and format the description
        String [] sa = description.split("\n");
        for(String str : sa) {
            content += str + "\n" + "                     ";
        }

        content +=
                "\nBegin time:      " + beginTime + "\n" +
                        "Duration:         " + duration + " day(s)\n" +
                        "Location:         " + location + "\n" +
                        "Participant(s):   ";

        // adding email addresses to email layout
        int start = 0;
        Address addressTo_temp = null;

        for (String receiver : emails) {
            content += receiver;
            start++;
            // no ',' after last email, so position is compared with total number of participants
            if(!(start == emails.size())) {
                content += ", ";
            }
            //
            try {
                addressTo_temp = new InternetAddress(receiver);
                message.addRecipient(MimeMessage.RecipientType.TO, addressTo_temp);
            } catch (AddressException e) {
                System.out.println("Problem setting recipient emails.");
                e.printStackTrace();
            }
        }
        message.setSubject("Time Scheduler Event"); // setting content of email
        message.setText(content);
    }
}
