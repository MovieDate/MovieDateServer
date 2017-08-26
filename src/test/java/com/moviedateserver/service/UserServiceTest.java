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
    public void loginByPhonePsw() throws Exception {
        int flag = userService.loginByPhonePsw("18219111600","123456");
//        System.out.println("密码是否正确==="+user);
        if (flag == 0) {
            System.out.println("密码错误==="+flag);
        }else {
            System.out.println("密码正确==="+flag);
        }
    }


    @Test
    public void addUserByPhonePsw() throws Exception {
        int flag = userService.addUserByPhonePsw("18219111602", "123456","xzc",0);
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
    public void updatePhoneById()throws Exception{
        int upaAmount =userService.updatePhoneById("13423722209",33);
        System.out.println("更新操作后返回的数目：" + upaAmount);
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

    @Test
    public void checkPsw() throws Exception {
        int flag = userService.checkPassword("18219111600","123456");
//        System.out.println("密码是否正确==="+user);
        if (flag == 0) {
            System.out.println("密码错误==="+flag);
        }else {
            System.out.println("密码正确==="+flag);
        }
    }

    @Test
    public void updateUser() throws Exception {
        int flag = userService.updataUser("13066209191","abcd","aaa",1,18,"运动","20170203","工作","廉江","65kg","175cm","处女座","无");
        System.out.println("更新返回==" + flag);
    }

}