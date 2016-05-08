package com.yard.manager.platform.redis;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 简单使用jedis
 *
 * @author xmong
 */
public class TestJedis {
    // redis服务器主机
    static String host = "192.168.202.29";
    // 端口号
    static int port = 6379;
    public static void main(String[] args) {
      /* // 根据redis主机和端口号实例化Jedis对象
       Jedis jedis = new Jedis(host, port);
       // 添加key-value对象，如果key对象存在就覆盖该对象
       jedis.set("name", "xmong");
       // 查取key的value值，如果key不存在返回null
       String value = jedis.get("name");
       System.out.println(value);
       // 删除key-value对象，如果key不存在则忽略此操作
       jedis.del("name");
       // 判断key是否存在，不存在返回false存在返回true
       jedis.exists("name");*/
    	JedisPoolConfig config = new JedisPoolConfig();
    	JedisPool pool = new JedisPool(config, host);
    	Jedis jedis = pool.getResource();
    	try {
    	    /// 开始使用
    	    jedis.set("foo", "bar");
    	    String foobar = jedis.get("foo");
    	    jedis.zadd("sose", 0, "car"); 
    	    jedis.zadd("sose", 0, "bike"); 
    	    Set<String> sose = jedis.zrange("sose", 0, -1);
    	} finally {
    	    /// 使用完后，将连接放回连接池
    	    if (null != jedis) {
    	        jedis.close();
    	    }
    	}
    	/// 应用退出时，关闭连接池:
    	pool.destroy();
    }
}
