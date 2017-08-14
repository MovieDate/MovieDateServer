package com.moviedateserver.service;

import com.moviedateserver.entity.Collect;
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
public class CollectServiceTest {
    @Autowired
    private CollectService collectService;

    @Test
    public void addCollectByCollecterId() throws Exception{
        int addAmount = collectService.addCollectByCollecterId(1, 3,  "20150503");
        System.out.println("操作后返回插入的数目：" + addAmount);
    }

    @Test
    public void findCollectByCollecterId() throws Exception {
        List<Collect> collectList = collectService.findCollectByCollecterId(1);
        for (Collect collect: collectList) {
            System.out.println("user===" + collect);
        }
    }
}

