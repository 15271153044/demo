package com.example.demoserver;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ywb.comm.redis.utils.RedisUtil;
import com.ywb.model.Person;
import com.ywb.service.IPersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTestController {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testRedis(){
        List<Object> strings = new ArrayList<>();
        strings.add("哈哈哈");
        strings.add("嘤嘤嘤");
        strings.add("嘎嘎嘎");

        redisUtil.lSetList("666", strings);

        System.out.println("list size: " + redisUtil.lGetListSize("666"));
        System.out.println(redisUtil.lGet("666", 0, -1));

    }
}
