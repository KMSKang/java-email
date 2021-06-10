import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class EmailSend {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://[ip]:[port];database=[dbName];user=[userId];password=[userPassword];";
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            Statement stmt = connection.createStatement();

            String sender = "a@naver.com";
            String receiver = "a@naver.com";
            String receivers = "a@naver.com,b@naver.com";

            ResultSet manufactureStore = stmt.executeQuery(
                    "SELECT * FROM");
            while (manufactureStore.next()) {
                manufactureStore.getString("fieldName");
            }
            manufactureStore.close();
            stmt.close();
            connection.close();

            /**
             * 메일 발송
             */
            // 메일 서버
            String smtpHost = "";
            int smtpPort = 0;
            String smtpUsername = "";
            String smtpPassword = "";

            Properties props = System.getProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.port", smtpPort);
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(sender, sender)); // 발신자
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver)); // 수신자 // 한명
//            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receivers)); // 수신자 (다수)

            String subject = "제목";
            String body = String.join(
                    System.getProperty("line.separator"),
                    "<p>안녕하세요,</p>\n"
            );

            msg.setSubject(subject);
            msg.setContent(body, "text/html;charset=euc-kr");

            Transport transport = session.getTransport();
            transport.connect(smtpHost, smtpUsername, smtpPassword);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println("메일 전송 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
