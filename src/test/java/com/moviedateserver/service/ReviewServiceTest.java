package com.moviedateserver.service;

import com.moviedateserver.entity.Review;
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
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Test
    public void addUserReviewByPostId() throws Exception {
        int addAmount = reviewService.addUserReviewByPostId(1,2,"评论","20150202");
        System.out.println("操作后返回插入的数目：" + addAmount);
    }

    @Test
    public void updateReviewByPostId() throws Exception {
        int updateAmount = reviewService.updateReviewByPostId(1,2,"评论2");
        System.out.println("更新操作后返回的数目：" + updateAmount);
    }

    @Test
    public void deleteReviewByReviewDetails() throws Exception {
        int deleteAmount = reviewService.deleteReviewByReviewDetails("评论",2);
        System.out.println("操作后返回删除的数目：" + deleteAmount);
    }

    @Test
    public void findReviewByReviewDetails() throws Exception {
        List<Review> reviewList = reviewService.findReviewByReviewDetails("评论");
        System.out.println("user===" + reviewList);
    }
}
