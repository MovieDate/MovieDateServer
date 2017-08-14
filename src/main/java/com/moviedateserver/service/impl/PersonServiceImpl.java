package com.moviedateserver.service.impl;

import com.moviedateserver.dao.PersonDao;
import com.moviedateserver.entity.Person;
import com.moviedateserver.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    /* 通过 postId 来添加
    * */
    public int addPersonByPostId(int postId, int startPersonId, int byPersonId, String personTime) {
        return personDao.addPersonByPostId(postId,startPersonId,byPersonId,personTime);
    }

    /**
     *通过postId 查询信息
     */
    public List<Person> findPersonByPostId(int postId) {
        return personDao.findPersonByPostId(postId);
    }
}
