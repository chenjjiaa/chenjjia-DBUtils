package com.chenjjiaa.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Author：  chenjunjia
 * Date：    2022/10/12 19:05
 * WeChat：  China_JoJo_
 * Blog：    https://juejin.cn/user/1856417285289304/posts
 * Github：  https://github.com/chenjjiaa
 */
public class ThreadLocal<T> {

    Map<Thread, T> threadLocalMap = new HashMap();

    public void set(T t) {
        threadLocalMap.put(Thread.currentThread(), t);
    }

    public T get() {
        return threadLocalMap.get(Thread.currentThread());
    }

    public void remove() {
        threadLocalMap.remove(Thread.currentThread());
    }
}
