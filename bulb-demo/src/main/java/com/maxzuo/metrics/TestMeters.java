package com.maxzuo.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.TimeUnit;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * Meters用来度量某个时间段的平均处理次数（request per second），每1、5、15分钟的TPS。比如一个service的请求数，
 * 通过metrics.meter()实例化一个Meter之后，然后通过meter.mark()方法就能将本次请求记录下来。统计结果有总的请求数，
 * 平均每秒的请求数，以及最近的1、5、15分钟的平均TPS。
 * <p>
 * Created by zfh on 2019/06/09
 */
public class TestMeters {

    /**
     * 实例化一个registry，最核心的一个模块，相当于一个应用程序的metrics系统的容器，维护一个Map
     */
    private static final MetricRegistry metrics = new MetricRegistry();

    /**
     * 在控制台上打印输出
     */
    private static ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).build();

    /**
     * 实例化一个Meter
     */
    private static final Meter requests = metrics.meter(name(TestMeters.class, "request"));

    public static void handleRequest() {
        requests.mark();
    }

    public static void main(String[] args) throws InterruptedException {
        reporter.start(3, TimeUnit.SECONDS);

        while(true){
            handleRequest();
            Thread.sleep(100);
        }
    }
}
