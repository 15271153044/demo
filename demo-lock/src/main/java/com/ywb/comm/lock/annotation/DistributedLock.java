package com.ywb.comm.lock.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * DistributedLock：
 * @author: zzhu
 * @date: 2021/5/25
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {

    String lockName() default "";

    String lockNamePre() default "";

    String lockNamePost() default "lock";

    String separator() default ".";

    String param() default "";

    int argNum() default 0;

    boolean fairLock() default false;

    boolean tryLock() default false;

    long waitTime() default 30L;

    long leaseTime() default 5L;

    TimeUnit timeUnit() default TimeUnit.SECONDS;

}
