package com.cddayuanzi.wxsdk.redpack.manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import com.cddayuanzi.wxsdk.Md5Util;
import com.cddayuanzi.wxsdk.httputil.HttpRequestClient;
import com.cddayuanzi.wxsdk.httputil.SendRequest;
import com.cddayuanzi.wxsdk.httputil.entity.SSLEntity;
import com.cddayuanzi.wxsdk.redpack.entity.RedPack;
import com.cddayuanzi.wxsdk.redpack.entity.rp.RpActivity;
import com.cddayuanzi.wxsdk.redpack.entity.rp.RpPackPram;
import com.cddayuanzi.wxsdk.redpack.entity.rp.RpShops;
import com.cddayuanzi.wxsdk.result.BaseJsonRespEntity;

public class RedPackUtil {
	public static String send_redpack_url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";

    /** 
     * 生成随机字符串 
     * @return 
     */  
    public static String createNonceStr() {  
         return UUID.randomUUID().toString().toUpperCase().replace("-", "");  
    }
    
    /** 
     * 生成商户订单号 
     * @param mch_id  商户号 
     * @param openid  
     * @return 
     */  
    public static String createBillNo(String mch_id,String openid){  
        Date dt=new Date();  
        SimpleDateFormat df = new SimpleDateFormat("yyyymmdd");  
        String nowTime= df.format(dt);  
        int length = 10;  
        return mch_id + nowTime + getRandomNum(length);  
    }

    /** 
     * 生成特定位数的随机数字 
     * @param length 
     * @return 
     */  
    private static String getRandomNum(int length) {  
        String val = "";  
        Random random = new Random();  
        for (int i = 0; i < length; i++) {  
            val += String.valueOf(random.nextInt(10));  
        }  
        return val;  
    } 
    
    /** 
     * 对请求参数名ASCII码从小到大排序后签名 
     * @param params 
     */  
    public static void sign(SortedMap<String, String> params, String key){  
        Set<Entry<String,String>> entrys=params.entrySet();    
        Iterator<Entry<String,String>> it=entrys.iterator();    
        StringBuffer result = new StringBuffer();  
        while(it.hasNext())    
        {    
           Entry<String,String> entry=it.next();    
           result.append(entry.getKey())  
                 .append("=")  
                 .append(entry.getValue())  
                 .append("&");  
        }    

        result.append("key=").append(key);  
        params.put("sign", Md5Util.getMD5(result.toString()).toUpperCase());  
    }  
    
    /** 
     * 生成提交给微信服务器的xml格式参数 
     * @param params 
     * @return 
     */  
    @SuppressWarnings("rawtypes")
	public static String getRequestXml(SortedMap<String,String> params){  
            StringBuffer sb = new StringBuffer();  
                sb.append("<xml>");  
                Set es = params.entrySet();  
                Iterator it = es.iterator();  
                while(it.hasNext()) {  
                     Map.Entry entry = (Map.Entry)it.next();  
                     String k = (String)entry.getKey();  
                     String v = (String)entry.getValue();  
                     sb.append("<"+k+">"+v+"</"+k+">");  
                 }  
                sb.append("</xml>");  
                return sb.toString();  
     }  
    
    /** 
     * 创建map 
     * @param billNo 
     * @param openid 
     * @param userId 
     * @param amount 
     * @return 
     */  
    public static SortedMap<String, String> createMap(RedPack rp){  
        SortedMap<String, String> params = new TreeMap<String, String>();  
        params.put("wxappid",rp.getWxappid());  
        params.put("nonce_str",createNonceStr());  
        params.put("mch_billno",rp.getMch_billno());  
        params.put("mch_id", rp.getMch_id());  
        params.put("nick_name", rp.getNick_name());  
        params.put("send_name", rp.getSend_name());  
        params.put("re_openid", rp.getRe_openid());  
        params.put("total_amount", rp.getTotal_amount()+"");  
        params.put("min_value", rp.getMin_value()+"");  
        params.put("max_value", rp.getMax_value()+"");  
        params.put("total_num", rp.getTotal_num()+"");  
        params.put("wishing", rp.getWishing());  
        params.put("client_ip", rp.getClient_ip());  
        params.put("act_name", rp.getAct_name());  
        params.put("remark", rp.getRemark());  
        return params;  
    } 
    
    public static String createXml(RedPack rp) {
    	SortedMap<String, String> sortMap = createMap(rp);
    	sign(sortMap, "8n0kABjxer5V671UCwYOHDeUhqv14Rij");
    	String requestXml = getRequestXml(sortMap);
    	return requestXml;
    }
    
    /**
     * 封装商户信息
     * 
     * @param mch_id 商户号
     * @param wxappid 公众账号appid
     * @param nick_name 提供方名称
     * @param send_name 商户名称
     * @return
     */
    public static RedPack getShops(String mch_id, String wxappid, String nick_name,String send_name) {
    	RedPack rp =  new RedPack();
    	
    	rp.setMch_id(mch_id);
    	rp.setWxappid(wxappid);
    	rp.setNick_name(nick_name);
    	rp.setSend_name(send_name);
    	
    	return rp;
    }
    
    /**
     *  封装与红包相关参数信息
     *  
     * @param rp
     * @param total_amount 付款金额
     * @param min_value 最小红包金额
     * @param max_value 最大红包金额
     * @param total_num 红包发放总人数
     * @param wishing 红包祝福语
     * @param re_openid 用户openid
     * @param client_ip Ip地址
     * @param act_name 活动名称
     * @param remark 备注
     * @return
     */
    public static RedPack getPackPram(RedPack rp, int total_amount, int min_value, int max_value, int total_num,
    		String wishing, String re_openid, String client_ip, String act_name, String remark) {
    	if(rp == null){
    		rp = new RedPack();
    	}
    	rp.setTotal_amount(total_amount);
    	rp.setMin_value(min_value);
    	rp.setMax_value(max_value);
    	rp.setTotal_num(total_num);
    	rp.setWishing(wishing);
    	rp.setRe_openid(re_openid);
    	rp.setClient_ip(client_ip);
    	rp.setAct_name(act_name);
    	rp.setRemark(remark);
    	
    	rp.setMch_billno(createBillNo(rp.getMch_id(), re_openid));
    	
    	return rp;
    }
    
    /**
     * 
     * 红包发送入口
     * 
     */
    public static void sendRedPack(RpShops shops, RpPackPram packPram, RpActivity rpActivity,String ip) {
    	//封装商户信息
    	RedPack rp = getShops(shops.getMch_id(), shops.getWxappid(), shops.getNick_name(), shops.getSend_name());
    	//封装红包信息
    	getPackPram(rp, packPram.getTotal_amount(), packPram.getTotal_amount(), packPram.getTotal_amount(), 1, 
    			packPram.getWishing(), packPram.getRe_openid(), ip, rpActivity.getAct_name(), rpActivity.getRemark());
		String xmls = createXml(rp);
		//发送HTTP请求
		try {
			SSLEntity ssl = new SSLEntity();
			ssl.setFilePath("D:/apiclient_cert.p12");
			ssl.setPassword("1236699502");
			ssl.setCaType(HttpRequestClient.CATYPE_PKCS12);
			SendRequest.sendRequest2WeiXin4XmlWithSSLKey(send_redpack_url, xmls, ssl, BaseJsonRespEntity.class);
		} catch (Exception e) {
		    e.printStackTrace();
		}
    }
    
    public static void main(String[] args) {
    	RpShops shops = new RpShops();
    	shops.setMch_id("1236699502");
    	shops.setNick_name("蓝盒子科技公众平台");
    	shops.setSend_name("重庆蓝盒子科技");
    	shops.setWxappid("wx4ae9b464531f45d5");
    	
    	RpPackPram pk = new RpPackPram();
    	pk.setRe_openid("oNmyCuPqVh7PHiOflhuDAmQUFVhQ");
    	pk.setTotal_amount(10000);
    	pk.setWishing("新年到，红包来！");
	
    	RpActivity act = new RpActivity();
    	act.setAct_name("任性活动");
    	act.setRemark("不要问我为什么 ，有钱任性！");
    	
    	sendRedPack(shops, pk, act, "192.168.1.11");
    }
}
