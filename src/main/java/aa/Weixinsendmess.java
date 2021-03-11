package aa;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.junit.Test;
import org.omg.PortableServer.ServantActivator;

import net.sf.json.JSONObject;

public class Weixinsendmess {
	public static void main(String args[]) {
		//getuserInfo();
	}
	
	
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?" + "grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public final static String gz_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=";
	public final static String getUserInfo = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	private String sendMsgUrl = "https://api.weixin.qq.com/cgi-bin/message/send?access_token=";
	
	private HttpClient webClient;
	  
	/**
	 * 给用户发消息1
	 *
	 
    public  void sendTextMessageToUser(String content,String toUser){
        String json = "{\"touser\": \""+toUser+"\",\"msgtype\": \"text\", \"text\": {\"content\": \""+content+"\"}}";
        //获取access_token
        GetExistAccessToken getExistAccessToken = GetExistAccessToken.getInstance();
        String accessToken = getExistAccessToken.getExistAccessToken();
        //获取请求路径
        String action = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+accessToken;
        System.out.println("json:"+json);
        try {
            connectWeiXinInterface(action,json);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    */
	
	/**
	 * 给用户发消息1
	 *
	 */
	@Test
	public void sendmess() throws Exception{
		String access_token = getAccess_token("wx128d093ff923833a","45490835a79e039ba01ad63ef11f27ba");
		//webClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		String url = MessageFormat.format(sendMsgUrl, access_token);
		HttpPost post = new HttpPost(url);
		ResponseHandler<?> responseHandler = new BasicResponseHandler();
		StringEntity entity = new StringEntity("hello.");
		post.setEntity(entity);
		String response = (String) this.webClient.execute(post, responseHandler);
		System.out.println("---response"+response);
	}

	/**
	 * 获取用户信息
	 *
	 */
	public static void getuserInfo() {
		String access_token = getAccess_token("wx128d093ff923833a","45490835a79e039ba01ad63ef11f27ba");
		System.out.println("---"+access_token);
		JSONObject jsonObject = getGz("wx128d093ff923833a","45490835a79e039ba01ad63ef11f27ba");
		JSONObject jsonObject2 = (JSONObject)jsonObject.get("data");
		String str = jsonObject2.get("openid").toString().replace("[", "").replace("]", "").replaceAll("\"", "");
		String[] openids = str.split(",");
		for(int i = 0;i < 1;i++) {
			String openid = openids[i];
			System.out.println("openid---"+openid);
			String userInfoUrl = getUserInfo.replace("ACCESS_TOKEN", access_token).replace("OPENID",openid);
			System.out.println("userInfoUrl---"+userInfoUrl);
			JSONObject jsonObject3 = httpRequst(userInfoUrl,"GET",null);
			System.out.println(jsonObject3);
		}
		
	}
	/**
	 * 获取access_token
	 * @param appid
	 * @param appsecret
	 * @return
	 */
	public static String getAccess_token(String appid, String appsecret) {
		try {
			String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
			JSONObject jsonObject = httpRequst(requestUrl, "GET", null);
			return jsonObject.getString("access_token");
		} catch (Exception e) {
			return "errer";
		}
	}
	
	/**
	 * 获取关注列表
	 * @param appid
	 * @param appsecret
	 */
	public static JSONObject getGz(String appid, String appsecret) {
		JSONObject jsonObject = null;
		try {
			String access_token = getAccess_token(appid, appsecret);
			String requestUrl = gz_url.replace("ACCESS_TOKEN", access_token);
			jsonObject = httpRequst(requestUrl, "GET", null);
			
		} catch (Exception e) {

		}
		return jsonObject;
	}
	
	public static JSONObject httpRequst(String requestUrl, String requetMethod,String outputStr) {
		JSONObject jsonobject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的新人管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
			sslcontext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocktFactory对象
			SSLSocketFactory ssf = sslcontext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requetMethod);
			if ("GET".equalsIgnoreCase(requetMethod))
				httpUrlConn.connect();
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonobject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
		} catch (Exception e) {
		}
		return jsonobject;
	}
}
class MyX509TrustManager implements X509TrustManager {
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
	}
	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
	}
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}
