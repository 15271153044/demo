Getting Started
Reference Documentation
For further reference, please consider the following sections:

Official Apache Maven documentation
Spring Boot Maven Plugin Reference Guide
Create an OCI image
springboot
集成 mysql + mybatis + mybatis-plus mybatis-generator 代码生成工具

单元测试 @junit

@RunWith(SpringRunner.class) @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@Test

@Qualifier 一个接口有多个实现类 如何注入

1、@Autowired 是通过 byType 的方式去注入的， 使用该注解，要求接口只能有一个实现类。 2、@Resource 可以通过 byName 和 byType的方式注入， 默认先按 byName的方式进行匹配，如果匹配不到，再按 byType的方式进行匹配。 3、@Qualifier 注解可以按名称注入， 但是注意是 类名

redis

分布式锁
