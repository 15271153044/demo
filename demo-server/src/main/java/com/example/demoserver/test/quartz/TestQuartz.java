package com.example.demoserver.test.quartz;

import com.ywb.comm.lock.annotation.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Slf4j
@Component
@EnableAsync
@EnableScheduling
public class TestQuartz {


//    @Transactional(rollbackFor = Exception.class)
    @Async
//    @Scheduled(cron = "*/1 * * * * ?")
    @DistributedLock(lockName = "test-key", tryLock = true, lockNamePre = "growth", lockNamePost = "LOCK", separator = "_", leaseTime = 10000)
    public void testQuartz() {
        try {
            LocalDateTime date =  LocalDateTime.now();
            log.error("测试定时任务执行:{}",date);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$测试定时任务执行结束$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        }
    }

    public static void main(String[] args) {
        LocalDateTime date =  LocalDateTime.now();
        log.error("测试定时任务执行:{}",date);
    }
}
