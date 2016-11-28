package com.wdl.wdl.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class HttpUtil {
	public static enum HttpConstant {
	    DEFAULT_CHARSET("UTF-8"),
	    CHARSET(DEFAULT_CHARSET.value),
	    ACCEPT("accept","*/*"),
	    CONTENT_TYPE("content-type", "application/x-www-form-urlencoded"),
	    USER_AGENT("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)"),
	    CONNECTION("connection","Keep-Alive");
	    
	    private String value;
	    private String key;
	    HttpConstant(String value){
	        this.setValue(value);
	    }
	    HttpConstant(String key,String value){
	        this.setKey(key);
	        this.setValue(value);
	    }
	    public void setValue(String value) {
	        this.value = value;
	    }
	    public String getValue() {
	        return value;
	    }
	    public void setKey(String key) {
	        this.key = key;
	    }
	    public String getKey() {
	        return key;
	    }
	}
	private static final Logger logger = Logger.getLogger(HttpUtil.class);
	private static String charSet = "UTF-8";
    public static String getCharSet() {
		return charSet;
	}

	public static void setCharSet(String charSet) {
		HttpUtil.charSet = charSet;
	}
	/**
	 * 静态方法，组装参数成为字符串
	 * @param param
	 * @param isNeedEncode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String packParamStrings(Map<String,String> param,boolean isNeedEncode) throws UnsupportedEncodingException{
	    String paramStr=null;
        if (param!=null&&!param.isEmpty()){
            StringBuilder sb=new StringBuilder("");
            for (Entry<String,String> entry:param.entrySet()){
                sb.append(entry.getKey())
                .append("=");
                	
                if (null!=entry.getValue()) if (isNeedEncode)
                    sb.append(URLEncoder.encode(entry.getValue(),charSet));
                else 
                    sb.append(entry.getValue());
                sb.append("&");
            }
            paramStr=sb.toString().replaceAll("\\&$", "");
        }else{
            paramStr=""; 
        }
	    return paramStr;
	}
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 发送url请求是否成功的结果
     */
	/**
	 * @update 修改参数param为map形式
	 * @author Defferson.Cheng
	 */
    public static String sendGet(String url,Map<String,String> param) throws Exception{
        String result;
        BufferedReader in = null;
        String urlNameString=url;
        try {
            String paramStr="?"+packParamStrings(param,true);
            logger.info("param:"+paramStr);
            urlNameString=urlNameString+paramStr;
            logger.info("发送的url数据："+urlNameString);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty(HttpConstant.ACCEPT.getKey(),HttpConstant.ACCEPT.getValue());
            connection.setRequestProperty(HttpConstant.CONNECTION.getKey(), HttpConstant.CONNECTION.getValue());
            connection.setRequestProperty(HttpConstant.USER_AGENT.getKey(), HttpConstant.USER_AGENT.getValue());
            // 建立实际的连接
            connection.connect();
            
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charSet));
            String line;
            String sss = "";
            while ((line = in.readLine()) != null) {
            	sss += line;
            }
            result=sss;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {// 使用finally块来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
            	throw new RuntimeException(e.getMessage());
            }
        }
        logger.debug(String.format("Send http-request by get with URL:%s", urlNameString));
        logger.debug(String.format("Recieve http-request result:%s", result));
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 发送url请求是否成功的结果
     */
    public static String sendPost(String url, Map<String,String> param) {
    	logger.info(String.format("向%s发送 POST请求", url));
    	OutputStream out = null;
        BufferedReader in = null;
        String result;
        String paramStr;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty(HttpConstant.ACCEPT.getKey(),HttpConstant.ACCEPT.getValue());
            conn.setRequestProperty(HttpConstant.CONNECTION.getKey(), HttpConstant.CONNECTION.getValue());
            conn.setRequestProperty(HttpConstant.CONTENT_TYPE.getKey(), HttpConstant.CONTENT_TYPE.getValue());
            conn.setRequestProperty(HttpConstant.USER_AGENT.getKey(), HttpConstant.USER_AGENT.getValue());
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = conn.getOutputStream();
            // 发送请求参数
            paramStr=packParamStrings(param,true);
            logger.info("param:"+paramStr);
			out.write(paramStr.getBytes(HttpConstant.CHARSET.getValue()));
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charSet));
            String line;
            String sss = "";
            while ((line = in.readLine()) != null) {
            	sss += line;
            }
            result = sss;
        } catch (Exception e) {
        	e.printStackTrace();
            logger.error("发送 POST请求出现异常！", e);
            throw new RuntimeException(e.getMessage(),e);
        } finally{ //使用finally块来关闭输出流、输入流
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            } catch(IOException ex){
                throw new RuntimeException(ex.getMessage(),ex);
            }
        }
        logger.debug(String.format("Send http-request by get with URL:%s and parameters:%s", url, paramStr));
        logger.debug(String.format("Recieve http-request result:%s", result));
        return result;
    }
    public static String sendPost(String url, String param) {
        OutputStream out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = conn.getOutputStream();
            // 发送请求参数
            out.write(param.getBytes(HttpConstant.CHARSET.getValue()));
//            out.write(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("",e);
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        logger.debug(String.format("Send http-request by get with URL:%s and parameters:%s", url, param));
        logger.debug(String.format("Recieve http-request result:%s", result));
        return result;
    }  
    
    public static String sendPost1(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");

            conn.setRequestProperty("content-type", "application/json;");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.write(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
}
