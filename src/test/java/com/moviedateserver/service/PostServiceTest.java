package com.moviedateserver.service;

import com.moviedateserver.entity.Post;
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
public class PostServiceTest {
    @Autowired
    private PostService postService;

    @Test
    public void addPostByPostPersonId() throws Exception {
        int addAmount = postService.addPostByPostPersonId(2,"20170103","廉江","战狼2","20170104",0,"20170105",0,"666");
        System.out.println("操作后返回插入的数目：" + addAmount);
    }

    @Test
    public void  deletePostByPostPersonId()throws Exception{
        int deleteAmount = postService.deletePostByPostPersonId(2);
        System.out.println("操作后返回删除的数目：" + deleteAmount);
    }

    @Test
    public void updatePostById() throws Exception {
        int updateAmount = postService.updatePostById(2,2,"20170103","江门","战狼2","20170104",0,"20170105",0,"哈哈哈");

        System.out.println("更新操作后返回的数目：" + updateAmount);
    }

    @Test
    public void findPostByid()throws Exception{
        Post post =postService.findPostByid(1);
        System.out.println("post=="+post);
    }

    @Test
    public void findPostBymovieName() throws Exception {
        List<Post> postList = postService.findPostBymovieName("战狼");
        System.out.println("user===" + postList);
    }

    @Test
    public void findPostBysite() throws Exception {
        List<Post> postList = postService.findPostBysite("江门");
        System.out.println("user==="+postList);
    }

    @Test
    public void findPostBydetails() throws Exception {
        List<Post> postList = postService.findPostBydetails("大地");
        System.out.println("user==="+postList);
    }
}
