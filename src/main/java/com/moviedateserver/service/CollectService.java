package com.moviedateserver.service;


import com.moviedateserver.entity.Collect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectService {

    /* 通过 postId 来添加 */
    int addCollectByCollecterId(int postId,int collecterId, String collectTime);
    /**
     *通过postId 查询信息
     */
    List<Collect> findCollectByCollecterId(int collecterId);

    /* 通过collecterId 和postid查询信息-验证是否已经收藏 */
    Collect findCollectByCollectPostId(int postId,int collecterId);

    /*通过collecterId 和postid取消收藏*/
    int deleteCollect(int postId, int collecterId);
}
