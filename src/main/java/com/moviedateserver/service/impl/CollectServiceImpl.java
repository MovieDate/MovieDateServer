package com.moviedateserver.service.impl;

import com.moviedateserver.dao.CollectDao;
import com.moviedateserver.entity.Collect;
import com.moviedateserver.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectServiceImpl implements CollectService{
    @Autowired
    private CollectDao collectDao;


    public int addCollectByCollecterId(int postId, int collecterId, String collectTime) {
        return collectDao.addCollectByCollecterId(postId,collecterId,collectTime);
    }

    public List<Collect> findCollectByCollecterId(int collecterId) {
        return collectDao.findCollectByCollecterId(collecterId);
    }

    public Collect findCollectByCollectPostId(int postId,int collecterId) {
        return collectDao.findCollectByCollectPostId(postId,collecterId);
    }
}
