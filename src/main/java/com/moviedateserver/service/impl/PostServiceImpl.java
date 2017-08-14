package com.moviedateserver.service.impl;

import com.moviedateserver.dao.PostDao;
import com.moviedateserver.entity.Post;
import com.moviedateserver.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    public int addPostByPostPersonId(int postPersonId, String postTime, String site, String movieName, String movieTime, int sex, String endTime, int movieType, String details) {
        return postDao.addPostByPostPersonId(postPersonId,postTime,site,movieName,movieTime,sex,endTime,movieType,details);
    }

    public int deletePostByPostPersonId(int id) {
        return postDao.deletePostByPostPersonId(id);
    }

    public int updatePostById(int id, int postPersonId, String postTime, String site, String movieName, String movieTime, int sex, String endTime, int movieType, String details) {
        return postDao.updatePostById(id,postPersonId,postTime,site,movieName,movieTime,sex,endTime,movieType,details);
    }

    public List<Post> findPostBymovieName(String movieName) {
        return postDao.findPostBymovieName(movieName);
    }

    public List<Post> findPostBysite(String site) {
        return postDao.findPostBysite(site);
    }

    public List<Post> findPostBydetails(String details) {
        return postDao.findPostBydetails(details);
    }
}
