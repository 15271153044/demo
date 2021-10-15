package com.ywb.comm.lock.service;

public interface DistributedLockCallback<T> {

    T process();

    String getLockName();

}
