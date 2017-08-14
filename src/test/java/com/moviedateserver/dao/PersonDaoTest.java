package com.moviedateserver.dao;

import com.moviedateserver.entity.Friend;
import com.moviedateserver.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

//配置spring和junit整合，这样junit在启动时就会加载spring容器
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class PersonDaoTest {
    @Autowired
    private PersonDao personDao;

    @Test
    public void addPersonByPostId() throws Exception {
        int addAmount = personDao.addPersonByPostId(20, 3, 3, "20150503");
        System.out.println("操作后返回插入的数目：" + addAmount);
    }

    @Test
    public void findPersonByPostId() throws Exception {
        List<Person> personList = personDao.findPersonByPostId(20);
        System.out.println("user===" + personList);
    }

}
