package com.example.demoserver.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demoserver.test.mapper.PersonMapper;
import com.ywb.model.Person;
import com.ywb.service.IPersonService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2021-10-14
 */
@Service("personService1")
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements IPersonService {

    @Override
    public IPage<Person> listPersonPg(Integer page, Integer size,String name) {
        Page<Person> pg = new Page<>(page,size);
        LambdaQueryWrapper<Person> wrapper = new LambdaQueryWrapper<>();
//        IPage<Person> persons = baseMapper.selectPage(pg, wrapper);

        Person person = new Person();
        person.setName(name);
        IPage<Person> persons = baseMapper.listPersons(pg, person);
        return persons;
    }
}
