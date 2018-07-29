package com.play.thread;

public class ThreadInfo {

    /**
     * Threadlocal 并不是用来解决多线程环境下对共享变量对并发访问问题，而是提供了保持对象对方法，另外可以保存静态共享变量以免传参麻烦
     */
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    private void setId(){
        threadLocal.set(Thread.currentThread().getId());
    }

    private Long getId(){
        return threadLocal.get();
    }

    public static void main(String[] args) {

        ThreadInfo threadInfo = new ThreadInfo();
        threadInfo.setId();

        System.out.println(threadInfo.getId());

        Thread t = new Thread(){
            public void run(){
                threadInfo.setId();
                System.out.println(threadInfo.getId());
            }
        };
        t.start();

    }
}
