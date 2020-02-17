package org.yunhuiyu.redislock.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.yunhuiyu.redislock.common.Lock;

import java.util.concurrent.TimeUnit;

/**
 * Create by: 云珲瑜
 * Date: 2020/2/16 10:02
 * Description:
 */
@Component
public class DistributedLockHandler {

    private static final Logger logger = LoggerFactory.getLogger(DistributedLockHandler.class);
    private final static long LOCK_EXPIRE = 10 * 1000L;//单个业务持有锁的时间10s，防止死锁
    private final static long LOCK_TRY_INTERVAL = 900L;//默认900ms尝试一次，为了方便测试，实际肯定要小很多
    private final static long LOCK_TRY_TIMEOUT = 7 * 1000L;//默认尝试7s

    @Autowired
    private StringRedisTemplate template;

    /**
     * 尝试获取全局锁，可根据业务需要自行传入参数
     */
    public boolean tryLock(Lock lock) {
        return getLock(lock, LOCK_TRY_TIMEOUT, LOCK_TRY_INTERVAL, LOCK_EXPIRE);
    }
    public boolean tryLock(Lock lock, Long timeout) {
        return getLock(lock, timeout, LOCK_TRY_INTERVAL, LOCK_EXPIRE);
    }
    public boolean tryLock(Lock lock, Long timeout, Long interval) {
        return getLock(lock, timeout, interval, LOCK_EXPIRE);
    }
    public boolean tryLock(Lock lock, Long timeout, Long interval, Long expire) {
        return getLock(lock, timeout, interval, expire);
    }

    /**
     * 获取分布式锁
     */
    private boolean getLock(Lock lock, long timeout, long tryInterval, long lockExpireTime) {
        try {
            if (StringUtils.isEmpty(lock.getName()) || StringUtils.isEmpty(lock.getValue())) {
                return false;
            }
            long startTime = System.currentTimeMillis();
            do{
                if (!template.hasKey(lock.getName())) {//不存在该锁
                    ValueOperations<String, String> ops = template.opsForValue();
                    ops.set(lock.getName(), lock.getValue(), lockExpireTime, TimeUnit.MILLISECONDS);
                    return true;
                } else {//存在锁
                    logger.debug("lock is exist！！");
                    System.out.println("lock is exist！！");
                }
                if (System.currentTimeMillis() - startTime > timeout) {//尝试超过了设定值之后直接跳出循环
                    System.out.println("尝试超时！");
                    return false;
                }
                Thread.sleep(tryInterval);
            }
            while (true) ;
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            System.out.println("异常！");
            return false;
        }
    }

    /**
     * 释放锁
     */
    public void releaseLock(Lock lock){
        if(!StringUtils.isEmpty(lock.getName())){
            template.delete(lock.getName());
        }
    }

}
