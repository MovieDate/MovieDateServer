package com.moviedateserver.dao;

import com.moviedateserver.entity.Post;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostDao {
    /**
     * 通过postpersonid传入数据库
     */
    int addPostByPostPersonId(@Param("postPersonId")int postPersonId,@Param("postTime")String postTime,
                              @Param("site")String site,@Param("movieName")String movieName,
                              @Param("movieTime")String movieTime,@Param("sex")int sex,
                              @Param("endTime")String endTime,@Param("movieType")int movieType,
                              @Param("details")String details);

    /**
    *通过 id 删除帖子,因为一个postpersonid可以有多个帖子，但是一次只要求删一个
    */
    int deletePostByPostPersonId(int id);

    /**
     *通过 id 更新帖子内容，,因为一个postpersonid可以有多个帖子，但是一次只要求更新一个
     */
    int updatePostById(@Param("id")int id,@Param("postPersonId")int postPersonId,
                            @Param("postTime")String postTime, @Param("site")String site,
                            @Param("movieName")String movieName, @Param("movieTime")String movieTime,
                            @Param("sex")int sex, @Param("endTime")String endTime,
                            @Param("movieType")int movieType, @Param("details")String details);


    /**
     *通过 id or 地点or 约影的影片名字 or约影地点 or发帖内容
     */
    Post findPostByid(int id);

    List<Post> findPostBymovieName(String movieName);

    List<Post> findPostBysite(String site);

    List<Post> findPostBydetails(String details);





}
