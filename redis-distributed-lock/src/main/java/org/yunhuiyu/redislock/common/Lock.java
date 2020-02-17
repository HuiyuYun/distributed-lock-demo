package org.yunhuiyu.redislock.common;

/**
 * Create by: 云珲瑜
 * Date: 2020/2/16 9:59
 * Description: 全局锁
 */
public class Lock {

    private String name;
    private String value;

    public Lock(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
