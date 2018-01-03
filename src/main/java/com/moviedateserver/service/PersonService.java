package com.moviedateserver.service;

import com.moviedateserver.entity.Person;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PersonService {
    /* 通过 postId 来添加 */
    int addPersonByPostId(int postId,int startPersonId,int byPersonId,String personTime);

    /**
     *通过postId 查询信息
     */
    List<Person> findPersonByPostId(int postId);

    /*通过postId和byPersonId验证是否已经报名约影*/
    List<Person> findPersobByPostpersonId(int postId, int byPersonId);

    /*通过byPersonId查找约影*/
    List<Person> findPersonBybyPersonId(int byPersonId);

    /*通过postId和byPersonId取消约影*/
    int deletePersonByPostpersonId(@Param("postId")int postId,@Param("byPersonId")int byPersonId);

}
