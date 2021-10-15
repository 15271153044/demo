package com.ywb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ywb.model.Person;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2021-10-14
 */
public interface IPersonService extends IService<Person> {

    IPage<Person> listPersonPg(Integer page,Integer size,String name);
}
