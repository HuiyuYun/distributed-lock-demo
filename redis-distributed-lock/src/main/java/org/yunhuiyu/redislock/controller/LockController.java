package org.yunhuiyu.redislock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yunhuiyu.redislock.common.Lock;
import org.yunhuiyu.redislock.handler.DistributedLockHandler;

/**
 * Create by: 云珲瑜
 * Date: 2020/2/16 10:29
 * Description:
 */
@RestController
public class LockController {

    @Autowired
    private DistributedLockHandler handler;

    @RequestMapping("/do")
    public String doSomething(){
        Lock lock = new Lock("zhangsan", "123");
        if (handler.tryLock(lock)) {
            System.out.println("程序正在执行");
            try {
                Thread.sleep(4000);
                handler.releaseLock(lock);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else System.out.println("获取失败！");
        return "hello lock" + System.currentTimeMillis();
    }

//    public static void main(String[] args) {
//        Jedis jedis = new Jedis("127.0.0.1", 6379);
//        System.out.println("redis is ok +++++" + jedis.ping());
//    }

}
