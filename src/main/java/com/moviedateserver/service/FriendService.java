package com.moviedateserver.service;

import com.moviedateserver.entity.Friend;

import java.util.List;

public interface FriendService {
    /* 通过 myId 来添加 */
    int addFriendByMyId(int myId,int friendId, String addTime);
    /* 通过 friendId 来删除 */
    int delFriendByFriendId(int friendId);
    /**
     *通过 myId 查询信息
     */
    List<Friend> findFriendByMyId(int myId);

    /*
    * 通过myId 和friendId查询信息*/
    Friend findFriendByMyIdFriendId(int myId,int friendId);
}
