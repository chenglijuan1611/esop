package com.fh.controller.base.wechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import net.sf.json.JSONObject;
import com.fh.service.base.wechat.WeChatManager;
import com.fh.service.system.user.UserManager;

/** 
 * 说明：物联新风公众号关注用户管理
 * 创建人：df
 * 创建时间：2019-11-22
 */
@RestController
@RequestMapping(value="/wechat")
@PropertySource("classpath:WuLianXinFeng.properties")
public class WeChatController extends BaseController {
	
	String menuUrl = "wechat/list.do"; //菜单地址(权限用)
	@Resource(name="wechatService")
	private WeChatManager wechatService;
	@Resource(name="userService")
	private UserManager userService;
	
	@Value("${getUserInfo_url}")
	private String getUserInfo_url;
	@Value("${appid}")
	private String appid;
	@Value("${appsecret}")
	private String appsecret;
	@Value("${access_token_url}")
	private String access_token_url;
	@Value("${getUserInfo}")
	private String getUserInfo;
	@Value("${gz_url}")
	private String gz_url;
	
	
	
	/** 模板消息发送
	 * 微信公共账号发送给账号
	 * @param openId 用户的唯一标识
	 * @param content 想要发送的消息内容
	 * @throws Exception
	 */
	@RequestMapping(value="/sendTempMess")
	public JSONObject sendTempMess() throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"新增ProcessFileManager");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		//JSONObject jsonObject = new JSONObject();
		String openId = pd.getString("openId");
		String content = pd.getString("content");
		//消息数据封装
		String token = "根据appid和appscrect获取assce_token";
		String postUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token;
		
		JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser", "发送到用户的openid");   // openid
        jsonObject.put("template_id", "你的模板id");
        jsonObject.put("url", "http://www.baidu.com");
 
        JSONObject data = new JSONObject();
        JSONObject first = new JSONObject();
        first.put("value", "hello");
        first.put("color", "#173177");
        JSONObject keyword1 = new JSONObject();
        keyword1.put("value", "hello");
        keyword1.put("color", "#173177");
        JSONObject keyword2 = new JSONObject();
        keyword2.put("value", "hello");
        keyword2.put("color", "#173177");
        JSONObject keyword3 = new JSONObject();
        keyword3.put("value", "hello");
        keyword3.put("color", "#173177");
        JSONObject remark = new JSONObject();
        remark.put("value", "hello");
        remark.put("color", "#173177");
        
        data.put("first",first);
        data.put("keyword1",keyword1);
        data.put("keyword2",keyword2);
        data.put("keyword3",keyword3);
        data.put("remark",remark);
 
        jsonObject.put("data", data);
    	String mess = jsonObject.toString();
		//发送消息
		//获取access_token
		try {
			//String access_token = getAccess_token("wx128d093ff923833a","45490835a79e039ba01ad63ef11f27ba");
	        //String postdata = createpostdata(openId, "text",content);
	        //customSend(postdata, access_token);
			//logBefore(logger, Jurisdiction.getUsername()+"给关注用户发了消息");
			//jsonObject.put("res", "success");
		}catch(Exception e) {
			//jsonObject.put("res", "fail");
			//logBefore(logger, Jurisdiction.getUsername()+"未能成功给关注用户发消息");
		}
		return jsonObject;
	}
	
	
	
	/** 普通文本消息，需用户在48h与公共帐号有互动
	 * 微信公共账号发送给账号
	 * @param openId 用户的唯一标识
	 * @param content 想要发送的消息内容
	 * @throws Exception
	 */
	@RequestMapping(value="/sendmessage")
	public JSONObject sendmessage() throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"新增ProcessFileManager");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		JSONObject jsonObject = new JSONObject();
		String openId = pd.getString("openId");
		String content = pd.getString("content");
		//发送消息
		//获取access_token
		try {
			String access_token = getAccess_token("wx128d093ff923833a","45490835a79e039ba01ad63ef11f27ba");
			if("".equals(access_token) || null == access_token) {
				jsonObject.put("res", "accessfail");
				return jsonObject;
			}
	        String postdata = createpostdata(openId, "text",content);
	        try {
	        	customSend(postdata, access_token);
	        }catch(Exception e) {
	        	e.printStackTrace();
	        	jsonObject.put("res", "sendfail");
				return jsonObject;
	        }
			logBefore(logger, Jurisdiction.getUsername()+"给关注用户发了消息");
			jsonObject.put("res", "success");
		}catch(Exception e) {
			jsonObject.put("res", "fail");
			logBefore(logger, Jurisdiction.getUsername()+"未能成功给关注用户发消息");
		}
		return jsonObject;
	}
	
	/**更新关注用户列表
	 * @param 无需参数
	 * @throws Exception
	 */
	@RequestMapping(value="/updateFocusUser")
	public JSONObject updateFocusUser() throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"新增ProcessFileManager");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		JSONObject jsonObject = new JSONObject();
		/*
		 * 查询出所有的关注用户
		 * */
		//获取access_token
		String access_token = getAccess_token("wx128d093ff923833a","45490835a79e039ba01ad63ef11f27ba");
		if("".equals(access_token) || null == access_token) {
			jsonObject.put("res", "accessIsNull");
			return jsonObject;
		}else {
			try {
				//获取关注用户列表
				JSONObject jObject =  getuserInfo(access_token);
				if(null == jObject) {
					jsonObject.put("res", "userListIsNull");
					return jsonObject;
				}else {
					String str = jObject.get("openid").toString().replace("[", "").replace("]", "").replaceAll("\"", "");
					String[] openids = str.split(",");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					for(int i = 0;i < openids.length;i++) {
						PageData pData = new PageData();
						String openid = openids[i];
						pData.put("OPENID", openid);	
						//判断该用户信息是否已经添加过
						PageData pd = wechatService.findByOpenId(pData);
						if(null != pd) {
							continue;
						}
						String userInfoUrl;
						JSONObject jsonObject3;
						try {
							userInfoUrl = getUserInfo.replace("ACCESS_TOKEN", access_token).replace("OPENID",openid);
							jsonObject3 = httpRequst(userInfoUrl,"GET",null);
						}catch(Exception e) {
							jsonObject.put("res", "interfaceFail");
							e.printStackTrace();
							return jsonObject;
						}
						if(null == jsonObject3) {
							jsonObject.put("res", "userInfoFail");
							return jsonObject;
						}else {
							pData.put("WECHATNICKNAME", jsonObject3.get("nickname").toString());
							pData.put("OPENID", openid);
							String sex = jsonObject3.get("sex").toString();
							if("1".equals(sex)) {
								pData.put("GENDER","男");
							}else if("2".equals(sex)) {
								pData.put("GENDER","女");
							}else {
								pData.put("GENDER","");
							}
							String timestmap = jsonObject3.get("subscribe_time").toString();
							String date = sdf.format(new Date(Long.parseLong(timestmap) * 1000L));
							pData.put("DAYOFPAYATTENTION",date);
							pData.put("REGION", jsonObject3.get("country").toString()+jsonObject3.get("province").toString()+jsonObject3.get("city").toString());
							wechatService.save(pData);
						}
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		logBefore(logger, Jurisdiction.getUsername()+"更新了关注用户信息条数据");
		jsonObject.put("res", "success");
		return jsonObject;
	}
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public String save(Model model,String checkadd) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增WeChat");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		String result = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("WECHAT_ID", this.get32UUID());	//主键
		wechatService.save(pd);
		model.addAttribute("msg","success");
		if(checkadd.equals("checked")) {
			model.addAttribute("checkadd",checkadd);
			result = "redirect:goAdd.do";
		}else {
			result = "save_result";
		}
		return result;
	}
	
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改WeChat");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData p = userService.findByUsername(pd);
		String name = p.getString("NAME");
		pd.put("NAME", name);
		wechatService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表WeChat");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		//String id = pd.getString("id");
		page.setPd(pd);
		List<PageData>	varList = wechatService.list(page);	//列出WeChat列表
		mv.setViewName("base/wechat/wechat_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(String checkadd)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("base/wechat/wechat_edit");
		mv.addObject("checkadd",checkadd);
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = wechatService.findById(pd);	//根据ID读取
		
		//查询出所有的系统用户信息listAll
		List<PageData> users = userService.listAll(pd);
		
		mv.setViewName("base/wechat/wechat_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("users", users);
		return mv;
	}	
	
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出WeChat到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("微信昵称");	//1
		titles.add("微信号");	//2
		titles.add("地区");	//3
		titles.add("性别");	//4
		titles.add("关注日期");	//5
		titles.add("关注用户唯一标识");	//6
		dataMap.put("titles", titles);
		List<PageData> varOList = wechatService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("WECHATNICKNAME"));	    //1
			vpd.put("var2", varOList.get(i).getString("WECHATNUMBER"));	    //2
			vpd.put("var3", varOList.get(i).getString("REGION"));	    //3
			vpd.put("var4", varOList.get(i).getString("GENDER"));	    //4
			vpd.put("var5", varOList.get(i).getString("DAYOFPAYATTENTION"));	    //5
			vpd.put("var6", varOList.get(i).getString("OPENID"));	    //6
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	/**
	 * 获取用户信息
	 *
	 */
	public JSONObject getuserInfo(String access_token) {
		//String access_token = getAccess_token("wx128d093ff923833a","45490835a79e039ba01ad63ef11f27ba");
		//System.out.println("---"+access_token);
		JSONObject jsonObject = getGz(access_token);
		JSONObject jsonObject2 = (JSONObject)jsonObject.get("data");
		return jsonObject2;
	}
	
	/**
	 * 获取access_token
	 * @param appid
	 * @param appsecret
	 * @return
	 */
	public String getAccess_token(String appid, String appsecret) {
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
	public JSONObject getGz(String access_token) {
		JSONObject jsonObject = null;
		try {
			//String access_token = getAccess_token(appid, appsecret);
			String requestUrl = gz_url.replace("ACCESS_TOKEN", access_token);
			jsonObject = httpRequst(requestUrl, "GET", null);
			
		} catch (Exception e) {

		}
		return jsonObject;
	}
	
	
	public JSONObject httpRequst(String requestUrl, String requetMethod,String outputStr) {
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
	
	public String createpostdata(String touser, String msgtype,String contentValue) {
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("touser", touser);
    	jsonObject.put("msgtype", msgtype);
    	JSONObject jObject = new JSONObject();
    	jObject.put("content", contentValue);
    	jsonObject.put("text", jObject);
    	String mess = jsonObject.toString();
    	return mess;
    }
	
	/**
     * post请求,发送消息
     */
    public void customSend(String json, String accessToken)throws Exception  {
    	
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStreamWriter outWriter = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
            out = new PrintWriter(outWriter); // 获取URLConnection对象对应的输出流
            // 发送请求参数
            out.print(json);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
//            System.out.println("客服消息result：" + result);
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
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
