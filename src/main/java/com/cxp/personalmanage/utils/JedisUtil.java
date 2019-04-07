package com.cxp.personalmanage.utils;

import java.util.Collections;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class JedisUtil {

	private static JedisPool jedisPool;

	@Autowired
	public void setJedisPool(JedisPool jedisPool){
		this.jedisPool = jedisPool;
	}

	private static  Jedis getResource() {
		return jedisPool.getResource();
	}

	public static  byte[] set(byte[] key, byte[] value) {
		Jedis jedis = getResource();
		try {
			jedis.set(key, value);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return value;
	}

	public static  void expire(byte[] key, int expireTime) {
		Jedis jedis = getResource();
		try {
			jedis.expire(key, expireTime);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
	}

	public static  byte[] get(byte[] key) {
		Jedis jedis = getResource();
		try {
			return jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return null;
	}

	public static  void del(byte[] key) {
		Jedis jedis = getResource();
		try {
			jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
	}

	public static  Set<byte[]> keys(String key) {
		Jedis jedis = getResource();
		try {
			Set<byte[]> keys = jedis.keys((key + "#").getBytes());
			return keys;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return null;
	}

	private static final String LOCK_SUCCESS = "OK";
	private static final String SET_IF_NOT_EXISTS = "NX";
	private static final String SET_WITH_EXPIRE_TIME="PX";
	private static final Long RELEASE_SUCCESS=1L;

	/**
	 * 尝试获取分布式锁
	 * @param lockKey   锁
	 * @param requestId	请求标识
	 * @param expireTime	超时时间
	 * @return 返回是否获取成功
	 */
	public static boolean tryDistributedLock(String lockKey,String requestId,long expireTime){
		Jedis jedis=null;
		try {
			jedis=getResource();
			String result = jedis.set(lockKey,requestId,SET_IF_NOT_EXISTS,SET_WITH_EXPIRE_TIME,expireTime);
			if (LOCK_SUCCESS.equals(result)){
				return true;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 释放分布式锁
	 * @param jedis Redis客户端
	 * @param lockKey 锁
	 * @param requestId 请求标识
	 * @return 是否释放成功
	 */
	public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
		if (RELEASE_SUCCESS.equals(result)) {
			return true;
		}
		return false;
	}
}
