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
}
