package com.moviedateserver.service;

import com.moviedateserver.entity.Friend;

import java.util.List;

public interface FriendService {
    /* 通过 myId 来添加 */
    int addFriendByMyId(int myId,int friendId, String addTime);
    /**
     *通过 myId 查询信息
     */
    List<Friend> findFriendByMyId(int myId);
}
