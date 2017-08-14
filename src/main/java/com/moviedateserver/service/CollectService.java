package com.moviedateserver.service;


import com.moviedateserver.entity.Collect;

import java.util.List;

public interface CollectService {

    /* 通过 postId 来添加 */
    int addCollectByCollecterId(int postId,int collecterId, String collectTime);
    /**
     *通过postId 查询信息
     */
    List<Collect> findCollectByCollecterId(int collecterId);
}
