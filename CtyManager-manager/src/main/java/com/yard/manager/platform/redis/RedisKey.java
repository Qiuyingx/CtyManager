package com.yard.manager.platform.redis;

/**
 * 存入redis中的key
 *
 * @author : leihc
 * @date : 2015年5月2日
 * 
 */
public class RedisKey {

	/**
	 * 根键
	 */
	public static String root = "COURTYARD";
	/**
	 * 兴趣
	 */
	public static String family_interest = "INTEREST";
	
	/**
	 * 
	 * @param family
	 * @param key
	 * @return COURTYARD#family#key
	 */
	public static String getKey(String family, String key){
		return root+"#" +family+"#"+key;
	}
}
