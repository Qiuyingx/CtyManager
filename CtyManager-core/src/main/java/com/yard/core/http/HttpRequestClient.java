package com.yard.core.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * http请求工具类，参照apache httpclient 4.*
 * @author jiangbo
 *
 */
public class HttpRequestClient {
	public static final String CATYPE_PKCS12 = "PKCS12";
	public static final String CHARSET_UTF8 = "utf-8";

	private HttpPost httpPost; // POST请求对象
	private HttpGet httpGet; // GET请求对象
	private SSLConnectionSocketFactory ssl; // ssl连接对象
	private StringEntity strEntity; // 请求参数（单一参数非键值对）
	private int socketTimeout = 5000; // 套接字超时
	private int connectTimeout = 5000; // 连接超时
	private int connectionRequestTimeout = 5000; // 请求超时
	private List<NameValuePair> params; // 请求参数（多参数，键值对）
	private String responseText = ""; // 请求返回文本

	/**
	 * 用于重复使用该对象时清除相关实例变量
	 */
	public void clean() {
		httpPost = null;
		httpGet = null;
		ssl = null;
		strEntity = null;
		params = null;
		responseText = "";
	}
	
	/**
	 * 设置POST请求
	 * @param url
	 * @return
	 */
	public HttpRequestClient setHttpPostUrl(String url) {
		httpPost = new HttpPost(url);
		return this;
	}

	/**
	 * 设置GET请求
	 * @param url
	 * @return
	 */
	public HttpRequestClient setHttpGetUrl(String url) {
		httpGet = new HttpGet(url);
		return this;
	}

	/**
	 * 启动https连接（公用，不带证书）
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 */
	public HttpRequestClient useSSLRequest() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
			// 信任所有
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		}).build();

		ssl = new SSLConnectionSocketFactory(sslContext);

		return this;
	}

	/**
	 * 启动https连接（带CA证书）
	 * @param caPath
	 * @param caPwd
	 * @param caType
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public HttpRequestClient useSSLRequest(String caPath, String caPwd, String caType) throws KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, CertificateException, IOException {
		return useSSLRequest(new File(caPath), caPwd, caType);
	}

	/**
	 * 启动https连接（带CA证书）
	 * @param caFile
	 * @param caPwd
	 * @param caType
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public HttpRequestClient useSSLRequest(File caFile, String caPwd, String caType) throws KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, CertificateException, IOException {
		KeyStore keyStore = KeyStore.getInstance(caType);
		FileInputStream instream = new FileInputStream(caFile);
		try {
			keyStore.load(instream, caPwd.toCharArray());
		} finally {
			instream.close();
		}

		SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, caPwd.toCharArray()).build();
		ssl = new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

		return this;
	}

	/**
	 * 设置单一请求参数，一般是一个对象
	 * @param obj
	 * @param objCharSet
	 * @return
	 */
	public HttpRequestClient setStringEntity(String obj, ContentType contentType) {
		strEntity = new StringEntity(obj, contentType);
		return this;
	}
	
	/**
	 * 设置单一请求参数，一般是一个对象
	 * @param obj
	 * @param objCharSet
	 * @return
	 */
	public HttpRequestClient setStringEntity(String obj, String charset) {
		strEntity = new StringEntity(obj, StringUtils.isEmpty(charset) ? CHARSET_UTF8 : charset);
		return this;
	}
	
	/**
	 * 设置套接字超时
	 * @param socketTimeout
	 * @return
	 */
	public HttpRequestClient setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
		return this;
	}

	/**
	 * 设置连接超时
	 * @param connectTimeout
	 * @return
	 */
	public HttpRequestClient setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
		return this;
	}

	/**
	 * 设置请求超时
	 * @param connectionRequestTimeout
	 * @return
	 */
	public HttpRequestClient setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
		return this;
	}

	/**
	 * 添加一对键值对参数
	 * @param key
	 * @param value
	 * @return
	 */
	public HttpRequestClient addParams(String key, String value) {
		if (null == params) {
			params = new ArrayList<NameValuePair>();
		}
		params.add(new BasicNameValuePair(key, value));

		return this;
	}

	/**
	 * 添加一组键值对参数
	 * @param map
	 * @return
	 */
	public HttpRequestClient addParams(Map<String, String> map) {
		if (null != map && map.size() > 0) {
			if (null == params) {
				params = new ArrayList<NameValuePair>();
			}

			Iterator<Entry<String, String>> it = map.entrySet().iterator();

			while (it.hasNext()) {
				Entry<String, String> entry = it.next();

				params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}

		return this;
	}

	/**
	 * 取请求返回文本
	 * @return
	 */
	public String getResponseText() {
		return responseText;
	}

	/**
	 * 执行请求
	 * @param reqCharset
	 * @param respCharset
	 * @return
	 * @throws Exception
	 */
	public boolean execute() throws Exception {
		return execute(null, null);
	}
	
	public boolean execute(String reqCharset, String respCharset) throws Exception {
		if (null == httpPost && null == httpGet) {
			throw new Exception("未设置请求路径！");
		}

		// 生成超时配置对象
		RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout).build();

		// 生成http实例
		HttpClientBuilder clientBuilder = HttpClients.custom();
		// 对http实例配置超时
		clientBuilder.setDefaultRequestConfig(defaultRequestConfig);

		if (null != ssl) {
			// 对http实例配置ssl用于https请求
			clientBuilder.setSSLSocketFactory(ssl);
		}

		// 生成客户端
		CloseableHttpClient client = clientBuilder.build();
		CloseableHttpResponse response = null;

		RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig).build();

		if (null != httpPost) {
			if (null != strEntity && null != params && params.size() > 0) {
				throw new Exception("POST请求参数只能选择一种！");
			}

			if (null != strEntity) {
				httpPost.setEntity(strEntity);
			} else if (null != params && params.size() > 0){
				httpPost.setEntity(new UrlEncodedFormEntity(params, StringUtils.isEmpty(reqCharset) ? CHARSET_UTF8 : reqCharset));
			}

			httpPost.setConfig(requestConfig);

			// POST请求
			response = client.execute(httpPost);
		} else {
			httpGet.setConfig(requestConfig);

			// GET请求
			response = client.execute(httpGet);
		}

		// 请求返回状态码
		boolean status = response.getStatusLine().getStatusCode() == 200;
		
		StringBuilder respText = new StringBuilder();
		if (status) {
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),
						StringUtils.isEmpty(respCharset) ? CHARSET_UTF8 : respCharset));
				String text;
				while ((text = bufferedReader.readLine()) != null) {
					respText.append(text);
				}

				// 释放资源和关闭底层的流
				EntityUtils.consume(entity);
			}
		}

		response.close();

		client.close();

		responseText = respText.toString();

		return status;
	}
	
	public static void main(String[] args) throws Exception {
		/*String json = "{\"filter\":{\"group_id\":\"0\",\"is_to_all\":false},\"mpnews\":{\"media_id\":\"qM3HNko5AbxH0AQndfKUwYaG0zHyydjKsIG2FRREKGgW73lsHvTsYZA-53scrNNC\"},\"msgtype\":\"mpnews\"}";
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=T2Jqru0sw0_AXLwG_pcDBFd6KXWRCgLbCZAiXsOTz4KA_YyqDyNQMCbu5Ro9U4W8W6AEprFGsUjaIaFngN_R0Ql2qd69yyyg02s0LPWmUOM";
*/
		String url = "https://a1.easemob.com/linju/linjudev/token";
		String json = "{\"grant_type\" : \"client_credentials\",\"client_id\":\"YXA6AOhAcAEVEeWyhFWlw2tMtg\",\"client_secret\":\"YXA6_Ddn1KBpdVFm6hiKW1CdOai6jok\"}";

		
		HttpRequestClient client = new HttpRequestClient();
		client.setHttpPostUrl(url).setStringEntity(json, "UTF-8").execute();
		
		System.out.println(client.getResponseText());
	}
}
