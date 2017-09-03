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

    public List<Post> findAllPost() {
        return postDao.findAllPost();
    }

    public int addPostByPostPersonId(int postPersonId, String postTime, String site, String movieName, String movieTime, int sex, int movieType, String details) {
        return postDao.addPostByPostPersonId(postPersonId,postTime,site,movieName,movieTime,sex,movieType,details);
    }


    public int deletePostByPostPersonId(int id) {
        return postDao.deletePostByPostPersonId(id);
    }

    public int updatePostById(int id, int postPersonId, String postTime, String site, String movieName, String movieTime, int sex, String endTime, int movieType, String details) {
        return postDao.updatePostById(id,postPersonId,postTime,site,movieName,movieTime,sex,endTime,movieType,details);
    }

    public Post findPostByid(int id) {
        return postDao.findPostByid(id);
    }

    public List<Post> findPostBymovieName(String movieName) {
        return postDao.findPostBymovieName(movieName);
    }

    public List<Post> findPostBysite(String site) {
        return postDao.findPostBysite(site);
    }

    public List<Post> findposttab(String details) {
        return postDao.findposttab(details);
    }
}
