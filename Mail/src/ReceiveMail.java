import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

public class ReceiveMail {
    
    // void receiveMail
    public static void receiveMail(String userName, String password) throws IOException {
        try {
            // Connect to mail server
            Properties properties = new Properties();
            properties.setProperty("mail.store.protocol", "pop3");
    		// 创建验证器
    		Authenticator auth = new Authenticator() {
    			public PasswordAuthentication getPasswordAuthentication() {
    				// 授权码验证
    				return new PasswordAuthentication("smilewalkman", "0a2da2863fda97bb");
    			}
    		};
            Session emailSession = Session.getDefaultInstance(properties,auth);
            Store emailStore = emailSession.getStore("pop3");
            emailStore.connect("pop.sina.com", userName, password);
            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Folder emailFolder = emailStore.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
            Message messages[] = emailFolder.getMessages();
            System.out.println(emailFolder.getMessageCount());
            for (int i = 0; i < messages.length; i++) {
            	   String subject = messages[i].getSubject();
            	   String from = (messages[i].getFrom()[0]).toString();
            	   System.out.println(messages[i].getReceivedDate()+":");
            	   System.out.println("第 " + (i + 1) + "封邮件的主题：" + subject);
            	   System.out.println("第 " + (i + 1) + "封邮件的发件人地址：" + from);
//            	   System.out.println(messages[i].getContent().toString());
            	   writePart(messages[i]);
            }
            emailFolder.close(false);
            emailStore.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String writePart(Part p) throws Exception {
       

        System.out.println("----------------------------");
        System.out.println("CONTENT-TYPE: " + p.getContentType());

        //check if the content is plain text
        if (p.isMimeType("text/plain")) {
            System.out.println("This is plain text");
            System.out.println("---------------------------");
            System.out.println((String) p.getContent());
        }
        //check if the content has attachment
        else if (p.isMimeType("multipart/*")) {
            System.out.println("This is a Multipart");
            System.out.println("---------------------------");
            Multipart mp = (Multipart) p.getContent();
            int count = mp.getCount();
            for (int i = 0; i < count; i++) {
                String str = writePart(mp.getBodyPart(i));
                if (str != null)
                    return str;
            }
        }
        //check if the content is a nested message
        else if (p.isMimeType("message/rfc822")) {
            System.out.println("This is a Nested Message");
            System.out.println("---------------------------");
            writePart((Part) p.getContent());
        }
        //check if the content is an inline image
        else if (p.isMimeType("image/jpeg")) {
            System.out.println("--------> image/jpeg");

        }
        else if (p.getContentType().contains("image/")) {
            System.out.println("content type" + p.getContentType());
            File f = new File("image" + new Date().getTime() + ".jpg");
            DataOutputStream output = new DataOutputStream(
                    new BufferedOutputStream(new FileOutputStream(f)));
            com.sun.mail.util.BASE64DecoderStream test =
                    (com.sun.mail.util.BASE64DecoderStream) p
                            .getContent();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = test.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }
        else {
            Object o = p.getContent();
            if (o instanceof String) {
                System.out.println("This is a string");
                System.out.println("---------------------------");
                System.out.println((String) o);
                return (String) o;
            }
            else if (o instanceof InputStream) {
                System.out.println("This is just an input stream");
                System.out.println("---------------------------");
                InputStream is = (InputStream) o;
                is = (InputStream) o;
                int c;
                while ((c = is.read()) != -1)
                    System.out.write(c);
            }
            else {
                System.out.println("This is an unknown type");
                System.out.println("---------------------------");
                System.out.println(o.toString());
            }
        }
        return null;
    }

    public static void main(String... args) throws IOException {
        receiveMail("smilewalkman@sina.com", "txb121314");
//        receiveMail("tangxuebo1986@163.com", "jj121314");
    }
}
