package org.yunhuiyu.redlock.service;

/**
 * Create by: 云珲瑜
 * Date: 2020/2/16 16:52
 * Description: 获取锁后需要处理的逻辑
 */

public interface AquiredLockWorker<T> {
    T invokeAfterLockAquire() throws Exception;
}
