package com.moviedateserver.service;

import com.moviedateserver.entity.Person;

import java.util.List;

public interface PersonService {
    /* 通过 postId 来添加 */
    int addPersonByPostId(int postId,int startPersonId,int byPersonId,String personTime);

    /**
     *通过postId 查询信息
     */
    List<Person> findPersonByPostId(int postId);
}
