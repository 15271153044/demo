package com.example.demoserver;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ywb.model.Person;
import com.ywb.service.IPersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestControllerTests {

    @Autowired
    @Qualifier("personService1")
    private IPersonService personService;

    @Test
    public void getPerson(){
        LambdaQueryWrapper<Person> wrapper = new LambdaQueryWrapper<>();
        Person person = personService.getOne(wrapper.eq(Person::getName, "ywb"));
        System.out.println(person != null ?"姓名 " + person.getName() + " 年龄  " + person.getAge():"null");
//        return new Person("张三","18");
    }
}
