package com.moviedateserver.service;

import com.moviedateserver.entity.Post;

import java.util.List;

public interface PostService {

    /*
    * 查找所有的帖子
    * */
    List<Post> findAllPost();

    /**
     * 通过postpersonid传入数据库
     */
    int addPostByPostPersonId(int postPersonId,String postTime,String site,String movieName,
                              String movieTime,int sex,int movieType,String details);

    /**
     *通过 id 删除帖子,因为一个postpersonid可以有多个帖子，但是一次只要求删一个
     */
    int deletePostByPostPersonId(int id);

    /**
     *通过 id 更新帖子内容，,因为一个postpersonid可以有多个帖子，但是一次只要求更新一个
     */
    int updatePostById(int id,int postPersonId,String postTime,String site,String movieName,
                            String movieTime,int sex,String endTime,int movieType,String details);

    /**
     *通过id or 地点 or 约影的影片名字 or约影地点 or发帖内容
     */
    Post findPostByid(int id);

    List<Post> findPostBymovieName(String movieName);

    List<Post> findPostBysite(String site);

    List<Post> findposttab(String details);

}
