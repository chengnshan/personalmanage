package com.cxp.personalmanage;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.cxp.personalmanage.utils.RedisUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class PersonalmanageApplicationTests {

	@Autowired
	@Qualifier(value = "redisTemplate")
	private RedisTemplate<String, Object> redisTemplate;

	@Test
	public void contextLoads() {
		redisTemplate.opsForValue().set("abc", "张三丰111");
		System.out.println("设置成功!");

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				String uuid = UUID.randomUUID().toString();
				boolean lockSuc = RedisUtils.setLock("redisLock",uuid, 20000L);
				System.out.println(lockSuc);
				if (lockSuc) {
					System.out.println("t1成功设置锁了");
				}else {
					System.out.println(RedisUtils.getLock("redisLock"));
					System.out.println("t2没有成功获取到锁");
				}
				try {
					Thread.sleep(5000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally{
					RedisUtils.releaseLock("redisLock", uuid);
				}
			}
		});
		

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				String uuid = UUID.randomUUID().toString();
				boolean lockSuc = RedisUtils.setLock("redisLock", uuid,20000L);
				System.out.println(lockSuc);
				if (lockSuc) {
					System.out.println("t2成功设置锁了");
				}else {
					System.out.println(RedisUtils.getLock("redisLock"));
					System.out.println("t2没有成功获取到锁");
				}

				try {
					Thread.sleep(5000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally{
					RedisUtils.releaseLock("redisLock", uuid);
				}
			}
		});
		
		t1.start();
		t2.start();

		try {
			Thread.sleep(30000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
