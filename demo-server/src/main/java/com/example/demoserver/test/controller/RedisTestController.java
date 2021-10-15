package com.example.demoserver.test.controller;

import com.ywb.comm.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * redis 测试工具类
 */
@RestController
public class RedisTestController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/set")
    @ResponseBody
    public Object set() {

        List<Object> strings = new ArrayList<>();
        strings.add("哈哈哈");
        strings.add("嘤嘤嘤");
        strings.add("嘎嘎嘎");

        redisUtil.lSetList("666", strings);

        get();

        return "set success~";
    }

    @GetMapping("/get")
    @ResponseBody
    public Object get() {

        System.out.println("list size: " + redisUtil.lGetListSize("666"));
        System.out.println(redisUtil.lGet("666", 0, -1));

        return "get success";
    }

    @GetMapping("/del")
    @ResponseBody
    public Object del() {

        redisUtil.lRemove("666", 0, "哈哈哈");
        get();

        return "delete success";
    }

}
