package com.moviedateserver.dao;

import com.moviedateserver.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by wunaifu on 2017/8/8.
 */
//配置spring和junit整合，这样junit在启动时就会加载spring容器
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class UserDaoTest {

    //注入Dao实现类依赖
//    @Resource
    @Autowired
    private UserDao userDao;//有红色波浪线，不用管它

    @Test
    public void addUserByPhonePsw() throws Exception {
        int addAmount = userDao.addUserByPhonePsw("18219111601","123456");
        System.out.println("操作后返回插入的数目：" + addAmount);
    }

    @Test
    public void deleteUserByPhone() throws Exception {
        int deleteAmount = userDao.deleteUserByPhone("18219111601");
        System.out.println("操作后返回删除的数目：" + deleteAmount);
    }

    @Test
    public void updatePswByPhonePsw() throws Exception {
        int updateAmount = userDao.updatePswByPhonePsw("18219111603","123456");
        System.out.println("更新操作后返回的数目：" + updateAmount);
    }

    @Test
    public void findUserByPhone() throws Exception {
        User user = userDao.findUserByPhone("18219111629");
        System.out.println("user==="+user);
    }

    @Test
    public void findAllUserDESC() throws Exception {
        List<User> userList=userDao.findAllUserDESC();
        for (User user : userList) {
            System.out.println("User===="+user);
        }
    }

    @Test
    public void checkPsw() throws Exception {
        User user = userDao.checkPassword("18219111600","1234561");
//        System.out.println("密码是否正确==="+user);
        if (user == null) {
            System.out.println("密码错误==="+user);
        }else {
            System.out.println("密码正确==="+user);
        }
    }

}