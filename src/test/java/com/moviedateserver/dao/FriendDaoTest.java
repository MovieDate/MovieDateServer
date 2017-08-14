package com.moviedateserver.dao;

import com.moviedateserver.entity.Collect;
import com.moviedateserver.entity.Friend;
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
public class FriendDaoTest {
    @Autowired
    private FriendDao friendDao;

    @Test
    public void addFriendByMyId() throws Exception{
        int addAmount = friendDao.addFriendByMyId(1, 3,  "20150503");
        System.out.println("操作后返回插入的数目：" + addAmount);
    }

    @Test
    public void findFriendByMyId() throws Exception {
        List<Friend> friendList = friendDao.findFriendByMyId(1);
        System.out.println("user===" + friendList);
    }


}
