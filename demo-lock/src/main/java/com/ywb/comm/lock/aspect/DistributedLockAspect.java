package com.ywb.comm.lock.aspect;

import com.ywb.comm.lock.annotation.DistributedLock;
import com.ywb.comm.lock.service.DistributedLockCallback;
import com.ywb.comm.lock.service.DistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.apache.commons.beanutils.PropertyUtils;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
/**
 * DistributedLockAspect：
 * @author: zzhu
 * @date: 2021/5/25
 */
@Aspect
@Component
@Slf4j
@Order(3) // TODO 什么意思？
public class DistributedLockAspect {

    @Autowired
    private DistributedLockService lockService;

    public DistributedLockAspect() {
    }

    @Pointcut("@annotation(com.ywb.comm.lock.annotation.DistributedLock)")
    public void DistributedLockAspect() {
    }

    @Around("DistributedLockAspect()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Class targetClass = pjp.getTarget().getClass();
        String methodName = pjp.getSignature().getName();
        Class[] parameterTypes = ((MethodSignature)pjp.getSignature()).getMethod().getParameterTypes();
        Method method = targetClass.getMethod(methodName, parameterTypes);
        Object[] arguments = pjp.getArgs();
        String lockName = this.getLockName(method, arguments);
        return this.lock(pjp, method, lockName);
    }

    @AfterThrowing(value = "DistributedLockAspect()", throwing = "ex")
    public void afterThrowing(Throwable ex) {
        if (ex instanceof RuntimeException) {
            throw (RuntimeException)ex;
        } else {
            throw new RuntimeException(ex);
        }
    }

    public String getLockName(Method method, Object[] args) {
        Objects.requireNonNull(method);
        DistributedLock annotation = (DistributedLock)method.getAnnotation(DistributedLock.class);
        String lockName = annotation.lockName();
        String param = annotation.param();
        if (StringUtils.isEmpty(lockName) && args.length > 0) {
            if (StringUtils.isNotEmpty(param)) {
                Object arg;
                if (annotation.argNum() > 0) {
                    arg = args[annotation.argNum() - 1];
                } else {
                    arg = args[0];
                }
                lockName = String.valueOf(this.getParam(arg, param));
            } else if (annotation.argNum() > 0) {
                lockName = args[annotation.argNum() - 1].toString();
            }
        }
        if (StringUtils.isNotEmpty(lockName)) {
            String preLockName = annotation.lockNamePre();
            String postLockName = annotation.lockNamePost();
            String separator = annotation.separator();
            StringBuilder lName = new StringBuilder();
            if (StringUtils.isNotEmpty(preLockName)) {
                lName.append(preLockName).append(separator);
            }

            lName.append(lockName);
            if (StringUtils.isNotEmpty(postLockName)) {
                lName.append(separator).append(postLockName);
            }
            lockName = lName.toString();
            return lockName;
        } else {
            throw new IllegalArgumentException("Can't get or generate lockName accurately!");
        }
    }

    public Object getParam(Object arg, String param) {
        if (StringUtils.isNotEmpty(param) && arg != null) {
            try {
                Object result = PropertyUtils.getProperty(arg, param);
                return result;
            } catch (NoSuchMethodException var4) {
                throw new IllegalArgumentException(arg + "没有属性" + param + "或未实现get方法。", var4);
            } catch (Exception var5) {
                throw new RuntimeException("", var5);
            }
        } else {
            return null;
        }
    }

    public Object lock(ProceedingJoinPoint pjp, Method method, String lockName) {
        DistributedLock annotation = (DistributedLock)method.getAnnotation(DistributedLock.class);
        boolean fairLock = annotation.fairLock();
        boolean tryLock = annotation.tryLock();
        return tryLock ? this.tryLock(pjp, annotation, lockName, fairLock) : this.lock(pjp,  lockName, fairLock,annotation.leaseTime());
    }

    public Object lock(final ProceedingJoinPoint pjp, final String lockName, boolean fairLock,long leaseTime) {
        return this.lockService.lock(new DistributedLockCallback<Object>() {
            @Override
            public Object process() {
                return DistributedLockAspect.this.proceed(pjp);
            }
            @Override
            public String getLockName() {
                return lockName;
            }
        }, leaseTime,fairLock);
    }

    public Object tryLock(final ProceedingJoinPoint pjp, DistributedLock annotation, final String lockName, boolean fairLock) {
        long waitTime = annotation.waitTime();
        long leaseTime = annotation.leaseTime();
        TimeUnit timeUnit = annotation.timeUnit();
        return this.lockService.tryLock(new DistributedLockCallback<Object>() {
            @Override
            public Object process() {
                return DistributedLockAspect.this.proceed(pjp);
            }
            @Override
            public String getLockName() {
                return lockName;
            }
        }, waitTime, leaseTime, timeUnit, fairLock);
    }

    public Object proceed(ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch (Throwable var3) {
            if (var3 instanceof RuntimeException) {
                throw (RuntimeException)var3;
            } else {
                throw new RuntimeException(var3);
            }
        }
    }

}
