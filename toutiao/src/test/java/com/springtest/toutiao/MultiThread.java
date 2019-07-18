package com.springtest.toutiao;

import java.util.concurrent.*;

class Producer implements Runnable {
    private BlockingQueue<String> q;//采用BlockingQueue队列的形式进行存储事件

    public Producer(BlockingQueue<String> q) {
        this.q = q;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; ++i) {
                Thread.sleep(100);
                q.put(String.valueOf(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {
    private BlockingQueue<String> q;

    public Consumer(BlockingQueue<String> q) {
        this.q = q;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println(Thread.currentThread().getName() + ":" + q.take());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class MultiThread {
    public static void testBlockingQueue() {
        BlockingQueue<String> q = new ArrayBlockingQueue<String>(10);
        new Thread(new Producer(q)).start();//起一条线程插入要处理的事件
        new Thread(new Consumer(q), "Consumer1").start();//事件较少，平滑分配线程
        new Thread(new Consumer(q), "Consumer2").start();
    }

    private static ThreadLocal<Integer> threadLocalUserIds=new ThreadLocal<Integer>(){public Integer initialValue(){
        return 0;
    }};
    private static int userId;
    public static void testThreadLocal(){
        for(int i=0;i<10;++i){
            final int fianlI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    threadLocalUserIds.set(fianlI);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("ThreadLocal:"+threadLocalUserIds.get());
                }
            }).start();
        }
        for(int i=0;i<10;++i){
            final int fianlI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    userId=fianlI;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("ThreadLocal:"+userId);
                }
            }).start();
        }
    }


    public static void testExecutor(){
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(new Runnable() {
            @Override
            public void run() {
                for(int i =0;i<10;++i){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Executor1 " +i);
                }
            }
        });
        service.submit(new Runnable() {
            @Override
            public void run() {
                for(int i =0;i<10;++i){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Executor2 " +i);
                }
            }
        });
        service.shutdown();
        while(!service.isTerminated()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("wait for termination");
        }
    }
    public static void testFuture(){
        ExecutorService service = Executors.newSingleThreadExecutor();
        //调用Callable具有返回值
        Future<Integer> future =service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(1000);
                throw new IllegalArgumentException("异常");
                //return 1;
            }
        });
        service.shutdown();
        try{
            //System.out.println(future.get());
            //设置获取返回值的时间，当超过这个时间时会收到异常
            System.out.println(future.get(100,TimeUnit.MILLISECONDS));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //testBlockingQueue();
        //testThreadLocal();
        //testExecutor();
        testFuture();//可以获取未来获取的某个值
    }
}
