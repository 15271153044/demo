package com.example.demoserver.test.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demoserver.test.mapper.PersonMapper;
import com.ywb.model.Person;
import com.ywb.service.IPersonService;
import org.springframework.stereotype.Service;

@Service("personService2")
public class Person2ServiceImpl extends ServiceImpl<PersonMapper, Person> implements IPersonService {

    @Override
    public IPage<Person> listPersonPg(Integer page, Integer size, String name) {
        return null;
    }
}
