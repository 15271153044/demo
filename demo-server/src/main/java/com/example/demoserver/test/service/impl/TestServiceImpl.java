package com.example.demoserver.test.service.impl;

import com.example.demoserver.test.mapper.PersonMapper;
import com.ywb.model.Person;
import com.ywb.TestService;
import com.ywb.config.TestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestConfig testConfig;

    @Resource
    private PersonMapper personMapper;


    @Override
    public Person test() {
        System.out.println(testConfig);
        Person person = personMapper.findPerson();
        System.out.println(person.getAge());
        return person;
    }
}