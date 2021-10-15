package com.ywb.comm.lock.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class DistributedLockService {

    private static final long DEFAULT_WAIT_TIME = 30L;
    private static final long DEFAULT_TIMEOUT = 5L;

    @Autowired
    private RedissonClient redisson;

    public DistributedLockService() {
    }

    public DistributedLockService(RedissonClient redisson) {
        this.redisson = redisson;
    }

    public <T> T lock(DistributedLockCallback<T> callback, long leaseTime, boolean fairLock) {
        return this.lock(callback, leaseTime, TimeUnit.SECONDS, fairLock);
    }

    public <T> T lock(DistributedLockCallback<T> callback, long leaseTime, TimeUnit timeUnit, boolean fairLock) {
        RLock lock = this.getLock(callback.getLockName(), fairLock);
        lock.lock(leaseTime, timeUnit);
        if (!Thread.currentThread().isInterrupted()) {
            //log.debug("{} gets lock.", Thread.currentThread().getName());
            T var8;
            try {
                T t = callback.process();
                var8 = t;
            } finally {
                log.debug("是否有锁:{}, 是否被当前线程锁着:{}.", lock.isLocked(), lock.isHeldByCurrentThread());
                if (lock != null && lock.isLocked() && lock.isHeldByCurrentThread()) {
                    //log.debug("{}, {} release lock.", Thread.currentThread().getName(), Thread.currentThread().getId());
                    lock.unlock();
                }

            }
            return var8;
        } else {
            throw new RuntimeException(Thread.currentThread().getName() + " does not get lock. lockName[ " + callback.getLockName() + "]");
        }
    }

    public <T> T tryLock(DistributedLockCallback<T> callback, boolean fairLock) {
        return this.tryLock(callback, DEFAULT_WAIT_TIME, DEFAULT_TIMEOUT, TimeUnit.SECONDS, fairLock);
    }

    public <T> T tryLock(DistributedLockCallback<T> callback, long waitTime, long leaseTime, TimeUnit timeUnit, boolean fairLock) {
        RLock lock = this.getLock(callback.getLockName(), fairLock);
        boolean trySuccess = false;
        T var11;
        try {
            trySuccess = lock.tryLock(waitTime, leaseTime, timeUnit);
            if (!trySuccess) {
                log.info(Thread.currentThread().getName() + "try to get lock failed. lockName[ " + callback.getLockName() + "]");
                return null;
            }
            if (Thread.currentThread().isInterrupted()) {
                throw new RuntimeException(Thread.currentThread().getName() + " does not get lock. lockName[ " + callback.getLockName() + "]");
            }
            T t = callback.process();
            var11 = t;
        } catch (InterruptedException var15) {
            return null;
        } finally {
            if (trySuccess && lock != null && lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return var11;
    }

    private RLock getLock(String lockName, boolean fairLock) {
        RLock lock;
        if (fairLock) {
            lock = this.redisson.getFairLock(lockName);
        } else {
            lock = this.redisson.getLock(lockName);
        }
        return lock;
    }

}
