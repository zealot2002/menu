package com.zzy.home.view;

/**
 * @author zzy
 * @date 2018/9/5
 */

public class Test {
    public static Test getInstance() {
        return LazyHolder.ourInstance;
    }

    private static class LazyHolder {
        private static final Test ourInstance = new Test();
    }

    private Test() {
    }
}
