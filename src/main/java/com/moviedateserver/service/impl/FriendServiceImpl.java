package com.moviedateserver.service.impl;

import com.moviedateserver.dao.FriendDao;
import com.moviedateserver.entity.Friend;
import com.moviedateserver.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService{
    @Autowired
    private FriendDao friendDao;

    public int addFriendByMyId(int myId, int friendId, String addTime) {
        return friendDao.addFriendByMyId(myId,friendId,addTime);
    }

    public int delFriendByFriendId(int friendId) {
        return friendDao.delFriendByFriendId(friendId);
    }

    public List<Friend> findFriendByMyId(int myId) {
        return friendDao.findFriendByMyId(myId);
    }
}
