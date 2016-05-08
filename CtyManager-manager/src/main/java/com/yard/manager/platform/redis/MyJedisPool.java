package com.yard.manager.platform.redis;

import java.util.ResourceBundle;

import com.yard.manager.base.constant.ManagerConstant;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * jedis池使用
 *
 * @author xmong
 */
public class MyJedisPool {
	// jedis池
    private static JedisPool pool;
   
    // 静态代码初始化池配置
    static {
       // 加载redis配置文件
       ResourceBundle bundle = ResourceBundle.getBundle("redis");
       if (bundle == null) {
           throw new IllegalArgumentException("[redis.properties] is not found!");
       }
       
       // 创建jedis池配置实例
       JedisPoolConfig config = new JedisPoolConfig();
       
       // 设置池配置项值
       // config.setMaxActive(Integer.valueOf(bundle.getString("redis.pool.maxActive")));
       config.setMaxIdle(Integer.valueOf(bundle.getString("redis.pool.maxIdle")));
       config.setMaxWaitMillis(Long.valueOf(bundle.getString("redis.pool.maxWait")));
       config.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow")));
       config.setTestOnReturn(Boolean.valueOf(bundle.getString("redis.pool.testOnReturn")));
       
       // 根据配置实例化jedis池
       pool = new JedisPool(config, bundle.getString("redis.ip"), Integer.parseInt(bundle.getString("redis.port")), 3000, bundle.getString("redis.password"));
    }
 
    /**
     * 活动通知未读数缓存
     */
    public static long noticeUnread(Integer userId) {
        Jedis jedis = pool.getResource();
        System.out.println("======pool -----------      :"+pool);
        String key = RedisKey.getKey(ManagerConstant.NOTICE_MSG_COUNT, String.valueOf(userId));
        return jedis.hincrBy(key, ManagerConstant.NOT_READ_COUNT, 1);
    }
    
    /**
     * 测试jedis池方法
     */
    public static void test1() {
       // 从jedis池中获取一个jedis实例
       Jedis jedis = pool.getResource();
 
       // 获取jedis实例后可以对redis服务进行一系列的操作
  String key = RedisKey.getKey("MSG_COUNT", String.valueOf(1007));
//	   jedis.hset(key, "MSG_COUNT", "1");
//	   System.out.println("--->>"+key);
//       System.out.println(jedis.get(key));
       //jedis.del("name");
       //jedis.hincrBy(key, "MSG_COUNT", 3);
       System.out.println(jedis.exists("name"));
       String values = jedis.hget(key, "MSG_COUNT");
       System.out.println("values:"+values);
       // 释放对象池，即获取jedis实例使用后要将对象还回去
       pool.returnResource(jedis);
      /* jedis.hget(key, "MSG_COUNT");
       jedis.hset(key, "MSG_COUNT", "1");
       jedis.hincrBy(key, "MSG_COUNT", 2);*/
    }
 
    public static void main(String[] args) {
//       test1();// 执行test1方法
    	long s = MyJedisPool.noticeUnread(1);
    	System.out.println("====>>"+s);
    }
}
