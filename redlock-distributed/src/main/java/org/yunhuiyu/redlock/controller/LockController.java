package org.yunhuiyu.redlock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yunhuiyu.redlock.service.AquiredLockWorker;
import org.yunhuiyu.redlock.service.DistributedLocker;

/**
 * Create by: 云珲瑜
 * Date: 2020/2/16 10:29
 * Description:
 */
@RestController
public class LockController {

    @Autowired
    private DistributedLocker distributedLocker;

    @RequestMapping("/do")
    public String doSomething() throws Exception {
        distributedLocker.lock("test",new AquiredLockWorker<Object>() {
            public Object invokeAfterLockAquire() {
                try {
                    System.out.println("执行方法！");
                    Thread.sleep(5000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

        });
        return "hello redlock!";
    }

//    public static void main(String[] args) {
//        Jedis jedis = new Jedis("127.0.0.1", 6379);
//        System.out.println("redis is ok +++++" + jedis.ping());
//    }

}
