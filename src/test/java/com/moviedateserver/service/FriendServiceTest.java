package com.moviedateserver.service;

import com.moviedateserver.entity.Friend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class FriendServiceTest {
    @Autowired
    private FriendService friendService;

    @Test
    public void addFriendByMyId() throws Exception{
        int addAmount = friendService.addFriendByMyId(1, 3,  "20150503");
        System.out.println("操作后返回插入的数目：" + addAmount);
    }

    @Test
    public void findFriendByMyId() throws Exception {
        List<Friend> friendList = friendService.findFriendByMyId(1);
        System.out.println("user===" + friendList);
    }
}
