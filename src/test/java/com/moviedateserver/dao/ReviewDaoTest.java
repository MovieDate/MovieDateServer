package com.moviedateserver.dao;

import com.moviedateserver.entity.Review;
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
public class ReviewDaoTest {

    @Autowired
    private  ReviewDao reviewDao;

    @Test
    public void addUserReviewByPostId() throws Exception {
        int addAmount = reviewDao.addUserReviewByPostId(1,2,"评论","20150202");
        System.out.println("操作后返回插入的数目：" + addAmount);
    }

    @Test
    public void updateReviewByPostId() throws Exception {
        int updateAmount = reviewDao.updateReviewByPostId(1,2,"评论2");
        System.out.println("更新操作后返回的数目：" + updateAmount);
    }

    @Test
    public void deleteReviewByReviewDetails() throws Exception {
        int deleteAmount = reviewDao.deleteReviewByReviewDetails(7,1,"101010","2017-09-03 16:31:59");
        System.out.println("操作后返回删除的数目：" + deleteAmount);
    }

    /*@Test
    public void findReviewByReviewDetails() throws Exception {
        List<Review> reviewList = reviewDao.findReviewByReviewDetails("评论");
        System.out.println("user===" + reviewList);
    }*/



}
