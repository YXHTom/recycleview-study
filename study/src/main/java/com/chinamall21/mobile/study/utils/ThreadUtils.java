package com.chinamall21.mobile.study.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * desc：线程池
 * author：Created by xusong on 2018/9/30 15:52.
 */

/**
 * ☆☆☆ThreadPoolExecutor执行策略:当一个任务被添加到线程池时:
 * 1.线程数量未达到corePoolSize,则新建一个线程(核心线程)执行任务
 * 2.线程数量达到了corePoolSize,则进入队列进行等待
 * 3.队列已满,则新建线程(非核心线程)执行任务
 * 4.队列已满,总线程数带到了maximumPoolSize则抛出异常(RejectedExecutionHandler)
 *
 * ☆☆☆常见的四种线程池
 * 1.CachedThreadPool
 *  可缓存的线程池:1.线程数无限制 2.有空闲线程则利用空闲线程,没有则创建新的线程 3.一定程度减少创建/销毁线程,减少开销
 * 2.FixedThreadPool
 *  定长线程池:1.可控制线程池的最大并发数(同时执行的线程) 2.超出的线程会在队列等待
 * 3.ScheduledThreadPool()
 *  定长线程池:1.支持定时及周期性的执行任务
 * 4.SingleThreadExecutor
 *  单线程化的线程池:1.有且只有一个线程执行任务 2.所有线程都按照指定的顺序执行,及遵循入队出队规则
 */
public class ThreadUtils extends ThreadPoolExecutor {

    //五个参数的构造函数
    /**
     * @param corePoolSize    线程池新建的时候,如果当前线程的总数小于corePoolSize,则新建的是核心线程,如果超过corePoolSize则是非核心线程
     *                        核心线程默认情况下会一直存活在线程池中,即使这个核心线程啥也不干(闲置状态)
     *                        如果指定ThreadPoolExecutor的allowCoreThreadTimeOut这个属性为true,那么核心线程如果不干活的话(闲置)的话,超过一定时间
     *                        (由下面参数决定),核心线程就会被销毁
     *
     * @param maximumPoolSize 该线程池中线程总数最大值 线程总数 = 核心线程+非核心线程
     *
     * @param keepAliveTime   线程池中非核心线程的闲置时长 非核心线程如果闲置超过这个值,该线程就会被销毁
     *
     * @param unit            keepAliveTime的单位，TimeUnit是一个枚举类型，其包括 NANOSECONDS ： 1微毫秒 = 1微秒 / 1000
     *                        MICROSECONDS ： 1微秒 = 1毫秒 / 1000
     *                        MILLISECONDS ： 1毫秒 = 1秒 /1000
     *                        SECONDS ： 秒
     *                        MINUTES ： 分
     *                        HOURS ： 小时
     *                        DAYS ： 天
     *
     * @param workQueue       该线程池的任务队列,维护着等待着执行的Runnable对象
     *                        当所有的核心线程都在干活时,新添加的任务会被添加到这个队列中等待处理,如果队列满了,则新建非核心线程数
     *                        常用的workQueue类型：
     *                        1.SynchronousQueue:这个队列接收到任务的时候,会直接提交给线程处理,而不保留它,如果所有的线程都在工作,那就
     *                        新建一个线程处理这个任务!所以为了保证不出现线程数达到了maximumPoolSize而不能新建线程的错误,使用这个类型队列
     *                        的时候,maximumPoolSize一般为Integer.Max_VALUE
     *
     *                        2.LinkedBlockingQueue:这个队列接收到任务的时候,如果当前线程小于核心线程数,则新建线程(核心线程)处理任务;如果当前线程
     *                        等于核心线程数,则进入队列等待.由于这个队列没有最大值限制,即所有超过核心线程数的任务都会被添加到队列中,这也就导致了
     *                        maximumPoolSize失效,因为总线程数永远不会超过corePoolSize
     *
     *                        3.ArrayBlockingQueue:可以限定队列的长度,接收到任务的时候,如果没有达到corePoolSize的值,则新建核心线程,如果达到了
     *                        则入队等候,如果队列已满,则新建线程(非核心线程)执行任务,如果达到了maximumPoolSize而且队列满了则发生错误
     *
     *                        4.DelayQueue:队列内元素必须实现Delayed接口,代表你传入的任务必须实现Delayed接口.这个队列接收到任务时先入列,达到了
     *                        指定的Delayed延迟时间才会执行任务.
     */
    public ThreadUtils(int corePoolSize, int maximumPoolSize,
                       long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);

    }

    //六个参数的构造函数-1

    public ThreadUtils(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                       TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    //六个参数的构造函数-2

    public ThreadUtils(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                       TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    //七个参数的构造函数

    public ThreadUtils(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                       TimeUnit unit, BlockingQueue<Runnable> workQueue,
                       ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);

    }
}
