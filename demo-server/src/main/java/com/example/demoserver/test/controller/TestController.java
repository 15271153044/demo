package com.example.demoserver.test.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ywb.comm.lock.annotation.DistributedLock;
import com.ywb.model.Person;
import com.ywb.TestService;
import com.ywb.service.IPersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    
    @Resource
    private TestService testService;

    @Autowired
    @Qualifier("personService1")
    private IPersonService personService;
    
    @RequestMapping("/getTest")
//    @ResponseBody
    public Person getTest(){
        Person person = new Person();
        return testService.test();
//        return new Person("张三","18");
    }

    /**
     * 添加注释  测试1234
     * @param person
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/getPerson")
//    @ResponseBody
//    @DistributedLock(param = "name", argNum = 0, tryLock = true, lockNamePre = "growth_getperson_", lockNamePost = "LOCK", separator = "_", leaseTime = 7)
    public Person getPerson(Person person) throws InterruptedException {
//        log.error("获取person  ===   " + LocalDateTime.now());
//        LambdaQueryWrapper<Person> wrapper = new LambdaQueryWrapper<>();
//        Thread.sleep(5000);
//        return personService.getOne(wrapper.eq(Person::getName,person.getName()));
//        Person p = new Person();
        return new Person();
    }

    @RequestMapping("/listPersonPg")
    public IPage<Person> listPersonPg(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                      @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                      String name){
        return personService.listPersonPg(page,size,name);
    }
}
