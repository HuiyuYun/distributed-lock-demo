package org.yunhuiyu.redlock.service;

import org.yunhuiyu.redlock.exception.UnableToAquireLockException;

/**
 * Create by: 云珲瑜
 * Date: 2020/2/16 16:53
 * Description:
 */

public interface DistributedLocker {

    /**
     * 获取锁
     *
     * @param resourceName 锁的名称
     * @param worker       获取锁后的处理类
     * @return 处理完具体的业务逻辑要返回的数据
     */
    <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws UnableToAquireLockException, Exception;

    <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime) throws UnableToAquireLockException, Exception;
}