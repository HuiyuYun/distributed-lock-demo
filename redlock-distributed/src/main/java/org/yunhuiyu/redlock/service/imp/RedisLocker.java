package org.yunhuiyu.redlock.service.imp;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yunhuiyu.redlock.service.AquiredLockWorker;
import org.yunhuiyu.redlock.common.RedissonConnector;
import org.yunhuiyu.redlock.exception.UnableToAquireLockException;
import org.yunhuiyu.redlock.service.DistributedLocker;

import java.util.concurrent.TimeUnit;

/**
 * Create by: 云珲瑜
 * Date: 2020/2/16 16:57
 * Description:
 */
@Service
public class RedisLocker implements DistributedLocker {

    private final static String LOCKER_PREFIX = "lock:";

    @Autowired
    RedissonConnector redissonConnector;

    public <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws Exception {
        return lock(resourceName, worker, 100);
    }

    public <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime) throws Exception {
        //获取redisson连接类
        RedissonClient redisson= redissonConnector.getClient();
        //传入LockName返回Lock实例
        RLock lock = redisson.getLock(LOCKER_PREFIX + resourceName);
        //尝试获取锁，waitTime尝试最长时间，lockTime最短时间，时间单位
        boolean success = lock.tryLock(100, lockTime, TimeUnit.SECONDS);
        if (success) {
            try {
                //获取到锁之后执行业务逻辑
                return worker.invokeAfterLockAquire();
            } finally {
                //释放锁
                lock.unlock();
            }
        }
        //获取锁失败抛出异常
        throw new UnableToAquireLockException();
    }

}
