package com.moviedateserver.dao;

import com.moviedateserver.entity.Friend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendDao {
    /*通过 myId 来添加*/
    int addFriendByMyId(@Param("myId")int myId, @Param("friendId")int friendId, @Param("addTime")String addTime);

    /*通过 friendId 来删除*/
    int delFriendByFriendId(int friendId);

    /*通过 myId 查询信息*/
    List<Friend>  findFriendByMyId(int myId);

    /*
    * 通过myId 和friendId查询信息*/
    Friend findFriendByMyIdFriendId(@Param("myId")int myId, @Param("friendId")int friendId);
}
