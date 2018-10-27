package com.example.common.tools;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * RxJava2 设置监听器，监听event
 * <p>
 * Created by LiuKuo at 2018/3/21
 */

public class RxBus {

    /**
     * 相当于一个事件队列，存储的是Flowable事件
     */
    private FlowableProcessor<Object> bus;

    /**
     * PublishSubject只会把在订阅发生的时间点之后来自原始Flowable的数据发射给观察者
     */
    public RxBus() {
        bus = PublishProcessor.create().toSerialized();
    }

    public static RxBus getDefault() {
        return RxBusHolder.Instance;
    }

    private static class RxBusHolder {
        private static RxBus Instance = new RxBus();
    }

    /**
     * 添加一个新的事件
     *
     * @param o Object
     */
    public void post(Object o) {
        bus.onNext(o);
    }

    /**
     * 过滤事件
     * 过滤操作符 ofType
     *
     * @param eventType 类型
     * @return Flowable<T>
     */
    public <T> Flowable<T> toFlowable(Class<T> eventType) {
        return bus.ofType(eventType);
    }
}
