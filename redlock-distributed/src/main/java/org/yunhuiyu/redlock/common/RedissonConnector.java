package org.yunhuiyu.redlock.common;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Create by: 云珲瑜
 * Date: 2020/2/16 16:55
 * Description: 连接redisson的类
 */
@Component
public class RedissonConnector {

    RedissonClient redisson;

    @PostConstruct
    public void init(){
        redisson = Redisson.create();
    }

    public RedissonClient getClient(){
        return redisson;
    }

}
