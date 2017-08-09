package com.moviedateserver.service;

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
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void addUserByPhonePsw() throws Exception {
        int flag = userService.addUserByPhonePsw("18219111602", "123456");
        System.out.println("添加返回=="+flag);
    }

    @Test
    public void deleteUserByPhone() throws Exception {
        int flag = userService.deleteUserByPhone("18219111602");
        System.out.println("删除返回==" + flag);
    }

    @Test
    public void updatePswByPhonePsw() throws Exception {
        int flag = userService.updatePswByPhonePsw("18219111601","123456test");
        System.out.println("更新返回==" + flag);
    }

    @Test
    public void findUserByPhone() throws Exception {
        User user = userService.findUserByPhone("18219111600");
        System.out.println("查找User==="+user);
    }

    @Test
    public void findAllUserDESC() throws Exception {
        List<User> userList=userService.findAllUserDESC();
        System.out.println("userList===="+userList);
    }

}