package com.cxp.personalmanage.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;
import javax.annotation.PostConstruct;

@Component
public class RedisUtils {

	private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

	private static RedisTemplate<String, Object> redisTemplate;

    private RedisUtils(){

    }

    @PostConstruct
    public void init(){
        logger.info("初始化RedisUtils成功");
    }

	@Autowired
	@Qualifier(value = "redisTemplate")
	public void setRedisTemlate(RedisTemplate<String, Object> redisTemplate) {
		RedisUtils.redisTemplate = redisTemplate;
        logger.info("{}", RedisUtils.redisTemplate);
	}

	/**
	 * 写入缓存
	 */
	public static boolean set(final String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<String, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 写入缓存设置时效时间
	 */
	public static boolean set(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<String, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 批量删除对应的Key
	 * 
	 * @param keys
	 */
	public static void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * 批量删除key
	 */
	public static void removePattern(final String pattern) {
		Set<String> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0) {
			redisTemplate.delete(keys);
		}
	}

	/**
	 * 删除对应的value
	 * 
	 * @param key
	 */
	public static void remove(final String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * 判断缓存中是否有对就的value
	 * 
	 * @param key
	 * @return
	 */
	public static boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 读取缓存
	 */
	public static Object get(final String key) {
		Object result = null;
		ValueOperations<String, Object> operations = redisTemplate.opsForValue();
		result = operations.get(key);
		return result;
	}

	/**
	 * 哈希添加
	 * 
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public static void hmSet(String key, Object hashKey, Object value) {
		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
		hash.put(key, hashKey, value);
	}

	/**
	 * 哈希读取数据
	 */
	public static Object hmGet(String key, Object hashKey) {
		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
		return hash.get(key, hashKey);
	}

	/**
	 * 列表添加
	 * 
	 * @param key
	 * @param value
	 */
	public static void IPush(String key, Object value) {
		ListOperations<String, Object> list = redisTemplate.opsForList();
		list.rightPush(key, value);
	}

	/**
	 * 列表获取
	 * 
	 * @param key
	 * @param l
	 * @param l1
	 * @return
	 */
	public static List<Object> IRange(String key, long l, long l1) {
		ListOperations<String, Object> list = redisTemplate.opsForList();
		return list.range(key, l, l1);
	}

	/**
	 * 集合添加
	 * 
	 * @param key
	 * @param value
	 */
	public static void add(String key, Object value) {
		SetOperations<String, Object> set = redisTemplate.opsForSet();
		set.add(key, value);
	}

	/**
	 * 集合获取
	 * 
	 * @param key
	 * @return
	 */
	public static Set<Object> getMember(String key) {
		SetOperations<String, Object> set = redisTemplate.opsForSet();
		return set.members(key);
	}

	/**
	 * 有序集合添加
	 * 
	 * @param key
	 * @param value
	 * @param scoure
	 */
	public static void zAdd(String key, Object value, double scoure) {
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		zset.add(key, value, scoure);
	}

	/**
	 * 有序集合获取
	 * 
	 * @param key
	 * @param scoure
	 * @param scoure1
	 * @return
	 */
	public static Set<Object> rangeByScore(String key, double scoure, double scoure1) {
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		return zset.rangeByScore(key, scoure, scoure1);
	}

	public static final String UNLOCK_LUA;

	static {
		StringBuilder sb = new StringBuilder();
		sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
		sb.append("then ");
		sb.append("    return redis.call(\"del\",KEYS[1]) ");
		sb.append("else ");
		sb.append("    return 0 ");
		sb.append("end ");
		UNLOCK_LUA = sb.toString();
	}

    /**
     * 设置分布式锁
     * @param key
     * @param expire
     * @return
     */
	public static boolean setLock(String key,String requestId, long expire) {
		try {
			RedisCallback<String> callback = (connection) -> {
				JedisCommands commands = (JedisCommands) connection.getNativeConnection();
				return commands.set(key, requestId, "NX", "PX", expire);
			};
			String result = redisTemplate.execute(callback);

			return !StringUtils.isEmpty(result);
		} catch (Exception e) {
			logger.error("set redis occured an exception", e);
		}
		return false;
	}

	public static String getLock(String key) {
		try {
			RedisCallback<String> callback = (connection) -> {
				JedisCommands commands = (JedisCommands) connection.getNativeConnection();
				return commands.get(key);
			};
			String result = redisTemplate.execute(callback);
			return result;
		} catch (Exception e) {
			logger.error("get redis occured an exception", e);
		}
		return "";
	}

	public static boolean releaseLock(String key,String requestId) {
		// 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
		try {
			List<String> keys = new ArrayList<>();
			keys.add(key);
			List<String> args = new ArrayList<>();
			args.add(requestId);

			// 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
			// spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
			RedisCallback<Long> callback = (connection) -> {
				Object nativeConnection = connection.getNativeConnection();
				// 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
				// 集群模式
				if (nativeConnection instanceof JedisCluster) {
					return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, keys, args);
				}

				// 单机模式
				else if (nativeConnection instanceof Jedis) {
					return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, keys, args);
				}
				return 0L;
			};
			Long result = redisTemplate.execute(callback);

			return result != null && result > 0;
		} catch (Exception e) {
			logger.error("release lock occured an exception", e);
		} finally {
			// 清除掉ThreadLocal中的数据，避免内存溢出
			//lockFlag.remove();
		}
		return false;
	}
}
