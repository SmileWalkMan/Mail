import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;

public class HttpsUtil {

    private static final class DefaultTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    private static HttpsURLConnection getHttpsURLConnection(String uri, String method) throws IOException {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SSLSocketFactory ssf = ctx.getSocketFactory();

        URL url = new URL(uri);
        HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();
        httpsConn.setSSLSocketFactory(ssf);
        httpsConn.setHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });
        httpsConn.setRequestMethod(method);
        httpsConn.setDoInput(true);
        httpsConn.setDoOutput(true);
        return httpsConn;
    }

    private static byte[] getBytesFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] kb = new byte[1024];
        int len;
        while ((len = is.read(kb)) != -1) {
            baos.write(kb, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        baos.close();
        is.close();
        return bytes;
    }

    private static void setBytesToStream(OutputStream os, byte[] bytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        byte[] kb = new byte[1024];
        int len;
        while ((len = bais.read(kb)) != -1) {
            os.write(kb, 0, len);
        }
        os.flush();
        os.close();
        bais.close();
    }

    public static byte[] doGet(String uri) throws IOException {
        HttpsURLConnection httpsConn = getHttpsURLConnection(uri, "GET");
        return getBytesFromStream(httpsConn.getInputStream());
    }

    public static byte[] doPost(String uri, String data) throws IOException {
    	
        HttpsURLConnection httpsConn = getHttpsURLConnection(uri, "POST");
        httpsConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        httpsConn.setRequestProperty("User-Agent", 
        		"Mozilla/5.0 (iPhone; CPU iPhone OS 12_5_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 MicroMessenger/8.0.9(0x18000927) NetType/WIFI Language/zh_CN");
//				"Mozilla/5.0 (iPhone; CPU iPhone OS 12_5_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 MicroMessenger/8.0.9(0x18000921) NetType/WIFI Language/zh_CN");
        httpsConn.setRequestProperty("Cookie", 
				"lambo-sso-key_0_=051xxzFa1pvXsB0clnHa1wPKhQ0xxzFU#tg0vfTqqDlya7y4mRZ77LxdlUF0F7bdi2UTzoYr0J2o=");
        
		setBytesToStream(httpsConn.getOutputStream(), data.getBytes());
        return getBytesFromStream(httpsConn.getInputStream());
    }
    public static void main(String[] args) throws Exception {
    	String starttime=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    	String endtime="";
    	
    	for(int i=0;;i++) {
	    	String uri = "https://reserve.moutai.com.cn/api/rsv-server/anon/consumer/getShops";
	        byte[] bytes = HttpsUtil.doPost(uri,"custId=******");
	        String output = new String(bytes);
	        String timeStr1=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	        System.out.println(timeStr1+":\n"+i+":"+output);
	        if(output.contains("浙江")) {
	        	PlayMP3.play("win.mp3");
	        	JOptionPane.showMessageDialog(null, "浙江来了 " ,
	        			"浙江可以预约了", JOptionPane.WARNING_MESSAGE);
	        	endtime=timeStr1;
	        	System.out.println("Start: "+starttime);
	        	System.out.println("End: "+endtime);
	        	break;
	        }
	        if(output.contains("请通过微信官方客户端查看")) {
	        	PlayMP3.play("reget.mp3");
	        	JOptionPane.showMessageDialog(null, "需要重新登陆 " ,
	        			"需要重新登陆", JOptionPane.WARNING_MESSAGE);
	        	endtime=timeStr1;
	        	System.out.println("Start: "+starttime);
	        	System.out.println("End: "+endtime);
	        	break;
	        }
	       
	        Thread.sleep(5000);
    	}
    }
}