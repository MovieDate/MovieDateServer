package com.moviedateserver.dao;

import com.moviedateserver.entity.Person;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PersonDao {

    /* 通过 postId 来添加 */
    int addPersonByPostId(@Param("postId")int postId,@Param("startPersonId")int startPersonId,
                          @Param("byPersonId")int byPersonId,@Param("personTime")String personTime);

    /**
     *通过postId 查询信息
     */
    List<Person> findPersonByPostId(int postId);
}
