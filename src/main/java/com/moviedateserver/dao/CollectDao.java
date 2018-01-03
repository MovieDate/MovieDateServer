package com.moviedateserver.dao;

import com.moviedateserver.entity.Collect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectDao {
    /*通过 collecterId 来添加*/
    int addCollectByCollecterId(@Param("postId")int postId, @Param("collecterId")int collecterId,
                                 @Param("collectTime")String collectTime);

    /*通过collecterId 查询信息*/
    List<Collect> findCollectByCollecterId(int collecterId);

    /* 通过collecterId 和postid查询信息-验证是否已经收藏 */
    Collect findCollectByCollectPostId(@Param("postId") int postId,@Param("collecterId") int collecterId);

    /*通过collecterId 和postid取消收藏*/
    int deleteCollect(@Param("postId") int postId,@Param("collecterId") int collecterId);
}
