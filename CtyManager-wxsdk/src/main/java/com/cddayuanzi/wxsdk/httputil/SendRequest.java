package com.cddayuanzi.wxsdk.httputil;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cddayuanzi.wxsdk.httputil.entity.SSLEntity;
import com.cddayuanzi.wxsdk.media.MediaType;
import com.google.gson.Gson;

/**
 * 微信接口请求
 * 
 * @author jiangbo
 *
 */
public class SendRequest {
	private static Logger logger = LoggerFactory.getLogger(SendRequest.class);

	public static <T> T sendRequest2WeiXin(String url, Class<T> resultClass) throws Exception {
		return sendRequest2WeiXin4Text(url, null, null, null, null, resultClass);
	}

	public static <T> T sendRequest2WeiXin4Json(String url, String json, Class<T> resultClass) throws Exception {
		return sendRequest2WeiXin4Json(url, json, null, resultClass);
	}

	public static <T> T sendRequest2WeiXin4Json(String url, String json, String charset, Class<T> resultClass) throws Exception {
		return sendRequest2WeiXin4Text(url, json, ContentType.APPLICATION_JSON, charset, null, resultClass);
	}

	public static <T> T sendRequest2WeiXin4Xml(String url, String xml, Class<T> resultClass) throws Exception {
		return sendRequest2WeiXin4Xml(url, xml, null, resultClass);
	}

	public static <T> T sendRequest2WeiXin4Xml(String url, String xml, String charset, Class<T> resultClass) throws Exception {
		return sendRequest2WeiXin4Text(url, xml, ContentType.APPLICATION_XML, charset, null, resultClass);
	}

	public static <T> T sendRequest2WeiXin4XmlWithSSLKey(String url, String xml, SSLEntity ssl, Class<T> resultClass)
			throws Exception {
		return sendRequest2WeiXin4Text(url, xml, ContentType.APPLICATION_XML, null, ssl, resultClass);
	}

	public static <T> T sendRequest2WeiXin4XmlWithSSLKey(String url, String xml, String charset, SSLEntity ssl,
			Class<T> resultClass) throws Exception {
		return sendRequest2WeiXin4Text(url, xml, ContentType.APPLICATION_XML, charset, ssl, resultClass);
	}

	public static <T> T sendRequest2WeiXin4Text(String url, String text, ContentType contentType, String charset, SSLEntity ssl,
			Class<T> resultClass) throws Exception {
		HttpRequestClient client = new HttpRequestClient();

		client.setHttpPostUrl(url);
		if (url.startsWith("https")) {
			if (null == ssl) {
				client.useSSLRequest();
			} else {
				client.useSSLRequest(ssl.getFilePath(), ssl.getPassword(), ssl.getCaType());
			}
		}
		if (!StringUtils.isEmpty(text)) {
			contentType = null == contentType ? ContentType.DEFAULT_TEXT : contentType;
			contentType = StringUtils.isEmpty(charset) ? contentType : contentType.withCharset(charset);

			client.setStringEntity(text, contentType);
		}
		client.execute();

		logger.info("请求返回：" + client.getResponseText());

		return new Gson().fromJson(client.getResponseText(), resultClass);
	}

	public static <T> T uploadMedia(String url, File file, MediaType mediaType, Class<T> resultClass) throws Exception {
		HttpRequestClient client = new HttpRequestClient();

		client.setHttpPostUrl(url);
		if (url.startsWith("https")) {
			client.useSSLRequest();
		}
		client.addFile("media", file, mediaType).execute();

		logger.info("请求返回：" + client.getResponseText());

		return new Gson().fromJson(client.getResponseText(), resultClass);
	}
	
	/**
	 * 下载多媒体文件
	 * @param url
	 * @param fileSavePath
	 * @return 返回文件保存本地的完整路径
	 * @throws Exception
	 */
	public static String downloadMedia(String url, String fileSavePath) throws Exception {
		HttpRequestClient client = new HttpRequestClient();

		client.setHttpPostUrl(url);
		if (url.startsWith("https")) {
			client.useSSLRequest();
		}
		
		client.download(fileSavePath, "utf-8", "utf-8");
		
		return client.getResponseText();
	}
}
